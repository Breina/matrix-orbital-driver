package api;

import input.Input;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public abstract class Commander {

    @Getter(AccessLevel.PRIVATE)
    private Consumer<byte[]> commandConsumer;

    @Getter(AccessLevel.PROTECTED)
    private Input input;

    public Commander(Commander parent) {
        this.commandConsumer = parent.getCommandConsumer();
        this.input = parent.getInput();
    }

    protected void send(byte[] command) {
        this.commandConsumer.accept(command);
    }
}
