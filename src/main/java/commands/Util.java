package commands;

public final class Util {
    private Util() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static byte hex(String hex) {
        int length = hex.length();
        if (length > 2)
            throw new IllegalArgumentException("The hex value can only be one byte long.");

        byte output;
        int index = 0;
        if (length == 2) {
            output = (byte) (Character.digit(hex.charAt(index++), 16) << 4);
        } else {
            output = 0;
        }
        return (byte) (output | Character.digit(hex.charAt(index), 16));
    }

    public static byte boolToByte(boolean b) {
        return b ? (byte) 1 : (byte) 0;
    }

    public static byte[] shortToBytes(short s) {
        return new byte[]{
                (byte) (s & 0xff),
                (byte) ((s >> 8) & 0xff)
        };
    }

    public static byte[] intToBytes(int i) {
        return new byte[]{
                (byte) (i & 0xff),
                (byte) ((i >> 8) & 0xff),
                (byte) ((i >> 16) & 0xff),
                (byte) ((i >> 24) & 0xff)
        };
    }

    public static String byteArrayToHex(byte... a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02X ", b));
        return sb.toString();
    }
}
