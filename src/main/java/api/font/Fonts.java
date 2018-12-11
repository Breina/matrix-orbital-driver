package api.font;

import api.Commander;
import api.util.RollingID;

public class Fonts extends Commander {

    private final RollingID<Font> fonts = new RollingID<>(1024);

    public Fonts(Commander parent) {
        super(parent);
    }

    /**
     * Upload a font to a graphic display. To create a font see the Font File Creation section, for upload protocol see the
     * File Transfer Protocol or XModem Transfer Protocol entries. Default font is ID 1.
     *
     * @param fontData Font file data, see the Font File Creation example.
     *
     * @return A {@link FontBuilder} which is to be used to create the font.
     */
    public FontBuilder createNewFont(byte[] fontData) {
        return new FontBuilder(fontBuilder -> fonts.register(id -> send(FontCommands.uploadFontFile(id, fontData)),
                        new Font(this, fontBuilder)));
    }

    /**
     * Toggle box space on or off. When on, a character sized box is cleared from the screen before a character is
     * written. This eliminates any text or bitmap remnants behind the character. Default is on.
     *
     * @param on boolean true for on or false for off.
     */
    public void setBoxSpaceMode(boolean on) {
        send(FontCommands.setBoxSpaceMode(on));
    }

}
