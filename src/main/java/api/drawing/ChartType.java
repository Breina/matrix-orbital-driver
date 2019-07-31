package api.drawing;

import lombok.AccessLevel;
import lombok.Getter;

public enum ChartType {

    BAR(0),
    LINE(1),
    STEP(2),
    BOX(3),
    NINESLICE(4),
    SEPARATED_BAR(5),
    SEPARATED_BOX(6);

    @Getter(AccessLevel.PACKAGE)
    private final byte id;

    ChartType(int id) {
        this.id = (byte) id;
    }
}
