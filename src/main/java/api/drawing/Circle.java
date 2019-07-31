package api.drawing;

import api.Commander;
import lombok.Setter;
import lombok.experimental.Accessors;

public class Circle extends Commander {

    private final int x, y, radius;

    @Accessors(fluent = true, chain = true)
    @Setter
    private boolean filled = false;

    public Circle(Commander parent, int x, int y, int radius) {
        super(parent);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw() {
        // @formatter:off
        send(
                !filled
                        ? DrawingCommands.drawCircle(x, y, radius)
                        : DrawingCommands.drawFilledCircle(x, y, radius)
        );
        // @formatter:on
    }
}
