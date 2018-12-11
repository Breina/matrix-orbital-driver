package api.communication;

import lombok.AccessLevel;
import lombok.Getter;

public enum Protocol {

    RS232(1),
    RS422(1),
    TTL(1),
    USB(1),
    I2C(0);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    Protocol(int id) {
        this.id = (byte) id;
    }
}
