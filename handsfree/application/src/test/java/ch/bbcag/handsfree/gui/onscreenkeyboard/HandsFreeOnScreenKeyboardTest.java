package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeKeyCodes;
import ch.bbcag.handsfree.error.NativeException;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
class HandsFreeOnScreenKeyboardTest extends ApplicationTest {

    private TextField textField;
    private HandsFreeOnScreenKeyboard keyboard;

    @Override
    public void start(Stage primaryStage) throws NativeException {
        textField = new TextField();
        primaryStage.setScene(new Scene(textField));
        primaryStage.show();

        HandsFreeContext context = new HandsFreeContext(primaryStage);

        keyboard = new HandsFreeOnScreenKeyboard(context);
        keyboard.display();
    }

    @Test
    public void when_click_on_keys_text_gets_written() {
        focusTextField();
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.LSHIFT);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.H);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.E);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.L);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.L);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.O);

        Assertions.assertThat(textField).hasText("Hello");
    }

    @Test
    public void when_text_copied_and_pasted_text_appears_twice() {
        focusTextField();
        textField.setText("Banana");

        clickOnKeyWithKeyCode(HandsFreeKeyCodes.LCONTROL, MouseButton.SECONDARY);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.A);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.C);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.LCONTROL);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.RIGHT);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.LCONTROL);
        clickOnKeyWithKeyCode(HandsFreeKeyCodes.V);

        Assertions.assertThat(textField).hasText("BananaBanana");
    }

    private void focusTextField() {
        clickOn(textField.getScene().getWindow().getX() + 10, textField.getScene().getWindow().getY() + 10);
    }

    private void clickOnKeyWithKeyCode(int keyCode) {
        clickOnKeyWithKeyCode(keyCode, MouseButton.PRIMARY);
    }

    private void clickOnKeyWithKeyCode(int keyCode, MouseButton button) {
        for(HandsFreeOnScreenKey key : keyboard.getKeys()) {
            if(key.getKey().getKeyCode() == keyCode) {
                clickOn(key, button);
                break;
            }
        }
    }

}