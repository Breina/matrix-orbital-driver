package api.text;

import lombok.AccessLevel;
import lombok.Getter;

public enum HorPos {

    LEFT(0),
    MIDDLE(1),
    RIGHT(2);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    HorPos(int id) {
        this.id = (byte) id;
    }
}