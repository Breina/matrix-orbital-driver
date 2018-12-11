package api.text;

import lombok.AccessLevel;
import lombok.Getter;

public enum VertPos {

    TOP(0),
    MIDDLE(1),
    BOTTOM(2);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    VertPos(int id) {
        this.id = (byte) id;
    }
}
