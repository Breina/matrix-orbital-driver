package api.communication;

import lombok.AccessLevel;
import lombok.Getter;

public enum ControlFlowMode {

    NONE(0),
    SOFTWARE(1),
    HARDWARE(2);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    ControlFlowMode(int id) {
        this.id = (byte) id;
    }
}
