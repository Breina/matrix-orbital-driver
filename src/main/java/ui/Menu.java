package ui;

import api.API;
import api.CommunicationException;
import api.text.*;
import api.text.Label;

import java.awt.*;

public class Menu {

    private static final int LEFT_MARK_SPACE = 10;
    private static final int MAX_WIDTH = 180;
    private static final int MAX_STRING_WIDTH = MAX_WIDTH - LEFT_MARK_SPACE;

    private final API api;

    public Menu(API api) {
        this.api = api;
    }

    public int show(String... options) throws CommunicationException {

        Text textApi = api.text();
        int yPos = 1;
        for (String option : options) {

            Dimension dimension = textApi.getStringExtents(option);

            int yBottom = yPos + dimension.height;
            Label label;
            if (dimension.width <= MAX_STRING_WIDTH && dimension.width != 0) {
                label = textApi.createNewLabel(LEFT_MARK_SPACE, yPos, MAX_WIDTH, yBottom, VertPos.TOP, HorPos.LEFT, null, false, 0);
            } else {
                label = textApi.createNewScrollingLabel(LEFT_MARK_SPACE, yPos, MAX_WIDTH, yBottom, VertPos.TOP, Direction.LEFT, null, false, 0, 50);
            }

            label.setText(option);

            yPos = yBottom;
        }

        return -1;
    }
}
