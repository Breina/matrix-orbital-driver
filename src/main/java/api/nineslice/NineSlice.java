package api.nineslice;

import api.Commander;
import api.util.RollingEntity;

public class NineSlice extends RollingEntity {

    NineSlice(Commander parent) {
        super(parent);
    }

    public void draw(int x0, int y0, int x1, int y1) {
        send(NineSliceCommands.display9Slice(getId(), x0, y0, x1, y1));
    }
}
