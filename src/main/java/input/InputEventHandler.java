package input;

import commands.Util;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static util.StringUtil.formatBinary;

@Log4j2
public class InputEventHandler implements ReadHandler {

    private static final byte
            DEFAULT_VK_TOP = Util.hex("41"),
            DEFAULT_VK_UP = Util.hex("42"),
            DEFAULT_VK_RIGHT = Util.hex("43"),
            DEFAULT_VK_LEFT = Util.hex("44"),
            DEFAULT_VK_CENTER = Util.hex("45"),
            DEFAULT_VK_DOWN = Util.hex("48"),
            DEFAULT_VK_BOTTOM = Util.hex("47");
    private final Queue<InputEvent[]> keyListeners;

    @Getter
    @Accessors(fluent = true)
    private byte
            vk_top = DEFAULT_VK_TOP,
            vk_up = DEFAULT_VK_UP,
            vk_right = DEFAULT_VK_RIGHT,
            vk_left = DEFAULT_VK_LEFT,
            vk_center = DEFAULT_VK_CENTER,
            vk_down = DEFAULT_VK_DOWN,
            vk_bottom = DEFAULT_VK_BOTTOM;

    public InputEventHandler() {
        keyListeners = new ConcurrentLinkedDeque<>();
    }

    public void waitFor(byte... bytes) {
        try {
            expect(bytes).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e);
        }
    }

    public Future<Byte> expect(byte... bytes) {
        CompletableFuture<Byte> future = new CompletableFuture<>();
        keyListeners.add(new InputEvent[]{new InputEvent(b -> future.completeAsync(() -> b), bytes)});
        return future;
    }

    public Future<Byte[]> expectBytes(int count) {
        CompletableFuture<Byte[]> future = new CompletableFuture<>();
        final List<Byte> bytes = new ArrayList<>();

        for (int i = count - 1; i >= 0; i--) {
            int finalI = i;
            keyListeners.add(new InputEvent[]{new InputEvent(e -> {
                        bytes.add(e);
                        if (finalI == 0) {
                            future.complete(bytes.toArray(Byte[]::new));
                        }
                    })}
            );
        }
        return future;
    }

    public void expectAny(InputEvent... inputEvents) {
        keyListeners.add(inputEvents);
    }

    public void waitForSequence(byte... bytes) {
        for (byte aByte : bytes) {
            waitFor(aByte);
        }
    }

    @Override
    public void onRead(byte[] input) {
        log.info(formatBinary("Received bytes", input));
        for (byte b : input) {
            if (keyListeners.isEmpty()) {
                log.warn("{}, but no listeners are present", formatBinary("Received byte", input));
                return;
            }

            InputEvent[] possibleEvents = keyListeners.peek();
            assert possibleEvents != null;

            inputSatisfied:
            {
                for (InputEvent possibleEvent : possibleEvents) {
                    if (possibleEvent.visit(b)) {
                        break inputSatisfied;
                    }
                }
                log.warn("{}, but no listeners satisfy it. Expected one of [{}]. Ignoring it and leaving the listeners as they are.",
                        formatBinary("Received byte", input),
                        Arrays.stream(possibleEvents)
                                .map(InputEvent::getKeys)
                                .map(ArrayUtils::toObject)
                                .flatMap(Arrays::stream)
                                .map(possibleByte -> String.format("%02X", possibleByte))
                                .reduce((b0, b1) -> b0 + ',' + b1)
                                .orElse("")
                );
                continue;
            }

            keyListeners.remove();
        }
    }
}
