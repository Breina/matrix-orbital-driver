package api.drawing;

import api.Commander;
import lombok.Getter;

public class LineBuilder extends Commander {

    private boolean jumped;

    @Getter
    private int x, y;

    LineBuilder(Commander parent, int x, int y) {
        super(parent);
        setCords(x, y, true);
    }

    public void jump(int x, int y) {
        setCords(x, y, true);
    }

    /**
     * Draw a line from the last point drawn to the coordinate specified using the current drawing colour.
     *
     * @param x Byte Left coordinate of terminus.
     * @param y Byte Top coordinate of terminus.
     */
    public void to(int x, int y) {
        if (jumped)
            send(DrawingCommands.drawLine(this.x, this.y, x, y));
        else
            send(DrawingCommands.continueLine(x, y));

        setCords(x, y, false);
    }

    /**
     * Draw a line relative from the last point drawn.
     *
     * @param dx Byte Relative x position.
     * @param dy Byte Relative y position.
     */
    public void toRelative(int dx, int dy) {
        to(x + dx, y + dy);
    }

    private void setCords(int x, int y, boolean jumped) {
        this.x = x;
        this.y = y;
        this.jumped = jumped;
    }
}

