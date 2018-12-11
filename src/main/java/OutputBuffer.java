import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

@Log4j2
public class OutputBuffer implements Runnable, Consumer<byte[]> {

    private static final int DEFAULT_PRIORITY = 10;

    private final Queue<Command> commands = new PriorityBlockingQueue<>();
    private final OutputStream outputStream;

    public OutputBuffer(final OutputStream outputStream) {
        this.outputStream = outputStream;

        new Thread(this).start();
    }

    @Override
    public void accept(final byte[] command) {
        addCommand(command, DEFAULT_PRIORITY);
    }

    public void addCommand(byte[] command, final int priority) {
        commands.add(new Command(command, priority));
        commands.notify();
    }

    @Override
    public void run() {
        while (true) {
            if (commands.isEmpty()) {
                try {
                    commands.wait();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }

                Command command = commands.poll();
                if (command != null) {
                    try {
                        outputStream.write(command.getCommand());
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private class Command implements Comparable<Command> {
        @Getter(AccessLevel.PRIVATE)
        private final byte[] command;
        private final int priority;

        @Override
        public int compareTo(Command o) {
            return Integer.compare(priority, o.priority);
        }
    }
}
