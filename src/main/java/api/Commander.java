package api;

import com.CommunicationException;
import input.InputEventHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public class Commander {

    @Getter(AccessLevel.PRIVATE)
    private final Consumer<byte[]> commandConsumer;

    @Getter(AccessLevel.PROTECTED)
    private final InputEventHandler input;

    public Commander(Consumer<byte[]> runCommand, InputEventHandler input) {
        commandConsumer = runCommand;
        this.input = input;
    }

    protected Commander(Commander parent) {
        commandConsumer = parent.getCommandConsumer();
        input = parent.getInput();
    }

    protected void send(byte... command) {
        commandConsumer.accept(command);
    }

    protected void uploadFile(byte[] uploadCommand) throws CommunicationException {
        Future<Byte> response = input.expect(1, 8);

        send(uploadCommand);

        try {
            switch (response.get()) {
                case 1:
                    return;
                case 8:
                    throw new CommunicationException("File upload failed.");
                default:
                    throw new IllegalStateException("Unexpected value: " + response.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new CommunicationException("Could not upload file: " + e.getMessage(), e);
        }
    }
}
