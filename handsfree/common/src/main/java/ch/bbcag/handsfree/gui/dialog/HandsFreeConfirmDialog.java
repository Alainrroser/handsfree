package ch.bbcag.handsfree.gui.dialog;

import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import javafx.geometry.Insets;

public class HandsFreeConfirmDialog extends HandsFreeDialog {

    private HandsFreeButton buttonOk;
    private HandsFreeButton buttonCancel;

    public HandsFreeConfirmDialog(String title, String text) {
        super(title, text);
        init();
    }

    private void init() {
        buttonOk = new HandsFreeDefaultButton("You bet!");
        buttonOk.setPrefWidth(200);
        buttonOk.setOnAction(event -> close());
        buttonOk.setPadding(new Insets(10, 0, 10, 0));
        buttonOk.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
        getContentBox().setLeft(buttonOk);

        buttonCancel = new HandsFreeDefaultButton("No way!");
        buttonCancel.setPrefWidth(200);
        buttonCancel.setOnAction(event -> close());
        buttonCancel.setPadding(new Insets(10, 0, 10, 0));
        getContentBox().setRight(buttonCancel);
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
