package api.drawing;

import api.Commander;
import api.nineslice.NineSlice;
import api.util.RollingID;
import lombok.Getter;

public class Drawing extends Commander {

    @Getter(lazy = true)
    private final Drawer drawer = new Drawer(this);

    private RollingID<Bar> barGraphs = new RollingID<>(256);

    private RollingID<StripChart> stripCharts = new RollingID<>(8);

    public Drawing(Commander parent) {
        super(parent);
    }

    /**
     * Clears the contents of the screen.
     */
    public void clear() {
        send(DrawingCommands.clear());
    }

    /**
     * Define and scroll the contents of a portion of the screen.
     *
     * @param x0 Byte Leftmost coordinate of the scroll window, zero indexed from left.
     * @param y0 Byte Topmost coordinate of the scroll window, zero indexed from top.
     * @param x1 Byte Rightmost coordinate of the scroll window, zero indexed from left.
     * @param y1 Byte Bottommost coordinate of the scroll window, zero indexed from top.
     * @param moveX Signed Short Number of pixels to scroll horizontally.
     * @param moveY Signed Short Number of pixels to scroll vertically.
     */
    public void scrollScreen(int x0, int y0, int x1, int y1, int moveX, int moveY) {
        send(DrawingCommands.scrollScreen(x0, y0, x1, y1, moveX, moveY));
    }

    /**
     * Initialize a bar graph in memory for later implementation. Graphs can be located anywhere on the screen, but
     * overlapping may cause distortion. Graph should be filled using the Draw a Bar Graph command.
     *
     * @param type Byte Graph style, see Bar Graph Types.
     * @param x0 Byte Leftmost coordinate.
     * @param y0 Byte Topmost coordinate.
     * @param x1 Byte Rightmost coordinate.
     * @param y1 Byte Bottommost coordinate.
     *
     * @return {@link Bar} The bar graph whose values can be updated.
     */
    public Bar createBarGraph(GraphType type, int x0, int y0, int x1, int y1) {
        return barGraphs.register(
                id -> send(DrawingCommands.initializeBarGraph(id, type, x0, y0, x1, y1)),
                new Bar(this)
        );
    }

    /**
     * Initialize a 9-slice bar graph in memory for later implementation. 9-slice graphs are also be filled using the Draw a
     * Bar Graph command and are allocated to the same memory as regular bitmaps.
     *
     * @param type Byte Graph style, see Bar Graph Types.
     * @param x0 Byte Leftmost coordinate of the 9-slice bar, zero indexed from left.
     * @param y0 Byte Topmost coordinate of the 9-slice bar, zero indexed from top.
     * @param x1 Byte Rightmost coordinate of the 9-slice bar, zero indexed from left.
     * @param y1 Byte Bottommost coordinate of the 9-slice bar, zero indexed from top.
     * @param fore9Slice Short 9-slice used for the foreground.
     * @param back9Slice Short 9-slice used for the background.
     *
     * @return A {@link Bar} graph whose values can be updated.
     */
    public Bar create9SliceBarGraph(GraphType type, int x0, int y0, int x1, int y1, NineSlice fore9Slice, NineSlice back9Slice) {
        return barGraphs.register(
                id -> send(DrawingCommands.initialize9SliceBarGraph(id, type, x0, y0, x1, y1, fore9Slice.getId(), back9Slice.getId())),
                new Bar(this)
        );
    }

    /**
     * Designate a portion of the screen for a chart. Visual changes will occur when the update command is issued.
     *
     * @param x0 Byte Leftmost coordinate of the strip chart, zero indexed from left.
     * @param y0 Byte Topmost coordinate of the strip chart, zero indexed from top.
     * @param x1 Byte Rightmost coordinate of the strip chart, zero indexed from left.
     * @param y1 Byte Bottommost coordinate of the strip chart, zero indexed from top.
     * @param min Short Minimum chart value.
     * @param max Short Maximum chart value. For line styles, make max-min at least one pixel less than chart height.
     * @param step Byte Scroll distance between updates, in pixels.
     * @param direction The direction in which updates a propagated.
     * @param type The type of graph, if one wants a 9-slice chart, please use the {@link #createNew9SliceChart(int, int, int, int, int, int, int, ChartDirection, NineSlice)} instead.
     *
     * @return A {@link StripChart} whose values can be updated.
     */
    public StripChart createNewChart(int x0, int y0, int x1, int y1,
                                     int min, int max, int step,
                                     ChartDirection direction, ChartType type) {
        if (type == ChartType.NINESLICE)
            throw new IllegalArgumentException("To create a 9-slice chart, please use Drawing#createNew9SliceChart instead.");

        return createNewChart(x0, y0, x1, y1, min, max, step, direction, type, 0);
    }

    /**
     * Designate a portion of the screen for a 9-slice chart. Visual changes will occur when the update command is issued.
     *
     * @param x0 Byte Leftmost coordinate of the strip chart, zero indexed from left.
     * @param y0 Byte Topmost coordinate of the strip chart, zero indexed from top.
     * @param x1 Byte Rightmost coordinate of the strip chart, zero indexed from left.
     * @param y1 Byte Bottommost coordinate of the strip chart, zero indexed from top.
     * @param min Short Minimum chart value.
     * @param max Short Maximum chart value. For line styles, make max-min at least one pixel less than chart height.
     * @param step Byte Scroll distance between updates, in pixels.
     * @param direction The direction in which updates a propagated.
     * @param nineSlice 9-slice file, if a 9-slice style strip chart is not desired send any value for this parameter.
     *
     * @return A {@link StripChart} whose values can be updated.
     */
    public StripChart createNew9SliceChart(int x0, int y0, int x1, int y1,
                                           int min, int max, int step,
                                           ChartDirection direction, NineSlice nineSlice) {
        return createNewChart(x0, y0, x1, y1, min, max, step, direction, ChartType.NINESLICE, nineSlice.getId());
    }

    private StripChart createNewChart(int x0, int y0, int x1, int y1,
                                      int min, int max, int step,
                                      ChartDirection direction, ChartType type, int sliceID) {
        return stripCharts.register(
                id -> send(DrawingCommands.initializeStripChart(id, x0, y0, x1, y1, min, max, step, direction, type, sliceID)),
                new StripChart(this, min, max));
    }
}
