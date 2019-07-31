package api.nineslice;

import api.Commander;
import api.util.RollingID;

public class NineSlices extends Commander {

    private final RollingID<NineSlice> nineSlices = new RollingID<>(1024);

    public NineSlices(Commander parent) {
        super(parent);
    }

    /**
     * Upload a 9-slice file to a graphic display. To create a 9-slice see the 9-Slice File Creation section, for upload
     * protocol see the File Transfer Protocol or XModem Transfer Protocol entries.
     *
     * @param data Byte(s) 9-slice file data, see the 9-Slice File Creation example.
     *
     * @return The {@link NineSlice} instance.
     */
    public NineSlice createNineSlice(byte[] data) {
        return nineSlices.register(
                id -> NineSliceCommands.upload9SliceFile(id, data),
                new NineSlice(this)
        );
    }

    /**
     * Upload a 9-slice mask that can clear areas of the screen before a 9-slice is drawn. Programmatically,
     * (9slice&mask) | (screen&~mask) is shown when a bitmap is drawn. To create a mask see the9-Slice File Creation
     * section, for upload protocol see the File Transfer Protocol or XModem Transfer Protocol entries.
     *
     * @param data Byte(s) 9-slice mask file data, see the 9-Slice File Creation example.
     *
     * @return The {@link NineSlice} mask instance.
     */
    public NineSlice createNineSliceMask(byte[] data) {
        return nineSlices.register(
                id -> NineSliceCommands.upload9SliceMask(id, data),
                new NineSlice(this)
        );
    }
}
