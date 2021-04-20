package ch.bbcag.handsfree;

import javafx.application.Application;
import tobii.Tobii;

import java.awt.*;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        Robot robot = new Robot();

        while(true) {
            float[] position = Tobii.gazePosition();
            float xRelative = position[0];
            float yRelative = position[1];

            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);
            robot.mouseMove(x, y);
        }

//        Application.launch(HandsFreeApplication.class);
    }

}
