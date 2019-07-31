package api.animation;

import com.google.common.primitives.Bytes;
import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class AnimationCommands extends AbstractCommands {

    byte[] uploadAnimationFile(int fileID, byte[] data) {
       checkRange(fileID, 0, 1023);
       return Bytes.concat(chain("5C04", fileID, 2, data.length, 4), data);
    }

    byte[] displayAnimation(int id, int fileID, int x, int y) {
        checkRange(id, 0, 15);
        checkRange(fileID, 0, 1023);
        return chain("C1", id, 1, fileID, 2, x, 1, y, 1);
    }

    byte[] deleteAnimation(int id) {
        checkRange(id, 0, 15);
        return chain("C7",  id, 1);
    }

    byte[] startStopAnimation(int id, boolean start) {
        checkRange(id, 0, 15);
        return chain("C2", id, 1, start ? 1 : 0, 1);
    }

    byte[] setAnimationFrame(int id, int frame) {
        checkRange(id, 0, 15);
        checkRange(frame, 0, 31);
        return chain("C5", id, 1, frame, 1);
    }

    byte[] getAnimationFrame(int id) {
        checkRange(id, 0, 15);
        return chain("C4", id, 1);
    }
}
