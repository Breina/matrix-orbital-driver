package api.text;

import lombok.AccessLevel;
import lombok.Getter;

public enum Direction {

    LEFT(0),
    RIGHT(1),
    BOUNCE(2);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    Direction(int id) {
        this.id = (byte) id;
    }
}
