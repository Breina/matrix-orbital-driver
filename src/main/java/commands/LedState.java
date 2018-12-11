package commands;

import lombok.AccessLevel;
import lombok.Getter;

public enum LedState {

    OFF(0),
    GREEN(1),
    RED(2),
    YELLOW(3);

    @Getter(AccessLevel.PACKAGE)
    private final byte colour;

    LedState(int colour) {
        this.colour = (byte) colour;
    }
}
