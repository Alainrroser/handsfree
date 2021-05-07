package ch.bbcag.handsfree.gui;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.eyetracker.RegionGazeHandler;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class HandsFreeIconifiedWidget {

    // We have decided to use a JFrame instead of a stage because it
    // is easier to position in the bottom right corner and because stages
    // can't be set to utility AND undecorated at the same time as opposed to JFrames.
    private JFrame frame;

    private HandsFreeIconButton button;

    private Stage stage;

    public HandsFreeIconifiedWidget(Stage stage, HandsFreeContext context) {
        this.stage = stage;

        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setType(Window.Type.UTILITY);

        button = new HandsFreeIconButton("/images/icon64.png");
        button.setPrefSize(64, 64);
        button.setOnAction(event -> returnToStage());

        Group root = new Group();
        root.getChildren().add(button);
        Scene scene = new Scene(root);

        JFXPanel panel = new JFXPanel();
        panel.setScene(scene);
        frame.setContentPane(panel);

        updateBounds();

        RegionGazeHandler regionGazeHandler = new RegionGazeHandler(button, 500, (x, y) -> button.fire());
        context.getEyeTracker().addRegionGazeHandler(regionGazeHandler);
    }

    private void returnToStage() {
        stage.show();
        stage.setIconified(false);
        stage.requestFocus();
        frame.dispose();
    }

    private void updateBounds() {
        frame.setSize((int) button.getWidth(), (int) button.getHeight());

        frame.setLocation(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - frame.getWidth(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - frame.getHeight()
        );
    }

    public void show() {
        frame.setVisible(true);
    }

}
