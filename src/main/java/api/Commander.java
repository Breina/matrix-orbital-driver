package api;

import input.InputEventHandler;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public class Commander {

    @Getter(AccessLevel.PRIVATE)
    private final Consumer<byte[]> commandConsumer;

    @Getter(AccessLevel.PROTECTED)
    private final InputEventHandler input;

    public Commander(Consumer<byte[]> runCommand, InputEventHandler input) {
        this.commandConsumer = runCommand;
        this.input = input;
    }

    protected Commander(Commander parent) {
        commandConsumer = parent.getCommandConsumer();
        input = parent.getInput();
    }

    protected void send(byte[] command) {
        commandConsumer.accept(command);
    }
}
