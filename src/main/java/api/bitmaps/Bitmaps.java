package api.bitmaps;

import api.Commander;
import api.util.RollingID;

public class Bitmaps extends Commander {

    private final RollingID<Bitmap> bitmaps = new RollingID<>(1024);

    public Bitmaps(Commander parent) {
        super(parent);
    }

    /**
     * Upload a bitmap to a graphic display. To create a bitmap see the Bitmap File Creation section, for upload protocol
     * see the File Transfer Protocol or XModem Transfer Protocol entries. Start screen is ID 1.
     *
     * @param bitmap Bitmap file data.
     *
     * @return The {@link Bitmap} instance.
     */
    public Bitmap createBitmap(BitmapData bitmap) {
        return bitmaps.register(
                id -> send(BitmapCommands.uploadBitmapFile(id, bitmap.getData())),
                new Bitmap(this)
        );
    }

    /**
     * Upload a bitmap mask that can clear areas of the screen before a bitmap is drawn. Programmatically,
     * (bitmap&mask) | (screen&~mask) is shown when a bitmap is drawn. To create a mask see the Bitmap Masking
     * section, for upload protocol see the File Transfer Protocol or XModem Transfer Protocol entries.
     *
     * @param mask Bitmap mask file data.
     *
     * @return The {@link Bitmap} instance.
     */
    public Bitmap createBitmapMask(BitmapData mask) {
        return bitmaps.register(
                id -> send(BitmapCommands.uploadBitmapMask(id, mask.getData())),
                new Bitmap(this)
        );
    }

    /**
     * Draw a bitmap directly to the graphic display without saving to memory. Cannot be implemented in a script.
     *
     * @param x Byte Leftmost coordinate of bitmap.
     * @param y Byte Topmost coordinate of bitmap.
     * @param bitmap Byte(s) Bitmap file data, see the Font File Creation example.
     */
    public void drawBitmapDirectly(int x, int y, byte[] bitmap) {
        send(BitmapCommands.drawBitmapDirectly(x, y, bitmap));
    }
}
