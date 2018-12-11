package api.drawing;

import lombok.AccessLevel;
import lombok.Getter;

public enum GraphType {

    DIRECTION_VERTICAL_BASE_BOTTOM(0),
    DIRECTION_HORIZONTAL_BASE_LEFT(1),
    DIRECTION_VERTICAL_BASE_TOP(2),
    DIRECTION_HORIZONTAL_BASE_RIGHT(3);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    GraphType(int id) {
        this.id = (byte) id;
    }
}
