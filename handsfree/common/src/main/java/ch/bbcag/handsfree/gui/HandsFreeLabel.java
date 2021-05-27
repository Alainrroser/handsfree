package ch.bbcag.handsfree.gui;

import javafx.scene.control.Label;

public class HandsFreeLabel extends Label {

    public HandsFreeLabel(String text) {
        super(text);

        setFont(HandsFreeFont.getFont(25.0));
        setTextFill(HandsFreeColors.FONT);
    }

}
