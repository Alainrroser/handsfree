package ch.bbcag.handsfree.gui.dialog;

import ch.bbcag.handsfree.gui.HandsFreeTextField;
import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class HandsFreeInputDialog extends HandsFreeDialog {

    private HandsFreeTextField inputField;
    private HandsFreeButton buttonOk;
    private HandsFreeButton buttonCancel;

    public HandsFreeInputDialog(String title, String text) {
        super(title, text);
        init();
    }

    private void init() {
        inputField = new HandsFreeTextField();
        inputField.setPadding(new Insets(5));
        getContentBox().setTop(inputField);
        BorderPane.setMargin(inputField, new Insets(0, 0, 20, 0));

        buttonOk = new HandsFreeTextButton("Ship it!");
        buttonOk.setPrefWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        getContentBox().setRight(buttonOk);

        buttonCancel = new HandsFreeTextButton("Nevermind");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getContentBox().setLeft(buttonCancel);
    }

    public void setOnOk(ConfirmedCallback callback) {
        buttonOk.setOnAction(event -> {
            callback.run(inputField.getText());
            close();
        });
    }

    public void setOnCanceled(Runnable runnable) {
        buttonCancel.setOnAction(event -> {
            runnable.run();
            close();
        });
    }

    public HandsFreeButton getButtonOk() {
        return buttonOk;
    }

    public HandsFreeButton getButtonCancel() {
        return buttonCancel;
    }

    public interface ConfirmedCallback {
        void run(String value);
    }

}
