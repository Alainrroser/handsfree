package ch.bbcag.handsfree;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tobii.Tobii;

import java.awt.*;

public class HandsFreeApplication extends Application {

    private static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    private long last = System.currentTimeMillis();

    private double centerX;
    private double centerY;

    private double leftEyeHeight = 100;
    private double rightEyeHeight = 100;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - last) / 1e9;
                last = now;

                GraphicsContext context = canvas.getGraphicsContext2D();

                context.setFill(Color.color(0.0, 0.0, 0.0));
                context.fillRect(0, 0, WIDTH, HEIGHT);

                context.setFill(Color.color(0.8, 0.1, 0.1));

                double targetX = centerX;
                double targetY = centerY;

                if(Tobii.isLeftEyePresent() || Tobii.isRightEyePresent()) {
                    targetX = Tobii.getGazeX() * Toolkit.getDefaultToolkit().getScreenSize().width;
                    targetY = Tobii.getGazeY() * Toolkit.getDefaultToolkit().getScreenSize().height;
                }

                centerX = Interpolator.LINEAR.interpolate(centerX, targetX, deltaTime * 10);
                centerY = Interpolator.LINEAR.interpolate(centerY, targetY, deltaTime * 10);

                context.fillOval(centerX - 100 - 50, centerY - leftEyeHeight / 2, 100, leftEyeHeight);
                context.fillOval(centerX + 100 - 50, centerY - rightEyeHeight / 2, 100, rightEyeHeight);

                if(Tobii.isLeftEyePresent()) {
                    leftEyeHeight += 1500 * deltaTime;
                    if(leftEyeHeight > 100.0) leftEyeHeight = 100.0;
                } else {
                    leftEyeHeight -= 1500 * deltaTime;
                    if(leftEyeHeight < 0.0) leftEyeHeight = 0;
                }

                if(Tobii.isRightEyePresent()) {
                    rightEyeHeight += 1500 * deltaTime;
                    if(rightEyeHeight > 100.0) rightEyeHeight = 100.0;
                } else {
                    rightEyeHeight -= 1500 * deltaTime;
                    if(rightEyeHeight < 0.0) rightEyeHeight = 0;
                }
            }
        };
        timer.start();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("HandsFree");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

}
