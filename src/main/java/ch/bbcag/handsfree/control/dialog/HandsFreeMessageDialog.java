package ch.bbcag.handsfree.control.dialog;

import ch.bbcag.handsfree.control.button.HandsFreeButton;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import javafx.geometry.Insets;

public class HandsFreeMessageDialog extends HandsFreeDialog {

    private HandsFreeButton buttonOk;

    public HandsFreeMessageDialog(String title, String text) {
        super(title, text);
        init();
    }

    private void init() {
        buttonOk = new HandsFreeDefaultButton("Got it!");
        buttonOk.setMaxWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        buttonOk.setOnAction(event -> close());
        getContentBox().setCenter(buttonOk);
    }

}
