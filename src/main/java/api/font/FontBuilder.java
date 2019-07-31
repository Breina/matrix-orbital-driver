package api.font;

import lombok.*;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
public class FontBuilder {

    @Getter(AccessLevel.PACKAGE)
    private final Function<FontBuilder, Font> afterBuild;

    /**
     * Space between left of display and first column of text. Default 0.
     */
    private int lineMargin = Font.currentLineMargin;

    /**
     * Space between top of display area and first row of text. Default 0.
     */
    private int topMargin = Font.currentTopMargin;

    /**
     * Space between characters. Default 0.
     */
    private int charSpace = Font.currentCharSpace;

    /**
     * Space between character rows. Default 1.
     */
    private int lineSpace = Font.currentLineSpace;

    /**
     * Point at which text scrolls up screen to display additional rows. Default 1.
     */
    private int scroll = Font.currentScroll;

    public FontBuilder lineMargin(int lineMargin) {
        this.lineMargin = lineMargin;
        return this;
    }

    public FontBuilder topMargin(int topMargin) {
        this.topMargin = topMargin;
        return this;
    }

    public FontBuilder charSpace(int charSpace) {
        this.charSpace = charSpace;
        return this;
    }

    public FontBuilder lineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
        return this;
    }

    public FontBuilder lineMarscrollgin(int scroll) {
        this.scroll = scroll;
        return this;
    }

    public Font create() {
        return afterBuild.apply(this);
    }
}
