package input;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Consumer;

public class InputEvent {
    @Getter(AccessLevel.PACKAGE)
    private final byte[] keys;
    private final Consumer<Byte> action;

    public InputEvent(Consumer<Byte> action, byte... keys) {
        this.keys = keys;
        this.action = action;
    }

    boolean visit(byte b) {
        if (keys.length == 0 || Arrays.binarySearch(keys, b) >= 0) {
            action.accept(b);
            return true;
        }
        return false;
    }
}
