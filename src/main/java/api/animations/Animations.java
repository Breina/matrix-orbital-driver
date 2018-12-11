package api.animations;

import api.Commander;
import api.filesystem.File;
import api.filesystem.FileSystem;
import api.util.RollingID;

public class Animations extends Commander {

    private final FileSystem fileSystem;

    private final RollingID<Animation> animations = new RollingID<>(16);

    public Animations(Commander parent, FileSystem fileSystem) {
        super(parent);
        this.fileSystem = fileSystem;
    }

    /**
     * Upload an animation file to a graphic display. To create an animation see the Animation File Creation section, for
     * upload protocol see the File Transfer Protocol or XModem Transfer Protocol entries. Up to
     * 16 animations can be displayed on the screen at one time, using the Display Animation command, but up to 1024
     * can be stored in memory for later use. Please note the total graphic memory size is 256KB.
     *
     * @param data Byte(s) Animation file data, see the Animation File Creation example.
     *
     * @return The {@link Animation} instance.
     */
    public Animation createAnimation(byte[] data) {
        File file = fileSystem.reserveFile(data);

        return animations.register(
                id -> AnimationCommands.uploadAnimationFile(file.getId(), data),
                new Animation(this, file)
        );
    }

    /**
     * Copies an existing animation so that it can be individually controlled.
     *
     * @param animation The animation to be copied.
     *
     * @return The copied {@link Animation} instance.
     */
    public Animation copyAnimation(Animation animation) {
        return animations.register(new Animation(this, animation.getFile()));
    }
}
