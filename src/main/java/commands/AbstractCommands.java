package commands;

import static commands.Util.hex;
import static commands.Util.intToBytes;
import static commands.Util.shortToBytes;

public abstract class AbstractCommands {

    private static final byte COMMAND_START = hex("FE");

    private static final int MAX_UBYTE = Byte.MAX_VALUE * 2 + 1;
    private static final int MAX_USHORT = Short.MAX_VALUE * 2 + 1;;
    private static final long MAX_UINT = 2L * Integer.MAX_VALUE + 1;

    protected static void checkValidUByte(int... values) {
        for (int value : values) {
            checkRange(value, 0, MAX_UBYTE);
        }
    }

    protected static void checkValidUShort(int... values) {
        for (int value : values) {
            checkRange(value, 0, MAX_USHORT);
        }
    }

    protected static void checkValidUInt(long... values) {
        for (long value : values) {
            checkRange(value, 0L, MAX_UINT);
        }
    }

    protected static void checkBounds(int x, int y) {
        checkRange(x, 1, 192);
        checkRange(y, 1, 64);
    }

    protected static void checkBounds(int x1, int y1, int x2, int y2, boolean checkOrder) {
        checkBounds(x1, y1);
        checkBounds(x2, y2);

        if (!checkOrder)
            return;

        if (x2 < x1)
            throw new IllegalArgumentException("x2 should be greater than x1.");
        if (y2 < y1)
            throw new IllegalArgumentException("y2 should be greater than y1.");
    }

    protected static <T extends Comparable<T>> void checkRange(T value, T min, T max) {
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("The value %d should be between %d and %d", value, min, max));
    }

    protected static byte[] chain(String code, int... parts) {
        int codeLength = code.length();
        if (codeLength % 2 != 0)
            throw new IllegalArgumentException("The code's length should be divisible by 2, but is " + codeLength + ".");

        int partsLength = parts.length;
        if (partsLength % 2 != 0)
            throw new IllegalArgumentException("The parts' length should be divisible by 2 but is " + partsLength + ".");

        int summy = 0;
        for (int i = 1; i < partsLength; i += 2)
            summy += parts[i];

        byte[] result = new byte[summy + 1 + codeLength / 2];
        result[0] = COMMAND_START;

        int resultIndex = 1;
        for (int i = 0; i < codeLength; i += 2)
            result[resultIndex++] = Util.hex(code.substring(i, i + 2));

        int index = 2;
        for (int i = 0; i < partsLength - 1; i += 2) {
            int value = parts[i];
            int split = parts[i + 1];

            switch (split) {
                case 1:
                    checkRange(value, 0, MAX_UBYTE);
                    result[index] = (byte) value;
                    break;
                case 2:
                    checkRange(value, 0, MAX_USHORT);
                    System.arraycopy(shortToBytes((short) value), 0, result, index, 2);
                    break;
                case 4:
                    System.arraycopy(intToBytes(value), 0, result, index, 4);
                    break;
                default:
                    throw new IllegalArgumentException("The split value should be either 1, 2 or 4, but was: " + split);
            }

            index += split;
        }

        return result;
    }

    private static byte[] chain(byte... bytes) {
        byte[] returnValue = new byte[bytes.length + 1];
        returnValue[0] = COMMAND_START;
        System.arraycopy(bytes, 0, returnValue, 1, bytes.length);
        return returnValue;
    }
}
