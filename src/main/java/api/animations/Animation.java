package api.animations;

import api.Commander;
import api.filesystem.File;
import api.util.RollingEntity;
import lombok.AccessLevel;
import lombok.Getter;

public class Animation extends RollingEntity {

    @Getter(AccessLevel.PACKAGE)
    private final File file;

    private boolean isDisplaying;

    Animation(Commander parent, File file) {
        super(parent);
        this.file = file;
        isDisplaying = false;
    }

    /**
     * Load the first frame of the specified animation in its stopped state at the specified location. If an animation is
     * already in use at that index it will be overwritten. Use the start animation command to play the displayed file.
     *
     * @param x Byte Leftmost coordinate of animation.
     * @param y Byte Topmost coordinate of animation.
     */
    public void display(int x, int y) {
        send(AnimationCommands.displayAnimation(getId(), file.getId(), x, y));
        isDisplaying = true;
    }

    /**
     * Stop and delete the displayed animation specified.
     * ID Byte Animation number to delete, value between 0 and 15.
     */
    public void delete() {
        send(AnimationCommands.deleteAnimation(getId()));
        isDisplaying = false;
    }

    public void start() {
        checkDisplaying();
        send(AnimationCommands.startStopAnimation(getId(), true));
    }

    public void stop() {
        checkDisplaying();
        send(AnimationCommands.startStopAnimation(getId(), false));
    }

    public void setAnimationFrame(int frame) {
        checkDisplaying();
        send(AnimationCommands.setAnimationFrame(getId(), frame));
    }

    public int getAnimationFrame() {
        checkDisplaying();
        send(AnimationCommands.getAnimationFrame(getId()));
        throw new RuntimeException("Not implemented");
    }

    private void checkDisplaying() {
        if (!isDisplaying)
            throw new IllegalArgumentException("Please call Animation#display before calling this method.");
    }

    @Override
    public void remove() {
        super.remove();
    }
}
