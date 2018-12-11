import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        System.setProperty("log4j.configurationFile", "log4j.xml");

        new Communicator();
//        new Communicator().test();
//        new Communicator().reset();
    }
}
