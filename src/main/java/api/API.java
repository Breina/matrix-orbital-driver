package api;

import api.animation.Animations;
import api.bitmap.Bitmaps;
import api.buzzer.Buzzer;
import api.communication.Communication;
import api.display.Display;
import api.drawing.Drawing;
import api.filesystem.FileSystem;
import api.font.Fonts;
import api.nineslice.NineSlices;
import api.text.Text;
import com.ComPort;
import lombok.Getter;
import lombok.experimental.Accessors;

public class API {

    @Getter
    @Accessors(fluent = true)
    private final FileSystem fileSystem;

    @Getter
    @Accessors(fluent = true)
    private final Animations animations;

    @Getter
    @Accessors(fluent = true)
    private final NineSlices nineSlices;

    @Getter
    @Accessors(fluent = true)
    private final Text text;

    @Getter
    @Accessors(fluent = true)
    private final Bitmaps bitmaps;

    @Getter
    @Accessors(fluent = true)
    private final Drawing drawing;

    @Getter
    @Accessors(fluent = true)
    private final Communication communication;

    @Getter
    @Accessors(fluent = true)
    private final Fonts fonts;

    @Getter
    @Accessors(fluent = true)
    private final Display display;

    @Getter
    @Accessors(fluent = true)
    private final Buzzer buzzer;

    public API(Commander commander, ComPort comPort) {

        fileSystem = new FileSystem(commander);
        animations = new Animations(commander, fileSystem);
        nineSlices = new NineSlices(commander);
        text = new Text(commander);
        bitmaps = new Bitmaps(commander);
        drawing = new Drawing(commander);
        communication = new Communication(commander, comPort);
        fonts = new Fonts(commander);
        display = new Display(commander);
        buzzer = new Buzzer(commander);
    }
}
