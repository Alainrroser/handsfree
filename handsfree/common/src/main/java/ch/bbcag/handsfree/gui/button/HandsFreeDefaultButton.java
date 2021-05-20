package ch.bbcag.handsfree.gui.button;

import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.HandsFreeFont;
import javafx.geometry.Insets;

public class HandsFreeDefaultButton extends HandsFreeButton {

    private static final int FONT_SIZE = 25;
    private static final int VERTICAL_PADDING = 20;

    public HandsFreeDefaultButton(String text) {
        setText(text);
        setMaxWidth(Double.MAX_VALUE);
        setTextFill(Colors.FONT);
        setFont(HandsFreeFont.getFont(FONT_SIZE));
        setPadding(new Insets(VERTICAL_PADDING, 0, VERTICAL_PADDING, 0));
        addBorder();
    }

}
