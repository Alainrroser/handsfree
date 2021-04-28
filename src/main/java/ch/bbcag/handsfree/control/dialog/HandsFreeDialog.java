package ch.bbcag.handsfree.control.dialog;

import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HandsFreeDialog extends Stage {

    private BorderPane buttonBox;

    public HandsFreeDialog(String title, String text) {
        BorderPane rootNode = new BorderPane();
        rootNode.setMinSize(500, 200);
        rootNode.setPadding(new Insets(0, 30, 30, 30));

        Label textLabel = new Label(text);
        textLabel.setFont(HandsFreeFont.getFont(20));
        textLabel.setTextFill(Colors.FONT);
        rootNode.setCenter(textLabel);

        buttonBox = new BorderPane();
        rootNode.setBottom(buttonBox);

        HandsFreeSceneConfiguration configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle(title);
        configuration.setHasMinimizeButton(false);

        HandsFreeScene scene = new HandsFreeScene(this, rootNode, configuration);
        scene.apply();
    }

    public BorderPane getButtonBox() {
        return buttonBox;
    }

}