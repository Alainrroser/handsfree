package ch.bbcag.handsfree.gui.dialog;

import ch.bbcag.handsfree.gui.HandsFreeTextField;
import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
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
        getContentBox().setLeft(buttonOk);

        buttonCancel = new HandsFreeTextButton("Nevermind");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getContentBox().setRight(buttonCancel);

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

    public void setOnOk(HandsFreeInputListener listener) {
        buttonOk.setOnAction(event -> {
            listener.run(inputField.getText());
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

}
