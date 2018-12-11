package api.bitmaps;

import api.Commander;
import api.util.RollingEntity;

public class Bitmap extends RollingEntity {

    Bitmap(Commander parent) {
        super(parent);
    }

    /**
     * Draw a previously uploaded bitmap from memory. Top left corner must be specified for drawing.
     *
     * @param x Byte Leftmost coordinate of bitmap.
     * @param y Byte Topmost coordinate of bitmap.
     */
    public void draw(int x, int y) {
        send(BitmapCommands.drawBitmapFromMemory(getId(), x, y));
    }

    /**
     * Draw a portion of a previously uploaded bitmap confined to the region specified.
     *
     * @param x Byte Leftmost coordinate of partial bitmap placement.
     * @param y Byte Topmost coordinate of partial bitmap placement.
     * @param x0 Byte Leftmost coordinate of the bitmap portion to be drawn.
     * @param y0 Byte Topmost coordinate of the bitmap portion to be drawn.
     * @param x1 Byte Rightmost coordinate of the bitmap portion to be drawn.
     * @param y1 Byte Bottommost coordinate of the bitmap portion to be drawn.
     */
    public void drawPartial(int x, int y, int x0, int y0, int x1, int y1) {
        if (x0 > x1)
            throw new IllegalArgumentException("x0 should be smaller than x1");
        if (y0 > y1) {
            throw new IllegalArgumentException("y0 should be smaller than y1");
        }
        int width = x1 - x0;
        int height = y1 - y0;

        send(BitmapCommands.drawPartialBitmap(getId(), x, y, x1, y1, width, height));
    }
}
