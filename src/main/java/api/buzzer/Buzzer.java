package api.buzzer;

import api.Commander;
import api.CommunicationException;

import java.io.IOException;

public class Buzzer extends Commander {

    private MusicPlayer musicPlayer;

    public Buzzer(Commander parent) {
        super(parent);
    }

    /**
     * Activates a buzz of specific frequency from the onboard piezo buzzer for a specified length of time.
     *
     * @param frequency Short Frequency of buzz in hertz.
     * @param time      Short *Duration of the beep in milliseconds.
     */
    public void activate(int frequency, int time) {
        send(BuzzerCommands.activatePiezoBuzzer(frequency, time));
    }

    /**
     * Set the frequency and duration of the default beep transmitted when the bell character is transmitted.
     *
     * @param frequency Short Frequency of the beep in Hertz, default 440Hz.
     * @param duration  Short Duration of the beep in milliseconds, default 100ms.
     */
    public void setBellBeep(int frequency, int duration) {
        send(BuzzerCommands.setDefaultBuzzerBeep(frequency, duration));
    }

    /**
     * Disables the beep when a bell character is transmitted.
     */
    public void disableBellBeep() {
        send(BuzzerCommands.setDefaultBuzzerBeep(0, 0));
    }

    /**
     * Set the frequency and duration of the default beep transmitted when a key is pressed.
     *
     * @param frequency Short Frequency of the beep in Hertz, default is 0 or off.
     * @param duration  Short Duration of the beep in milliseconds, default is 0 or off.
     */
    public void setKeyboardBeep(int frequency, int duration) {
        send(BuzzerCommands.setKeypadBuzzerBeep(frequency, duration));
    }

    /**
     * Disables the beep when a key is pressed.
     */
    public void disableKeyboardBeep() {
        send(BuzzerCommands.setKeypadBuzzerBeep(0, 0));
    }

    public MusicPlayer getMusicPlayer() throws CommunicationException {
        if (musicPlayer == null) {
            try {
                musicPlayer = new MusicPlayer(this);
            } catch (IOException e) {
                throw new CommunicationException(e);
            }
        }
        return musicPlayer;
    }
}
