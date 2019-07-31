package api.communication;

import lombok.AccessLevel;
import lombok.Getter;

public enum BaudRate {

    RATE_9600   (9600,  207),
    RATE_14400  (14400, 138),
    RATE_19200  (19200, 103),
    RATE_28800  (28800, 68),
    RATE_38400  (38400, 51),
    RATE_57600  (57600, 34),
    RATE_76800  (76800, 25),
    RATE_115200 (115200,16);

    @Getter(AccessLevel.PACKAGE)
    private final byte command;

    @Getter
    private final int baud;

    BaudRate(int baud, int speed) {
        this.baud = baud;
        this.command = (byte) speed;
    }
}
