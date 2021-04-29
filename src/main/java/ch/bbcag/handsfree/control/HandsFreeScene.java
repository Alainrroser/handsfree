package ch.bbcag.handsfree.control;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HandsFreeScene {

    private Scene scene;
    private Group group;
    private Pane contentRootPane;

    private Stage stage;

    public HandsFreeScene(Stage stage, Pane contentRootPane, HandsFreeSceneConfiguration configuration) {
        this.stage = stage;
        this.contentRootPane = contentRootPane;

        group = new Group();
        scene = new Scene(group);

        VBox rootNode = new VBox();

        Paint borderColor = Colors.STAGE_BORDER;
        BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.SOLID;
        CornerRadii cornerRadii = CornerRadii.EMPTY;
        BorderWidths borderWidths = new BorderWidths(1);
        BorderStroke borderStroke = new BorderStroke(borderColor, borderStrokeStyle, cornerRadii, borderWidths);
        Border border = new Border(borderStroke);
        rootNode.setBorder(border);

        group.getChildren().add(rootNode);

        HandsFreeStageDecoration decoration = new HandsFreeStageDecoration(stage, configuration);
        rootNode.getChildren().add(decoration);

        Pane bodyPane = new Pane();
        bodyPane.setBackground(new Background(new BackgroundFill(Colors.BACKGROUND, null, null)));
        bodyPane.setMinSize(contentRootPane.getWidth(), contentRootPane.getHeight());
        bodyPane.getChildren().add(contentRootPane);
        rootNode.getChildren().add(bodyPane);
    }

    public Pane getContentRootPane() {
        return contentRootPane;
    }

    public void apply() {
        stage.setScene(scene);

        if(!stage.isShowing()) {
            stage.initStyle(StageStyle.UNDECORATED);
        }
    }

    public void centerStageOnScreen() {
        stage.centerOnScreen();
    }

}
