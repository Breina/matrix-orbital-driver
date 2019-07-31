package api.drawing;

import lombok.AccessLevel;
import lombok.Getter;

public enum ChartDirection {

    BOTTOM_ORIGIN_LEFT_SHIFT    (0b0000_0000),
    LEFT_ORIGIN_UPWARD_SHIFT    (0b0010_0000),
    TOP_ORIGIN_RIGHT_SHIFT      (0b0100_0000),
    RIGHT_ORIGIN_DOWNWARD_SHIFT (0b0110_0000),
    BOTTOM_ORIGIN_RIGHT_SHIFT   (0b1000_0000),
    LEFT_ORIGIN_DOWNWARD_SHIFT  (0b1010_0000),
    TOP_ORIGIN_LEFT_SHIFT       (0b1100_0000),
    RIGHT_ORIGIN_UPWARD_SHIFT   (0b1110_0000);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    ChartDirection(int id) {
        this.id = (byte) id;
    }
}
