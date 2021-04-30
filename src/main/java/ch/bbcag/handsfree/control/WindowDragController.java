package ch.bbcag.handsfree.control;

import javafx.scene.Node;
import javafx.stage.Window;

import java.awt.*;

public class WindowDragController {

    private Window window;
    private Node dragNode;

    private double dragClickXRelativeToStage;
    private double dragClickYRelativeToStage;

    public WindowDragController(Window window, Node dragNode) {
        this.window = window;
        this.dragNode = dragNode;
    }

    public void enable() {
        initDragFunctionality();
    }

    private void initDragFunctionality() {
        dragNode.setOnMousePressed(event -> {
            dragClickXRelativeToStage = event.getSceneX();
            dragClickYRelativeToStage = event.getSceneY();
        });

        dragNode.setOnMouseDragged(event -> {
            double stageX = event.getScreenX() - dragClickXRelativeToStage;
            double stageY = event.getScreenY() - dragClickYRelativeToStage;

            dragStageTo(stageX, stageY);
        });
    }

    private void dragStageTo(double x, double y) {
        // Clamp position to top left border
        x = Math.max(0, x);
        y = Math.max(0, y);

        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        // Clamp position to bottom right border
        if(x + window.getWidth() > screenWidth) {
            x = screenWidth - window.getWidth();
        }
        if(y + window.getHeight() > screenHeight) {
            y = screenHeight - window.getHeight();
        }

        window.setX(x);
        window.setY(y);
    }

}
