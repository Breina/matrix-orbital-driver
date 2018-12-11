package api.text;

import api.Commander;
import api.communication.SoftwareControlFlow;
import api.font.Font;
import api.util.RollingID;
import lombok.Getter;

public class Text extends Commander {

    @Getter(lazy = true)
    private final Cursor cursor = new Cursor(this);

    private RollingID<TextWindow> textWindows = new RollingID<>(16);
    private RollingID<Label> labels = new RollingID<>(16);

    public Text(Commander parent) {
        super(parent);
    }

    /**
     * Clears the contents of the screen.
     */
    public void clear() {
        send(TextCommands.clear());
    }

    /**
     * Read the size of the rectangle that the specified string would occupy if it was rendered with the current font.
     *
     * @param text String String on which to preform extents calculation. A single line of text is assumed.
     *
     * @return Byte(s) Width and height of the string in pixels. A width greater than the screen will return 0.
     */
    public void getStringExtents(String text) {
        send(TextCommands.getStringExtents(text));
        throw new RuntimeException("Not implemented");
    }

    /**
     * Designates a portion of the screen to which text can be confined. Font commands affect only the current window,
     * default (entire screen) is window 0.
     *
     * @param x0 Byte Unique text window identification number, value between 0 and 15. Byte Leftmost coordinate.
     * @param y0 Byte Topmost coordinate.
     * @param x1 Byte Rightmost coordinate.
     * @param y1 Byte Bottommost coordinate.
     * @param font Short Unique font ID to use for this window, value between 0 and 1023.
     * @param charSpace Byte Spacing between characters to use for this window.
     * @param lineSpace Byte Spacing between lines to use for this window.
     * @param scroll Byte Number of pixel rows to write to before scrolling text.
     *
     * @return The {@link TextWindow} instance.
     */
    public TextWindow createNewTextWindow(int x0, int y0, int x1, int y1, Font font, int charSpace, int lineSpace, int scroll) {
        return textWindows.register(
                id -> send(TextCommands.initializeTextWindow(id, x0, y0, x1, y1, font, charSpace, lineSpace, scroll)),
                new TextWindow(this));
    }

    /**
     * Designates a portion of the screen that can be easily updated with one line of text, often used to display variables.
     *
     * @param x1 Byte Leftmost coordinate.
     * @param y1 Byte Topmost coordinate.
     * @param x2 Byte Rightmost coordinate.
     * @param y2 Byte Bottommost coordinate.
     * @param vertPos Byte Vertical justification of the label text; 0 for top, 1 for middle, or 2 for bottom.
     * @param horPos Byte Horizontal justification of the label text; 0 for left, 1 for centre, or 2 for right.
     * @param font Short Unique font ID to use for this label, value between 0 and 1023.
     * @param background Byte State of the pixels in the label region that is not occupied by text; 0 for off or 1 for on.
     * @param charspace Byte Spacing between characters to use for this label.
     *
     * @return The {@link Label} instance.
     */
    public Label createNewLabel(int x1, int y1, int x2, int y2, VertPos vertPos, HorPos horPos, Font font, boolean background, int charspace) {
        return labels.register(
                id -> send(TextCommands.initializeLabel(id, x1, y1, x2, y2, vertPos, horPos, font, background, charspace)),
                new Label(this));
    }

    /**
     * Designates a portion of the screen that can be easily updated with one line of text, often used to display variables.
     *
     * @param x1 Byte Leftmost coordinate.
     * @param y1 Byte Topmost coordinate.
     * @param x2 Byte Rightmost coordinate.
     * @param y2 Byte Bottommost coordinate.
     * @param vertPos Byte Vertical justification of the label text; 0 for top, 1 for middle, or 2 for bottom.
     * @param direction Byte Direction of the scrolling behavior; 0 for left, 1 for right, or 2 for bounce.
     * @param font Short Unique font ID to use for this label, value between 0 and 1023.
     * @param background Byte State of the pixels in the label region that is not occupied by text; 0 for off or 1 for on.
     * @param charspace Byte Spacing between characters to use for this label.
     * @param delay Short Time in milliseconds to elapse between characters printed.
     *
     * @return The {@link Label} instance.
     */
    public Label createNewScrollingLabel(int x1, int y1, int x2, int y2, VertPos vertPos, Direction direction, Font font, boolean background, int charspace, int delay) {
        return labels.register(
                id -> send(TextCommands.initializeScrollingLabel(id, x1, y1, x2, y2, vertPos, direction, font, background, charspace, delay)),
                new Label(this));
    }
}
