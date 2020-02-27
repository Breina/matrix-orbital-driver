package api.text;

import api.Commander;
import api.util.RollingEntity;
import lombok.NonNull;

public class Label extends RollingEntity {

    private final int xLeft, yTop, xRight, yBottom;

    Label(Commander parent, int xLeft, int yTop, int xRight, int yBottom) {
        super(parent);
        this.xLeft = xLeft;
        this.yTop = yTop;
        this.xRight = xRight;
        this.yBottom = yBottom;
    }

    /**
     * Update a previously created label or scrolling label with new text. Send a null character (empty string) to clear.
     *
     * @param text String Information to display in the label.
     */
    public void setText(String text) {
        send(TextCommands.updateLabel(getId(), text));
    }

    public int getxLeft() {
        return xLeft;
    }

    public int getyTop() {
        return yTop;
    }

    public int getxRight() {
        return xRight;
    }

    public int getyBottom() {
        return yBottom;
    }
}
