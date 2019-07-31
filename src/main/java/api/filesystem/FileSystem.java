package api.filesystem;

import api.Commander;
import api.util.RollingID;

public class FileSystem extends Commander {

    private final RollingID<File> files = new RollingID<>(1024);

    public FileSystem(Commander parent) {
        super(parent);
    }

    public File reserveFile(byte[] data) {
        return files.register(new File(this));
    }
}
