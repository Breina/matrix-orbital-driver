package api.drawing;

import api.Commander;

public class Drawer extends Commander {

    private boolean foreground = true;

    Drawer(Commander parent) {
        super(parent);
    }

    /**
     * Set the colour to be used for all future drawing commands that do not implicitly specify colour.
     *
     * @param foreground False for background or true for text colour.
     */
    public void setDrawingColor(boolean foreground) {
        if (this.foreground != foreground) {
            send(DrawingCommands.setDrawingColour(foreground ? 1 : 0));
            this.foreground = foreground;
        }
    }

    /**
     * Draw a single pixel at the specified coordinate using the current drawing colour.
     *
     * @param x Byte Horizontal position of pixel to be drawn.
     * @param y Byte Vertical position of pixel to be drawn.
     */
    public void pixel(int x, int y) {
        send(DrawingCommands.drawPixel(x, y));
    }

    /**
     * Draw a line connecting two termini. Lines may be rendered differently when drawn right to left versus left to right.
     *
     * @param x0 Byte Horizontal coordinate of first terminus.
     * @param y0 Byte Vertical coordinate of first terminus.
     * @param x1 Byte Horizontal coordinate of second terminus.
     * @param y1 Byte Vertical coordinate of second terminus.
     *
     * @return A {@link LineBuilder} which can be used to continue this line.
     */
    public LineBuilder line(int x0, int y0, int x1, int y1) {
        LineBuilder lineBuilder = new LineBuilder(this, x0, y0);
        lineBuilder.to(x1, y1);
        return lineBuilder;
    }

    /**
     * Draw a rectangle.
     *
     * @param x0 Byte Leftmost coordinate.
     * @param y0 Byte Topmost coordinate.
     * @param x1 Byte Rightmost coordinate.
     * @param y1 Byte Bottommost coordinate.
     *
     * @return A {@link Rectangle} which can be used to customize and eventually draw it.
     */
    public Rectangle rectangle(int x0, int y0, int x1, int y1) {
        return new Rectangle(this, x0, y0, x1, y1, () -> this.foreground);
    }

    /**
     * Draw a circle.
     *
     * @param x Byte Horizontal coordinate of the circle centre.
     * @param y Byte Vertical coordinate of the circle centre.
     * @param radius Byte Distance between the circle perimeter and centre.
     *
     * @return A {@link Circle} which can be used to customize and eventually draw it.
     */
    public Circle circle(int x, int y, int radius) {
        return new Circle(this, x, y, radius);
    }

    /**
     * Draw an ellipse.
     *
     * @param x Byte Horizontal coordinate of the ellipse centre, zero indexed from left.
     * @param y Byte Vertical coordinate of the ellipse centre, zero indexed from top.
     * @param xRadius Byte Distance between the furthest horizontal point on the ellipse perimeter and centre.
     * @param yRadius Byte Distance between the furthest vertical point on the ellipse perimeter and centre.
     *
     * @return An {@link Ellipse} which can be used to customize and eventually draw it.
     */
    public Ellipse ellipse(int x, int y, int xRadius, int yRadius) {
        return new Ellipse(this, x, y, xRadius, yRadius);
    }
}
