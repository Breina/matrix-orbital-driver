package ui;

import api.API;
import api.display.Display;
import api.drawing.Drawer;
import api.drawing.Drawing;
import api.text.Label;
import api.text.*;
import com.CommunicationException;
import input.InputEvent;
import input.InputEventHandler;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class Menu {

    private static final int LEFT_MARK_SPACE = 10;
    private static final int MAX_WIDTH = 180;
    //    private static final String CURSOR = ">";
    private static final String CURSOR = new String(new byte[]{29}, US_ASCII);
    private final API api;
    private final Text textApi;
    private final Drawing drawing;
    private final Drawer drawer;
    private final InputEventHandler input;

    public Menu(API api, InputEventHandler input) {
        this.api = api;
        textApi = api.text();
        drawing = api.drawing();
        drawer = drawing.drawer();

        this.input = input;
    }

    public int prompt(String titleText, String... options) throws CommunicationException {
        assert options.length >= 2 : "What's the point of a menu otherwise?";

        Label title = createLabel(titleText, 1, 1);
        int currentY = title.getyBottom() + 1;

        drawer.setDrawingColor(true);
        drawer.line(1, currentY, MAX_WIDTH, currentY);

        Dimension selectionDimension = textApi.getStringExtents(CURSOR);
        int indentWidth = selectionDimension.width + 1;
        final int textHeight = selectionDimension.height;
        currentY += 2;

        final int lineHeight = textHeight + 1;
        int shownMenuOptions = Math.min(
                options.length,
                (Display.HEIGHT - currentY) / lineHeight
        );
        Label[] selectionBoxes = new Label[shownMenuOptions];
        Label[] menuItems = new Label[shownMenuOptions];

        for (int i = 0; i < shownMenuOptions; i++) {
            int yBottom = currentY + textHeight;
            selectionBoxes[i] = textApi.createNewLabel(1, currentY, selectionDimension.width, yBottom, VertPos.TOP, HorPos.LEFT, null, false, 0);

            menuItems[i] = createLabel(options[i], indentWidth, currentY);
            currentY += lineHeight;
        }

        int currentOption = 0;

        for (; ; ) {
            selectionBoxes[currentOption].setText(CURSOR);

            final byte up = input.vk_up();
            final byte down = input.vk_down();
            final byte ok = input.vk_down();

            Future<Byte> keyEvent = input.expect(up, down);
            Byte key = null;
            try {
                key = keyEvent.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new CommunicationException(e);
            }

            selectionBoxes[currentOption].setText("");
            if (key == up) {
                currentOption = Math.max(currentOption - 1, 0);
            } else {
                currentOption = Math.min(currentOption + 1, shownMenuOptions - 1);
            }
        }

//        return -1;
    }

    private Label createLabel(String text, int xLeft, int yTop) throws CommunicationException {
        Dimension dimension = textApi.getStringExtents(text);
        int yBottom = yTop + dimension.height;
        Label label = textApi.createNewLabel(xLeft, yTop, MAX_WIDTH, yBottom, VertPos.TOP, HorPos.LEFT, null, false, 0);
        label.setText(text);
        return label;
    }
}
