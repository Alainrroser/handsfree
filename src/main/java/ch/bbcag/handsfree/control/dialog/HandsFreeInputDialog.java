package ch.bbcag.handsfree.control.dialog;

import ch.bbcag.handsfree.control.HandsFreeTextField;
import ch.bbcag.handsfree.control.button.HandsFreeButton;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import java.util.concurrent.Callable;

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

        buttonOk = new HandsFreeDefaultButton("Ship it!");
        buttonOk.setPrefWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        getContentBox().setLeft(buttonOk);

        buttonCancel = new HandsFreeDefaultButton("Nevermind");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getContentBox().setRight(buttonCancel);
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

    public interface ConfirmedCallback {
        void run(String value);
    }

}
