package ch.bbcag.handsfree.gui;

import javafx.scene.text.Font;

import java.io.InputStream;

public class HandsFreeFont {

    private static final String FONT_LOCATION = "/fonts/Roboto-Regular.ttf";
    private static final InputStream FONT_INPUT_STREAM = HandsFreeFont.class.getResourceAsStream(FONT_LOCATION);
    private static final Font FONT = Font.loadFont(FONT_INPUT_STREAM, 20);

    public static Font getFont(double size) {
        return Font.font(FONT.getFamily(), size);
    }

}
