package ch.bbcag.handsfree.gui.dialog;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
class HandsFreeInputDialogTest extends ApplicationTest {

    private HandsFreeInputDialog dialog;

    @Override
    public void start(Stage primaryStage) {
        dialog = new HandsFreeInputDialog("Test", "Test");
        dialog.show();
    }

    @Test
    void when_input_text_and_press_ok_input_passed_to_listener() {
        dialog.setOnOk(value -> Assertions.assertThat(value).isEqualTo("Hello World"));
        write("Hello World");
        clickOn(dialog.getButtonOk());
    }

    @Test
    public void when_press_cancel_dialog_closes() {
        clickOn(dialog.getButtonCancel());
        Assertions.assertThat(dialog.isShowing()).isEqualTo(false);
    }

}