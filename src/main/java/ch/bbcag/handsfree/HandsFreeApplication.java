package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeDialog;
import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.control.HandsFreeScene;
import com.sun.javafx.charts.ChartLayoutAnimator;
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
        aButton.setOnAction(event -> new HandsFreeDialog("Dialog"));
        box.getChildren().add(aButton);

        box.getChildren().add(new HandsFreeDefaultButton("World"));

        HandsFreeSceneConfiguration configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle("HandsFree");
        HandsFreeScene scene = new HandsFreeScene(primaryStage, box, configuration);

        scene.apply();
        primaryStage.show();
    }

}
