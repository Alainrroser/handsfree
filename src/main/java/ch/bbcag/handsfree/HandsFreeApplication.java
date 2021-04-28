package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.control.HandsFreeScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

public class HandsFreeApplication extends Application {

    private static final double WIDTH = 700;
    private static final double HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(20);
        box.setPadding(new Insets(100, 100, 0, 100));
        box.setMinSize(WIDTH, HEIGHT);

        HandsFreeDefaultButton aButton = new HandsFreeDefaultButton("Hello");
        aButton.setOnAction(event -> Platform.runLater(() -> JOptionPane.showMessageDialog(null, "Greetings!")));
        box.getChildren().add(aButton);

        box.getChildren().add(new HandsFreeDefaultButton("World"));
        HandsFreeScene scene = new HandsFreeScene(primaryStage, box, "HandsFree");

        scene.apply();
        primaryStage.show();
    }

}
