package api.bitmap;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class BitmapCommands extends AbstractCommands {

    byte[] uploadBitmapFile(int id, int size) {
        checkRange(id, 0, 1023);
        return Bytes.concat(chain("5E",  id, 2, size, 4));
    }

    byte[] uploadBitmapMask(int id, byte[] maskData) {
        checkRange(id, 0, 1023);
        return Bytes.concat(chain("5C",  id, 2, maskData.length, 4), maskData);
    }

    byte[] drawBitmapFromMemory(int id, int x, int y) {
        checkRange(id, 0, 1023);
        return chain("62", id, 2, x, 1, y, 1);
    }

    byte[] drawPartialBitmap(int id, int x, int y, int xPart, int yPart, int width, int height) {
        checkRange(id, 0, 1023);
        checkBounds(x, y);
        return chain("C0", id, 2, x, 1, y, 1, xPart, 1, yPart, 1, width, 1, height, 1);
    }

    byte[] drawBitmapDirectly(int x, int y, byte[] data) {
        return Bytes.concat(chain("64", x, 1, y, 1), data);
    }
}
