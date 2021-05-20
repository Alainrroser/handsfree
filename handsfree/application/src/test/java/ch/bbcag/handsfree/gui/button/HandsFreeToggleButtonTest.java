package ch.bbcag.handsfree.gui.button;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
class HandsFreeToggleButtonTest extends ApplicationTest {

    private HandsFreeToggleButton button;

    @Override
    public void start(Stage primaryStage) {
        button = new HandsFreeToggleButton("Activated");
        primaryStage.setScene(new Scene(new StackPane(button), 200, 200));
        primaryStage.show();
    }

    @Test
    public void when_button_is_clicked_text_updates() {
        clickOn(".button");
        Assertions.assertThat(button).hasText("Activated: ON");
        clickOn(".button");
        Assertions.assertThat(button).hasText("Activated: OFF");
    }

}