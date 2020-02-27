package util;

import commands.Util;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

@Log4j2
public final class StringUtil {
    public static String formatBinary(String action, byte... binary) {
        return String.format(action + ": %s (%s)", Util.byteArrayToHex(binary), new String(binary, US_ASCII)).replace('\n', ' ');
    }
}
