package api.text;

import api.Commander;
import api.util.RollingEntity;

public class TextWindow extends RollingEntity {

    TextWindow(Commander parent) {
        super(parent);
    }

    /**
     * Writes text to this TextWindow.
     *
     * @param text The text to write.
     */
    public void write(String text) {
        send(TextCommands.setTextWindow(getId()));
        send(TextCommands.write(text));
    }

    /**
     * Clear the contents of a specific text window, similar to the clear screen command.
     */
    public void clear() {
        send(TextCommands.clearTextWindow(getId()));
    }
}
