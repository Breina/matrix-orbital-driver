package api.nineslice;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class NineSliceCommands extends AbstractCommands {

    byte[] upload9SliceFile(int id, byte[] data) {
        checkRange(id, 0, 1023);
        return Bytes.concat(chain("5C03",  id, 2, data.length, 4), data);
    }

    byte[] upload9SliceMask(int id, byte[] data) {
        checkRange(id, 0, 1023);
        return Bytes.concat(chain("5C06",  id, 2, data.length, 4), data);
    }

    byte[] display9Slice(int id, int x1, int y1, int x2, int y2) {
        checkRange(id, 0, 1023);
        checkBounds(x1, y1, x2, y2, true);
        return chain("FB", id, x1, y1, x2, y2);
    }
}
