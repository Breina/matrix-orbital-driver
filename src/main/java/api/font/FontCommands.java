package api.font;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class FontCommands extends AbstractCommands {

    byte[] uploadFontFile(int id, byte[] fontData) {
        checkRange(id, 0, 1023);
        return Bytes.concat(chain("24", id, 2, fontData.length, 4), fontData);
    }

    byte[] setCurrentFont(Font font) {
        return chain("31", font.getId(), 2);
    }

    byte[] setFontMetrics(int lineMargin, int topMargin, int charSpace, int lineSpace, int scroll) {
        return chain("32",  lineMargin, 1, topMargin, 1, charSpace, 1, lineSpace, 1, scroll, 1);
    }

    byte[] setBoxSpaceMode(boolean on) {
        return chain("AC", on ? 1 : 0, 1);
    }
}
