package api.text;

import api.Commander;

public class Cursor extends Commander {

    Cursor(Commander parent) {
        super(parent);
    }

    /**
     * Writes the text to the cursor with the current font.
     *
     * @param text The text to be written.
     */
    public void write(String text) {
        send(TextCommands.write(text));
    }

    /**
     * Returns the cursor to the top left of the screen.
     */
    public void goHome() {
        send(TextCommands.goHome());
    }

    /**
     * Sets the cursor to a specific cursor position where the next transmitted character is printed.
     *
     * @param textColumn Byte Value between 1 and number of character columns.
     * @param textRow Byte Value between 1 and number of character rows.
     */
    public void setPosition(int textColumn, int textRow) {
        send(TextCommands.setCursorPosition(textColumn, textRow));
    }

    /**
     * Sets the cursor to an exact pixel position where the next transmitted character is printed.
     *
     * @param x Byte Value between 1 and screen width, represents leftmost character position.
     * @param y Byte Value between 1 and screen height, represents topmost character position.
     */
    public void setCoordinate(int x, int y) {
        send(TextCommands.setCursorCoordinate(x, y));
    }

    /**
     * Sets the autoscroll setting for when the end of the screen is reached. Display default is Auto Scroll on.
     *
     * @param autoScroll
     * When true: The entire contents of screen are shifted up one line when the end of the screen is reached.<br/>
     * When false: New text is written over the top line when the end of the screen is reached.
     */
    public void setAutoScroll(boolean autoScroll) {
        send(autoScroll ? TextCommands.autoScrollOn() : TextCommands.autoScrollOff());
    }
}
