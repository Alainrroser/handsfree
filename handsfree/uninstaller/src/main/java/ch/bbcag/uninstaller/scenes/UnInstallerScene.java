package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.uninstaller.Const;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.Objects;

public class UninstallerScene extends HandsFreeScene {

    private BorderPane borderPane;
    private HBox buttonHBox;

    public UninstallerScene(Stage stage, HandsFreeSceneConfiguration configuration) {
        super(stage, new HBox(), configuration);

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM,
                                         Const.BOX_PADDING_RIGHT_LEFT));
        HBox.setHgrow(borderPane, Priority.ALWAYS);
        ((HBox) getContentRoot()).getChildren().add(borderPane);

        buttonHBox = new HBox(Const.BOX_SPACING);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(buttonHBox);
    }

    public void addIconImageBackground() {
        Color color = Color.web("D34715");
        Stop[] stops = {
                new Stop(0, color),
                new Stop(1, color.deriveColor(0.0, 1.0, 1.0, 0.0))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        Pane imageContainer = new Pane();
        imageContainer.setMinWidth(Const.HEIGHT * 0.6);
        imageContainer.setMinHeight(Const.HEIGHT);
        imageContainer.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Start.class.getResourceAsStream("/images/icon128.png"))));
        imageView.setFitWidth(128);
        imageView.setFitHeight(128);
        imageView.setX(32);
        imageView.setY(16);
        imageContainer.getChildren().add(imageView);

        HBox root = (HBox) getContentRoot();
        root.getChildren().clear();
        root.getChildren().addAll(imageContainer, borderPane);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                imageView.setTranslateY(10 + Math.sin(now / 1e9d * 2) * 10);
            }
        }.start();
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void addButton(String text, HandsFreeButtonPalette palette, Runnable onClicked) {
        HandsFreeTextButton button = new HandsFreeTextButton(text);
        button.setPrefWidth(Const.BUTTON_WIDTH);
        button.setPadding(Const.BUTTON_PADDING);
        button.setPalette(palette);
        button.setOnAction(event -> {
            if(onClicked != null) {
                onClicked.run();
            }
        });
        buttonHBox.getChildren().add(button);
    }

}
