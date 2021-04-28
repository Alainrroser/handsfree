package ch.bbcag.handsfree.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.List;

public class HandsFreeScene {

    private Scene scene;
    private Group group;

    private Stage stage;

    public HandsFreeScene(Stage stage, Pane contentRootPane, String title) {
        this.stage = stage;

        group = new Group();
        scene = new Scene(group);

        VBox rootNode = new VBox();
        group.getChildren().add(rootNode);

        HandsFreeStageDecoration decoration = new HandsFreeStageDecoration(stage, title);
        rootNode.getChildren().add(decoration);

        Pane bodyPane = new Pane();
        bodyPane.setBackground(new Background(new BackgroundFill(Colors.BACKGROUND, null, null)));
        bodyPane.setMinSize(contentRootPane.getWidth(), contentRootPane.getHeight());
        bodyPane.getChildren().add(contentRootPane);
        rootNode.getChildren().add(bodyPane);
    }

    public void apply() {
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
    }

}
