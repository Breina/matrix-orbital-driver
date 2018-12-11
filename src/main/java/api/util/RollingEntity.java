package api.util;

import api.Commander;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class RollingEntity extends Commander {

    @Setter(AccessLevel.PACKAGE)
    private Runnable onRemove;

    @Setter(AccessLevel.PACKAGE)
    @Getter
    private byte id;

    public RollingEntity(Commander parent) {
        super(parent);
    }

    /**
     * Removes this object and frees the ID to be used by others.
     */
    public void remove() {
        onRemove.run();
    }
}
