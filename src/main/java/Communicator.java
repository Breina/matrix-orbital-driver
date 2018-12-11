import api.communication.BaudRate;
import api.communication.Communication;
import api.communication.CommunicationCommands;
import commands.Util;
import input.Input;
import lombok.extern.log4j.Log4j2;
import util.StringUtil;

import java.io.IOException;

@Log4j2
public class Communicator {

    private static final BaudRate BAUD_RATE = BaudRate.RATE_115200;

    private final COMWrapper comWrapper;
    private final Input input;

    public Communicator() throws CommunicationException {
        this.comWrapper = new COMWrapper();
        this.input = new Input();

        setup();
    }

    private void setup() {
//        run(resetLCD());
//        comWrapper.resetBaudRate();
//        run(resetLCD());

//        run(setBaudRate(BAUD_RATE));
//        comWrapper.setBaudRate(BAUD_RATE.getBaud());
//        log.info("Set baud rate to " + BAUD_RATE.getBaud());

        comWrapper.setReadHandler(input);
    }

    public void test() throws InterruptedException, IOException {

//        run(setLed(VertPos.BOTTOM, LedState.OFF));
//        run(setLed(VertPos.MIDDLE, LedState.OFF));
//        run(setLed(VertPos.TOP, LedState.OFF));
//
//        run(setLed(VertPos.BOTTOM, LedState.RED));
//
////        run(clear());
////        run(setKeypadBuzzerBeep(440, 100));
////        run(autoTransmitKeyPressesOn());
////        run(clearKeyBuffer());
//
//        run(createLabel(3, 10,  10, 60, 50,  VertPos.MIDDLE, HorPos.MIDDLE, 0,  false, 0));
//        run(updateLabel(3, "CPU"));
//
////        MusicPlayer musicPlayer = new MusicPlayer((freq, length) -> run(buzz(freq, length)));
////        musicPlayer.playMidi("D:\\Libraries\\Music\\MIDI\\Downloads\\Comptine d un autre été (Kyle Landry).mid", 2f);
////        musicPlayer.playMidi("D:\\Libraries\\Music\\MIDI\\strauss-the-blue-danube.mid", 1f);
////        musicPlayer.playMidi("D:\\Libraries\\Music\\MIDI\\bach-toccata-and-fugue-in-d-minor.mid", 3f);
//
////        int delay = 500;
////        int f0 = 440;
////        int f1 = 523;
////        int f1 = 880;
//
////        for (float polyTime = 50; polyTime > 1; polyTime /= 1.01) {
////            int round = Math.round(polyTime);
////            run(buzz(f0, round));
////            Thread.sleep(round);
////
////            run(buzz(f1, round));
////            Thread.sleep(round);
////        }
//
//
//        run(setBacklightColor(Color.BLUE));
//        run(setBacklightOn());
//        run(clear());
//
////        run(setLed(VertPos.BOTTOM, LedState.RED));
////        run(echo("test"));
////        run(setLed(VertPos.BOTTOM, LedState.GREEN));
//
////        run(initializeStripChart(0, 0, 0, 191, 63, 0, 100, 1, ChartType.LINE, ChartDirection.BOTTOM_ORIGIN_RIGHT_SHIFT, 0));
////        run(initializeStripChart(0, 171, 53, 191, 63, 0, 100, 1, ChartType.LINE, ChartDirection.BOTTOM_ORIGIN_RIGHT_SHIFT, 0));
////
////        JavaSysMon javaSysMon = new JavaSysMon();
////        CpuTimes oldCpuTime = javaSysMon.cpuTimes();
////        for (;;) {
////            CpuTimes newCpuTime = javaSysMon.cpuTimes();
////            run(updateStripChart(0, ((int) (100 * newCpuTime.getCpuUsage(oldCpuTime)))));
////
////            oldCpuTime = newCpuTime;
////
////
////            Thread.sleep(100);
////        }
//
////        Random r = new Random(System.currentTimeMillis());
////        for (int i = 0; i < 250; i++) {
////            run(updateStripChart(0, r.nextInt(10)));
////
////            run(setBacklightColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))));
////        }
//
//
//        run(setLed(VertPos.BOTTOM, LedState.GREEN));
    }

    private void run(byte[] command) {
        log.info(StringUtil.formatBinary("Sending", command));
        try {
            comWrapper.write(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        run(CommunicationCommands.reset());
        input.blockUntilReceived(Util.hex("fe"), Util.hex("d4"));
    }
}
