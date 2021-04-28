package ch.bbcag.handsfree.control;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HandsFreeDialog extends Stage {

    public HandsFreeDialog(String title) {
        BorderPane rootNode = new BorderPane();
        rootNode.setMinSize(500, 200);

        HandsFreeSceneConfiguration configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle(title);
        configuration.setHasMinimizeButton(false);

        HandsFreeScene scene = new HandsFreeScene(this, rootNode, configuration);
        scene.apply();
        show();
    }

}
