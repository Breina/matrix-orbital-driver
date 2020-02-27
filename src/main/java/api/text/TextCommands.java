package api.text;

import api.font.Font;
import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import commands.CommonCommands;
import commands.Util;
import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@UtilityClass
class TextCommands extends AbstractCommands {

    private static final Charset CHARSET = StandardCharsets.US_ASCII;

    byte[] write(String text) {
        return text.getBytes(CHARSET);
    }

    byte[] clear() {
        return CommonCommands.clear();
    }

    byte[] goHome() {
        return chain("48");
    }

    byte[] setCursorPosition(int characterColumn, int characterRow) {
        return chain("47", characterColumn, 1, characterRow, 1);
    }

    byte[] setCursorCoordinate(int x, int y) {
        checkBounds(x, y);
        return chain("79", x, 1, y, 1);
    }

    byte[] getStringExtents(String text) {
        return Bytes.concat(chain("29"), text.getBytes(CHARSET), new byte[]{0});
    }

    byte[] initializeTextWindow(int id, int x1, int y1, int x2, int y2, Font font, int charSpace, int lineSpace, int scroll) {
        checkRange(id, 0, 15);
        checkBounds(x1, y1, x2, y2, true);

        return chain("2B", id, 1, x1, 1, y1, 1, x2, 1, y2, 1, font.getId(), 2, charSpace, 1, lineSpace, 1, scroll, 1);
    }

    byte[] setTextWindow(int id) {
        checkRange(id, 0, 15);
        return chain("2A", id, 1);
    }

    byte[] clearTextWindow(int id) {
        checkRange(id, 0, 15);
        return chain("2C", id, 1);
    }

    byte[] initializeLabel(int id, int x1, int y1, int x2, int y2, VertPos vertPos, HorPos horPos, Font font, boolean background, int charspace) {
        checkRange(id, 0, 15);
        checkBounds(x1, y1, x2, y2, true);

        return chain("2D",
                id, 1,
                x1, 1, y1, 1,
                x2, 1, y2, 1,
                vertPos.getId(), 1,
                horPos.getId(), 1,
                font == null ? 1 : font.getId(), 2,
                Util.boolToByte(background), 1,
                charspace, 1);
    }

    byte[] initializeScrollingLabel(int id, int x1, int y1, int x2, int y2, VertPos vertPos, Direction direction, Font font, boolean background, int charspace, int delayMs) {
        checkRange(id, 0, 15);
        checkBounds(x1, y1, x2, y2, true);

        return chain("2F",
                id, 1,
                x1, 1, y1, 1,
                x2, 1, y2, 1,
                vertPos.getId(), 1,
                direction.getId(), 1,
                font == null ? 1 : font.getId(), 2,
                Util.boolToByte(background), 1,
                charspace, 1,
                delayMs, 2);
    }

    byte[] updateLabel(int id, String text) {
        checkRange(id, 0, 15);
        return Bytes.concat(chain("2E", id, 1), text.getBytes(CHARSET), new byte[]{0});
    }

    byte[] autoScrollOn() {
        return chain("51");
    }

    byte[] autoScrollOff() {
        return chain("52");
    }
}
