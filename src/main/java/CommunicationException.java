import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class CommunicationException extends IOException {
    public CommunicationException(Logger logger, String s) {
        super(s);
        logger.error(s);
    }
}
