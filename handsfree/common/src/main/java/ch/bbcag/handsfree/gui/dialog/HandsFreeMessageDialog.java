package ch.bbcag.handsfree.gui.dialog;

import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

public class HandsFreeMessageDialog extends HandsFreeDialog {

    private HandsFreeButton buttonOk;

    public HandsFreeMessageDialog(String title, String text) {
        super(title, text);
        init();
    }

    private void init() {
        buttonOk = new HandsFreeTextButton("Got it!");
        buttonOk.setMaxWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        buttonOk.setOnAction(event -> close());
        getContentBox().setCenter(buttonOk);

        addKeyListener();
    }

    private void addKeyListener() {
        getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
                buttonOk.fire();
            }
        });
    }

}
