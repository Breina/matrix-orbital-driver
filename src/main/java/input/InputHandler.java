package input;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class InputHandler implements Runnable {

    private final InputStream is;
    private ReadHandler handler;

    public InputHandler(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {

        log.info("Started input thread");

        try {
            for (;;) {
                while (is.available() == 0) {}

                int size = is.available();
                log.trace("Available bytes: " + size);
                byte[] buffer = new byte[size];
                is.readNBytes(buffer, 0, size);

                if (handler != null) {
                    handler.onRead(buffer);
                } else {
                    log.warn("Received bytes, but no handler was present");
                    log.warn(new String(buffer));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInputHandler(ReadHandler handler) {
        this.handler = handler;
    }
}
