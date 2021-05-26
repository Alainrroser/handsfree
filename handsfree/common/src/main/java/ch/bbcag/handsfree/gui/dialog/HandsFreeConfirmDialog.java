package ch.bbcag.handsfree.gui.dialog;

import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public class HandsFreeConfirmDialog extends HandsFreeDialog {

    private HandsFreeButton buttonOk;
    private HandsFreeButton buttonCancel;

    public HandsFreeConfirmDialog(String title, String text) {
        super(title, text);
        init();
    }

    private void init() {
        buttonOk = new HandsFreeTextButton("You bet!");
        buttonOk.setPrefWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        getContentBox().setRight(buttonOk);

        buttonCancel = new HandsFreeTextButton("No way!");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getContentBox().setLeft(buttonCancel);

        addKeyListener();
    }

    private void addKeyListener() {
        getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                buttonOk.fire();
            } else if(event.getCode() == KeyCode.ESCAPE) {
                buttonCancel.fire();
            }
        });
    }

    public void setOnConfirmed(Runnable runnable) {
        buttonOk.setOnAction(event -> {
            runnable.run();
            close();
        });
    }

    public void setOnCanceled(Runnable runnable) {
        buttonCancel.setOnAction(event -> {
            runnable.run();
            close();
        });
    }

}
