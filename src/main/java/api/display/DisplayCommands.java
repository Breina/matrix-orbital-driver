package api.display;

import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class DisplayCommands extends AbstractCommands {

    byte[] backlightOn(int minutes) {
        checkRange(minutes, 0, 255);
        return chain("42", minutes, 1);
    }

    byte[] backlightOff() {
        return chain("46");
    }

    byte[] setBrightness(int brightness) {
        checkRange(brightness, 0, 255);
        return chain("99", brightness, 1);
    }

    byte[] setAndSaveBrightness(int brightness) {
        checkRange(brightness, 0, 255);
        return chain("98", brightness, 1);
    }

    byte[] setBacklightColour(int red, int green, int blue) {
        checkRange(red, 0, 255);
        checkRange(green, 0, 255);
        checkRange(blue, 0, 255);
        return chain("82", red, 1, green, 1, blue, 1);
    }

    byte[] setContrast(int contrast) {
        checkRange(contrast, 0, 255);
        return chain("50", contrast, 1);
    }

    byte[] setAndSaveContrast(int contrast) {
        checkRange(contrast, 0, 255);
        return chain("91", contrast, 1);
    }
}
