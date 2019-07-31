import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        System.setProperty("log4j.configurationFile", "log4j.xml");

        Communicator communicator = new Communicator();
        communicator.test();
//        new Communicator().reset();
    }
}
