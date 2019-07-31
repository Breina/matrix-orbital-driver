package util;

import commands.Util;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;

@Log4j2
public final class StringUtil {
    public static String formatBinary(String action, byte... binary) {
        try {
            return String.format(action + ": %s (%s)", Util.byteArrayToHex(binary), new String(binary, "UTF-8")).replace('\n', ' ');
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            return "";
        }
    }
}
