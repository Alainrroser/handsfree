package ch.bbcag.handsfree.control.button;

import ch.bbcag.handsfree.control.Colors;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class HandsFreeButton extends Button {

    private HandsFreeButtonPalette palette = HandsFreeButtonPalette.DEFAULT_PALETTE;
    private boolean borderEnabled = false;

    private static final int BORDER_RADIUS = 5;
    private static final int BORDER_WIDTH = 2;

    public HandsFreeButton() {
        updateBackground(Colors.BUTTON);
        configureColorPalette();
    }

    public void setPalette(HandsFreeButtonPalette palette) {
        this.palette = palette;
        configureColorPalette();
    }

    public HandsFreeButtonPalette getPalette() {
        return palette;
    }

    public void addBorder() {
        borderEnabled = true;
        updateBorder();
        updateBackground();
    }

    private void configureColorPalette() {
        setOnMouseEntered(event -> updateBackground(palette.getHoverColor()));
        setOnMouseExited(event -> updateBackground(palette.getDefaultColor()));
        setOnMousePressed(event -> updateBackground(palette.getPressColor()));
        setOnMouseReleased(event -> updateBackground(palette.getDefaultColor()));

        updateBorder();
        updateBackground(palette.getDefaultColor());
    }

    private void updateBorder() {
        if(borderEnabled) {
            BorderStrokeStyle strokeStyle = BorderStrokeStyle.SOLID;
            CornerRadii radii = new CornerRadii(BORDER_RADIUS);
            BorderWidths widths = new BorderWidths(BORDER_WIDTH);
            BorderStroke stroke = new BorderStroke(palette.getBorderColor(), strokeStyle, radii, widths);

            Border border = new Border(stroke);
            setBorder(border);
        } else {
            setBorder(null);
        }
    }

    private void updateBackground() {
        updateBackground(getBackground().getFills().get(0).getFill());
    }

    private void updateBackground(Paint paint) {
        int backgroundRadius = borderEnabled ? BORDER_RADIUS : 0;
        BackgroundFill fill = new BackgroundFill(paint, new CornerRadii(backgroundRadius), Insets.EMPTY);
        Background background = new Background(fill);
        setBackground(background);
    }

}
