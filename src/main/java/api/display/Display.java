package api.display;

import api.Commander;

import java.awt.*;

public class Display extends Commander {

    public static final int WIDTH = 192, HEIGHT = 64;

    public Display(Commander parent) {
        super(parent);
    }

    /**
     * Turns the display backlight on for a specified length of time. If an inverse display color is used this command will
     * essentially turn on the text.
     */
    public void backlightOn() {
        backlightOn(0);
    }

    /**
     * Turns the display backlight on for a specified length of time. If an inverse display color is used this command will
     * essentially turn on the text.
     *
     * @param minutes Byte Number of minutes to leave backlight on, a value of 0 leaves the display on indefinitely.
     */
    public void backlightOn(int minutes) {
        send(DisplayCommands.backlightOn(0));
    }

    /**
     * Turns the display backlight off. If an inverse display colour is used this command will turn off the text.
     */
    public void backlightOff() {
        send(DisplayCommands.backlightOff());
    }

    /**
     * Immediately sets and saves the backlight brightness. Although brightness can be changed using the set command.
     *
     * @param brightness Byte Brightness level from 0(Dim) to 255(Bright). Default is 255;
     * @param persist    If true, it is reset to this saved value on start up
     */
    public void setBrightness(int brightness, boolean persist) {
        send(
                persist
                        ? DisplayCommands.setAndSaveBrightness(brightness)
                        : DisplayCommands.setBrightness(brightness)
        );
    }

    /**
     * Set the colour of a tri-colour backlight. Only for tri-colour displays. Default is white (255, 255, 255).
     *
     * @param colour The colour to be set.
     */
    public void setBacklightColour(Color colour) {
        send(DisplayCommands.setBacklightColour(colour.getRed(), colour.getGreen(), colour.getBlue()));
    }

    /**
     * Immediately sets the contrast between background and text. If an inverse display color is used this also
     * represents the text brightness.
     *
     * @param contrast Byte Contrast level from 0(Light) to 255(Dark). Default is 128.
     * @param persist  If true, it is reset to this saved value on start up.
     */
    public void setContrast(int contrast, boolean persist) {
        send(
                persist
                        ? DisplayCommands.setContrast(contrast)
                        : DisplayCommands.setAndSaveContrast(contrast)
        );
    }
}
