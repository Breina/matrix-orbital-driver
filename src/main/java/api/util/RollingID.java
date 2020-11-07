package api.util;

import com.CommunicationException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import java.util.function.Consumer;
import util.ThrowingConsumer;

public class RollingID<T extends RollingEntity> {

    private final Cache<Byte, T> entities;
    private final int maxId;
    private int entityCount = 0;
    private byte entityIndexCounter = 0;

    public RollingID(int maxEntities) {
        this.maxId = maxEntities - 1;

        entities = CacheBuilder.newBuilder()
                .maximumSize(maxEntities)
                .weakValues()
                .removalListener((RemovalListener<Byte, T>) notification -> {
                    entityCount--;
                    notification.getValue().remove();
                })
                .build();
    }

    private void deregister(Byte key) {
        entities.invalidate(key);
    }

    public T register(Consumer<Byte> creationAction, T entity) {
        byte id = getNextAvailableId();

        creationAction.accept(id);

        return addEntity(entity, id);
    }

    public T registerFileUpload(ThrowingConsumer<Byte, CommunicationException> creationAction, T entity)
        throws CommunicationException {
        byte id = getNextAvailableId();

        creationAction.accept(id);

        return addEntity(entity, id);
    }

    private T addEntity(T entity, byte id) {
        entities.put(id, entity);
        entityCount++;
        entity.setId(id);
        entity.setOnRemove(() -> deregister(id));
        return entity;
    }

    public T register(T entity) {
        return register(id -> {
        }, entity);
    }

    private byte getNextAvailableId() {
        if (entityCount == maxId) {
            throw new IndexOutOfBoundsException("All 8 slots for graphs are taken. Please remove some before creating new ones.");
        }
        while (entities.getIfPresent(++entityIndexCounter) != null) {
            entityIndexCounter %= maxId;
        }
        return entityIndexCounter;
    }
}
