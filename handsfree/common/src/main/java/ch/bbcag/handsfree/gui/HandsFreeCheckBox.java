package ch.bbcag.handsfree.gui;

import javafx.scene.control.CheckBox;

public class HandsFreeCheckBox extends CheckBox {

    // TODO: implement custom check box

    public HandsFreeCheckBox(String text) {
        super(text);

        setFont(HandsFreeFont.getFont(22.0));
        setTextFill(Colors.FONT);
    }

}
