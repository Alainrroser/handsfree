package ch.bbcag.handsfree.gui;

import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class HandsFreeStageDecoration extends BorderPane {

    private static final double HEIGHT = 50;
    private static final double ICON_SIZE = 30;
    private static final double BUTTON_WIDTH = 70;

    private HandsFreeIconButton minimizeButton;
    private HandsFreeIconButton closeButton;

    public HandsFreeStageDecoration(Stage stage, HandsFreeSceneConfiguration configuration) {
        setMinHeight(HEIGHT);
        setMaxHeight(HEIGHT);
        setBackground(new Background(new BackgroundFill(Colors.BUTTON, null, null)));

        HBox leftBox = new HBox(10);
        leftBox.setPadding(new Insets(0, 0, 0, 10));
        leftBox.setAlignment(Pos.CENTER_LEFT);
        setLeft(leftBox);

        HBox rightBox = new HBox();
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        setRight(rightBox);
        
        if(configuration.isHasMinimizeButton()) {
            minimizeButton = new HandsFreeIconButton("/images/minimize.png");
            minimizeButton.setOnAction(event -> stage.setIconified(true));
            minimizeButton.setPrefWidth(BUTTON_WIDTH);
            minimizeButton.setMaxHeight(Double.MAX_VALUE);
            rightBox.getChildren().add(minimizeButton);
        }

        closeButton = new HandsFreeIconButton("/images/close.png");
        closeButton.setOnAction(event -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        closeButton.setPrefWidth(BUTTON_WIDTH);
        closeButton.setMaxHeight(Double.MAX_VALUE);
        closeButton.setPalette(HandsFreeButtonPalette.CLOSE_PALETTE);
        rightBox.getChildren().add(closeButton);

        ImageView iconView = new ImageView(new Image(Objects.requireNonNull(HandsFreeScene.class.getResourceAsStream("/images/icon64.png"))));
        iconView.setFitWidth(ICON_SIZE);
        iconView.setFitHeight(ICON_SIZE);
        leftBox.getChildren().add(iconView);

        Label title = new Label(configuration.getTitle());
        title.setFont(HandsFreeFont.getFont(20));
        title.setTextFill(Colors.FONT);
        leftBox.getChildren().add(title);

        WindowDragController dragController = new WindowDragController(stage, this);
        dragController.enable();
    }

    public HandsFreeIconButton getMinimizeButton() {
        return minimizeButton;
    }

    public HandsFreeIconButton getCloseButton() {
        return closeButton;
    }
}
