package ch.bbcag.handsfree.control.button;

import ch.bbcag.handsfree.control.Colors;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class HandsFreeButton extends Button {

    private int borderRadius = 0;

    public HandsFreeButton() {
        updateBackground(Colors.BUTTON);

        setOnMouseEntered(event -> updateBackground(Colors.BUTTON_HOVERED));
        setOnMouseExited(event -> updateBackground(Colors.BUTTON));
        setOnMousePressed(event -> updateBackground(Colors.BUTTON_PRESSED));
        setOnMouseReleased(event -> updateBackground(Colors.BUTTON));
    }

    public void addBorder(int width, Paint paint, int radius) {
        BorderStrokeStyle strokeStyle = BorderStrokeStyle.SOLID;
        CornerRadii radii = new CornerRadii(radius);
        BorderWidths widths = new BorderWidths(width);
        BorderStroke stroke = new BorderStroke(paint, strokeStyle, radii, widths);

        Border border = new Border(stroke);
        setBorder(border);

        this.borderRadius = radius;
        updateBackground();
    }

    private void updateBackground() {
        updateBackground(getBackground().getFills().get(0).getFill());
    }

    private void updateBackground(Paint paint) {
        BackgroundFill fill = new BackgroundFill(paint, new CornerRadii(borderRadius), Insets.EMPTY);
        Background background = new Background(fill);
        setBackground(background);
    }

}
