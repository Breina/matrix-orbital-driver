package api.drawing;

import api.Commander;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Supplier;

public class Rectangle extends Commander {

    private final int x0, y0, x1, y1;
    private final Supplier<Boolean> foregroundGetter;

    @Accessors(fluent = true, chain = true)
    @Setter
    private int cornerRadius = 0;

    @Accessors(fluent = true, chain = true)
    @Setter
    private boolean filled = false;

    Rectangle(Commander commander, int x0, int y0, int x1, int y1, Supplier<Boolean> foregroundGetter) {
        super(commander);
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.foregroundGetter = foregroundGetter;
    }

    public void draw() {
        int color = foregroundGetter.get() ? 1 : 0;

        // @formatter:off
        send(
                cornerRadius == 0
                        ? !filled
                                ? DrawingCommands.drawRectange(color, x0, y0, x1, y1)
                                : DrawingCommands.drawFilledRectange(color, x0, y0, x1, y1)
                        : !filled
                                ? DrawingCommands.drawRoundedRectange(x0, y0, x1, y1, cornerRadius)
                                : DrawingCommands.drawFilledRoundedRectange(x0, y0, x1, y1, cornerRadius)
        );
        // @formatter:on
    }
}
