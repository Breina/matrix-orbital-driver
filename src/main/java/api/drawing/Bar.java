package api.drawing;

import api.Commander;
import api.util.RollingEntity;

public class Bar extends RollingEntity {

    Bar(Commander parent) {
        super(parent);
    }

    /**
     * Fill in a portion of a bar graph after initialization. Any old value will be overwritten by the new. Setting a value of
     * zero before setting a new value will restore a graph should it become corrupted.
     *
     * @param value Byte Portion of graph to fill in pixels, will not exceed display bounds.
     */
    public void updateValue(int value) {
        send(DrawingCommands.drawBarGraph(getId(), value));
    }
}
