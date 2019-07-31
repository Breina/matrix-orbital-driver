package api.drawing;

import api.Commander;
import lombok.Setter;
import lombok.experimental.Accessors;

public class Ellipse extends Commander {

    private final int x, y, xRadius, yRadius;

    @Accessors(fluent = true, chain = true)
    @Setter
    private boolean filled = false;

    public Ellipse(Commander parent, int x, int y, int xRadius, int yRadius) {
        super(parent);
        this.x = x;
        this.y = y;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    public void draw() {
        // @formatter:off
        send(
                !filled
                        ? DrawingCommands.drawEllipse(x, y, xRadius, yRadius)
                        : DrawingCommands.drawFilledEllipse(x, y, xRadius, yRadius)
        );
        // @formatter:on
    }
}
