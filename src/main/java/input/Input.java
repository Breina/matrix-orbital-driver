package input;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.StringUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Log4j2
public class Input implements ReadHandler {

    private Multimap<Byte, Action> keyListeners;

    public Input() {
        keyListeners = Multimaps.synchronizedListMultimap(ArrayListMultimap.create(7, 2));
    }

    @Override
    public void onRead(byte[] input) {
        log.info(StringUtil.formatBinary("Receiving", input));

        for (byte b : input) {
            Collection<Action> listeners = this.keyListeners.get(b);
            if (listeners == null || listeners.isEmpty()) {
                log.info(StringUtil.formatBinary("Ignored", b));
            } else {
                Iterator<Action> iterator = listeners.iterator();
                while (iterator.hasNext()) {
                    Action listener = iterator.next();

                    listener.run();
                    if (listener.shouldRemove()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void addOneTimeListener(byte key, Runnable listener) {
        keyListeners.put(key, new Action(listener, true));
    }

    public void addListener(byte key, Runnable listener) {
        keyListeners.put(key, new Action(listener, false));
    }

    public void removeListener(byte key, Runnable listener) {
        keyListeners.remove(key, listener);
    }

    public void blockUntilReceived(byte... keys) {
        if (keys.length != 0) {
            Runnable r = () -> {
                int newLength = keys.length - 1;
                byte[] tail = new byte[newLength];
                System.arraycopy(keys, 1, tail, 0, newLength);
                blockUntilReceived(tail);
            };
            FutureTask<Void> futureTask = new FutureTask<>(r, null);
            addListener(keys[0], r);
            try {
                futureTask.get();
                removeListener(keys[0], r);
            } catch (InterruptedException | ExecutionException e) {
                log.error(e);
            }
        }
    }

    private class Action {
        private final Runnable listener;
        private final boolean shouldRemove;

        private Action(Runnable listener, boolean shouldRemove) {
            this.listener = listener;
            this.shouldRemove = shouldRemove;
        }

        public void run() {
//            listener.notify();
            listener.run();
        }

        public boolean shouldRemove() {
            return shouldRemove;
        }
    }

}
