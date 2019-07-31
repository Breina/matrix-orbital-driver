package api.drawing;

import api.Commander;
import api.util.RollingEntity;

public class StripChart extends RollingEntity {

    private final int min, max;

    StripChart(Commander parent, int min, int max) {
        super(parent);
        this.min = min;
        this.max = max;
    }

    /**
     * Shift the specified strip chart and draw a new value.
     *
     * @param value Short Value to add to the chart.
     */
    public void updateValue(int value) {
        if (value < min || value > max)
            throw new IllegalArgumentException(String.format("The updated value (%d) is outside the chart's range: [%d, %d]", value, min, max));

        send(DrawingCommands.updateStripChart(getId(), value));
    }
}
