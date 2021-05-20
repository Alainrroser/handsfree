package ch.bbcag.handsfree.gui;

import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

@ExtendWith(ApplicationExtension.class)
class HandsFreeScrollPaneTest extends ApplicationTest {

    private HandsFreeScrollPane scrollPane;
    private Pane content;

    @Override
    public void start(Stage primaryStage) {
        content = new Pane();
        content.setMinWidth(500);
        content.setMinHeight(1000);

        scrollPane = new HandsFreeScrollPane();
        scrollPane.setMinSize(500, 500);
        scrollPane.setMaxSize(500, 500);
        scrollPane.setContent(content);

        primaryStage.setScene(new Scene(new StackPane(scrollPane)));
        primaryStage.show();
    }

    @Test
    public void when_mouse_scroll_pane_translates_content() {
        clickOn(scrollPane);
        scroll(10, VerticalDirection.DOWN);

        Assertions.assertThat(content.getTranslateY()).isEqualTo(-500.0);
    }

    @Test
    public void when_scroll_bar_drag_translates_content() {
        drag(scrollPane.getScrollBar(), MouseButton.PRIMARY);
        moveBy(0, 300);
        release(MouseButton.PRIMARY);

        Assertions.assertThat(content.getTranslateY()).isEqualTo(-500.0);
    }

}