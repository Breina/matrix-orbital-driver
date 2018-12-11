package api.drawing;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import commands.CommonCommands;
import commands.Util;
import lombok.experimental.UtilityClass;

@UtilityClass
class DrawingCommands extends AbstractCommands {

    byte[] clear() {
        return CommonCommands.clear();
    }

    byte[] setDrawingColour(int color) {
        return chain("63", color, 1);
    }

    byte[] drawPixel(int x, int y) {
        checkBounds(x, y);
        return chain("70", x, 1, y, 1);
    }

    byte[] drawLine(int x1, int y1, int x2, int y2) {
        checkBounds(x1, y1, x2, y2, false);
        return chain("6C", x1, 1, y1, 1, x2, 1, y2, 1);
    }

    byte[] continueLine(int x, int y) {
        checkBounds(x, y);
        return chain("65", x, 1, y, 1);
    }

    byte[] drawRectange(int color, int x1, int y1, int x2, int y2) {
        checkBounds(x1, y1, x2, y2, true);
        return chain("72", x1, 1, y1, 1, x2, 1, y2, 1);
    }

    byte[] drawFilledRectange(int color, int x1, int y1, int x2, int y2) {
        checkBounds(x1, y1, x2, y2, true);
        return chain("78", x1, 1, y1, 1, x2, 1, y2, 1);
    }

    byte[] drawRoundedRectange(int x1, int y1, int x2, int y2, int radius) {
        checkBounds(x1, y1, x2, y2, true);
        return chain("80", x1, 1, y1, 1, x2, 1, y2, 1, radius, 1);
    }

    byte[] drawFilledRoundedRectange(int x1, int y1, int x2, int y2, int radius) {
        checkBounds(x1, y1, x2, y2, true);
        return chain("81", x1, 1, y1, 1, x2, 1, y2, 1, radius, 1);
    }

    byte[] drawCircle(int x, int y, int radius) {
        checkBounds(x, y);
        return chain("7B", x, 1, y, 1, radius, 1);
    }

    byte[] drawFilledCircle(int x, int y, int radius) {
        checkBounds(x, y);
        return chain("7C", x, 1, y, 1, radius, 1);
    }

    byte[] drawEllipse(int x, int y, int xRadius, int yRadius) {
        checkBounds(x, y);
        return chain("7D", x, 1, y, 1, xRadius, 1, yRadius, 1);
    }

    byte[] drawFilledEllipse(int x, int y, int xRadius, int yRadius) {
        checkBounds(x, y);
        return chain("7F", x, 1, y, 1, xRadius, 1, yRadius, 1);
    }

    byte[] scrollScreen(int x1, int y1, int x2, int y2, int moveX, int moveY) {
        checkBounds(x1, y1, x2, y2, true);
        checkRange(moveX, (int) Short.MIN_VALUE, (int) Short.MAX_VALUE);
        checkRange(moveY, (int) Short.MIN_VALUE, (int) Short.MAX_VALUE);

        return Bytes.concat(chain("59", x1, 1, y1, 1, x2, 1, y2, 1), Util.shortToBytes((short) moveX), Util.shortToBytes((short) moveY));
    }

    byte[] initializeBarGraph(int id, GraphType type, int x1, int y1, int x2, int y2) {
        checkRange(id, 0, 255);
        checkBounds(x1, y1, x2, y2, true);
        return chain("67", id, 1, type.getId(), 1, x1, 1, y1, 1, x2, 1, y2, 1);
    }

    byte[] initialize9SliceBarGraph(int id, GraphType type, int x1, int y1, int x2, int y2, int fore9Slice, int back9Slice) {
        checkRange(id, 0, 255);
        checkBounds(x1, y1, x2, y2, true);
        return chain("73", id, 1, type.getId(), 1, x1, 1, y1, 1, x2, 1, y2, 1, fore9Slice, 2, back9Slice, 2);
    }

    byte[] drawBarGraph(int id, int value) {
        checkRange(id, 0, 255);
        return chain("69", value, 1);
    }

    byte[] initializeStripChart(int id, int x1, int y1, int x2, int y2, int min, int max, int step, ChartDirection direction, ChartType type, int nineSliceFileID) {
        checkRange(id, 0, 7);
        checkBounds(x1, y1, x2, y2, true);

        return chain(
                "6E",
                id, 1,
                x1, 1, y1, 1,
                x2, 1, y2, 1,
                min, 2,
                max, 2,
                step, 1,
                direction.getId() | type.getId(), 1,
                nineSliceFileID, 2);
    }

    byte[] updateStripChart(int handle, int value) {
        checkRange(handle, 0, 7);
        return chain("6F", handle, 1, value, 2);
    }
}
