package ch.bbcag.handsfree.control.dialog;

import ch.bbcag.handsfree.control.button.HandsFreeButton;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import javafx.geometry.Insets;

public class HandsFreeConfirmDialog extends HandsFreeDialog {

    private HandsFreeButton buttonOk;
    private HandsFreeButton buttonCancel;

    public HandsFreeConfirmDialog(String title, String text) {
        super(title, text);

        buttonOk = new HandsFreeDefaultButton("You bet!");
        buttonOk.setPrefWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        getButtonBox().setLeft(buttonOk);

        buttonCancel = new HandsFreeDefaultButton("No way!");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getButtonBox().setRight(buttonCancel);
    }

    public void setOnConfirmed(Runnable runnable) {
        buttonOk.setOnAction(event -> {
            runnable.run();
            close();
        });
    }

}
