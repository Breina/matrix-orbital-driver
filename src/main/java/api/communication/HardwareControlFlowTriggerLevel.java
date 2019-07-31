package api.communication;

import lombok.AccessLevel;
import lombok.Getter;

public enum HardwareControlFlowTriggerLevel {

    BYTES_1(0),
    BYTES_4(1),
    BYTES_8(2),
    BYTES_14(3);

    @Getter(AccessLevel.PACKAGE)
    private final byte level;

    HardwareControlFlowTriggerLevel(int level) {
        this.level = (byte) level;
    }
}
