package ch.bbcag.handsfree.control.dialog;

import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HandsFreeDialog extends Stage {

    private BorderPane rootNode;
    private Label textLabel;
    private BorderPane buttonBox;

    public HandsFreeDialog(String title, String text) {
        rootNode = new BorderPane();
        rootNode.setPadding(new Insets(0, 30, 30, 30));

        textLabel = new Label(text);
        textLabel.setFont(HandsFreeFont.getFont(22));
        textLabel.setTextFill(Colors.FONT);
        textLabel.setWrapText(true);
        textLabel.setTextAlignment(TextAlignment.CENTER);
        rootNode.setCenter(textLabel);
        BorderPane.setAlignment(textLabel, Pos.CENTER);

        buttonBox = new BorderPane();
        rootNode.setBottom(buttonBox);

        HandsFreeSceneConfiguration configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle(title);
        configuration.setHasMinimizeButton(false);

        HandsFreeScene scene = new HandsFreeScene(this, rootNode, configuration);
        scene.apply();
        scene.centerStageOnScreen();
        scene.setAlwaysOnTop(true);

        setSize(500, 200);
    }

    public void setSize(double width, double height) {
        rootNode.setMinWidth(width);
        rootNode.setMaxWidth(width);
        rootNode.setMinHeight(height);
        rootNode.setMaxHeight(height);
    }

    public BorderPane getContentBox() {
        return buttonBox;
    }

}
