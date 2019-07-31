package api.buzzer;

import commands.AbstractCommands;
import lombok.experimental.UtilityClass;

@UtilityClass
class BuzzerCommands extends AbstractCommands {

    byte[] activatePiezoBuzzer(int frequency, int time) {
        checkValidUShort(frequency, time);
        return chain("BB", frequency, 2, time, 2);
    }

    byte[] setDefaultBuzzerBeep(int frequency, int duration) {
        checkValidUShort(frequency, duration);
        return chain("BC", frequency, 2, duration, 2);
    }

    byte[] setKeypadBuzzerBeep(int frequency, int duration) {
        checkValidUShort(frequency, duration);
        return chain("B6", frequency, 2, duration, 2);
    }
}
