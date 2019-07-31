package api.font;

import api.Commander;
import api.util.RollingEntity;
import lombok.Setter;

@Setter
public class Font extends RollingEntity {

    static int
            currentLineMargin = 0,
            currentTopMargin = 0,
            currentCharSpace = 0,
            currentLineSpace = 1,
            currentScroll = 1;

    /**
     * Space between left of display and first column of text. Default 0.
     */
    private int lineMargin;

    /**
     * Space between top of display area and first row of text. Default 0.
     */
    private int topMargin;

    /**
     * Space between characters. Default 0.
     */
    private int charSpace;

    /**
     * Space between character rows. Default 1.
     */
    private int lineSpace;

    /**
     * Point at which text scrolls up screen to display additional rows. Default 1.
     */
    private int scroll;

    Font(Commander parent, FontBuilder fontBuilder) {
        super(parent);

        lineMargin = fontBuilder.getLineMargin();
        topMargin = fontBuilder.getTopMargin();
        charSpace = fontBuilder.getCharSpace();
        lineSpace = fontBuilder.getLineSpace();
        scroll = fontBuilder.getScroll();
    }

    public void setCurrent() {
        send(FontCommands.setCurrentFont(this));
        if (!areFontMetricsDefault()) {
            setFontMetrics();
        }
    }

    private void setFontMetrics() {
        send(FontCommands.setFontMetrics(
                currentLineMargin = lineMargin,
                currentTopMargin = topMargin,
                currentCharSpace = charSpace,
                currentLineSpace = lineSpace,
                currentScroll = scroll
        ));
    }

    private boolean areFontMetricsDefault() {
        return currentLineMargin == lineMargin &&
                currentTopMargin == topMargin &&
                currentCharSpace == charSpace &&
                currentLineSpace == lineSpace &&
                currentScroll == scroll;
    }
}
