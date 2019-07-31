//package commands;
//
//import api.text.HorPos;
//import api.text.VertPos;
//
//import java.awt.*;
//
//import static commands.Util.*;
//
//public class CommandFactory extends AbstractCommands {
//
//    private static final int MAX_UBYTE = Byte.MAX_VALUE * 2 + 1;
//    private static final int MAX_USHORT = Short.MAX_VALUE * 2 + 1;;
//    private static final long MAX_UINT = 2L * Integer.MAX_VALUE + 1;
//
//    public static final byte COMMAND_START = hex("FE");
//    public static final byte COMMAND_DEL = hex("FD");
//    public static final byte COMMAND_BAUD = hex("39");
//    public static final byte COMMAND_ECHO = hex("FF");
//    public static final byte COMMAND_NULL = hex("0");
//    public static final byte COMMAND_ETX = hex("3");
//
//
//    public static final byte COMMAND_FILLED_RECTANGLE = hex("78");
//
//    public static final byte COMMAND_LED = hex("5A");
//
//    public static final byte COMMAND_BUZZER = hex("BB");
//
//    public static final byte COMMAND_BACKLIGHT_ON = hex("42");
//    public static final byte COMMAND_BACKLIGHT_OFF = hex("46");
//    public static final byte COMMAND_BACKLIGHT_COLOR = hex("82");
//
//    public static final byte COMMAND_CREATE_LABEL = hex("2D");
//    public static final byte COMMAND_UPDATE_LABEL = hex("2E");
//
//
//
//
//
//
//
//
//    public static byte[] fillRect(boolean on, int x1, int y1, int x2, int y2) {
//        return chain(COMMAND_FILLED_RECTANGLE, (byte) (on ? 1 : 0), (byte) x1, (byte) y1, (byte) x2, (byte) y2);
//    }
//
//    public static byte[] setLed(VertPos pos, LedState state) {
//        return chain(COMMAND_LED, pos.getId(), state.getColour());
//    }
//
//    public static byte[] setBacklightOn() {
//        return chain(COMMAND_BACKLIGHT_ON, (byte) 0);
//    }
//
//    public static byte[] setBacklightOff() {
//        return chain(COMMAND_BACKLIGHT_OFF);
//    }
//
//    public static byte[] setBacklightColor(Color color) {
//        return chain(COMMAND_BACKLIGHT_COLOR, (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
//    }
//
//    public static byte[] buzz(int frequency, int timeMs) {
//        return chain(COMMAND_BUZZER, 1, frequency, 2, timeMs, 2);
//    }
//
//    public static byte[] setKeypadBuzzerBeep(int frequency, int timeMs) {
//        return chain(hex("B6"), 1, frequency, 2, timeMs, 2);
//    }
//
//    public static byte[] clearKeyBuffer() {
//        return chain(hex("45"));
//    }
//
//    public static byte[] autoTransmitKeyPressesOn() {
//        return chain(hex("41"));
//    }
//
//    public static byte[] autoTransmitKeyPressesOff() {
//        return chain(hex("4F"));
//    }
//
//    public static byte[] createLabel(int id, int x1, int y1, int x2, int y2, VertPos vertPos, HorPos horPos, int font, boolean background, int charspace) {
//        checkRange(id, 0, 15);
//        checkRange(font, 0, 1023);
//
//
//    }
//
//    public static byte[] updateLabel(int id, String text) {
//        checkRange(id, 0, MAX_UBYTE);
//
//        byte[] textBytes = text.getBytes();
//        int length = textBytes.length;
//        byte[] cmd = new byte[4 + length + 1];
//        cmd[0] = COMMAND_START;
//        cmd[1] = COMMAND_UPDATE_LABEL;
//        cmd[2] = (byte) id;
//        cmd[3] = COMMAND_NULL;
//        System.arraycopy(textBytes, 0, cmd, 4, length);
//        cmd[cmd.length - 1] = COMMAND_NULL;
//        return cmd;
//    }
//}
