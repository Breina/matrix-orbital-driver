package api.text;

import api.Commander;
import api.util.RollingEntity;

public class Label extends RollingEntity {

    Label(Commander parent) {
        super(parent);
    }

    /**
     * Update a previously created label or scrolling label with new text. Send a null character (empty string) to clear.
     *
     * @param text String Information to display in the label.
     */
    public void setText(String text) {
        send(TextCommands.updateLabel(getId(), text));
    }
}
