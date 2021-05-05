package ch.bbcag.handsfree.gui.button;

import ch.bbcag.handsfree.gui.Colors;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class HandsFreeButton extends Button {

    private HandsFreeButtonPalette palette = HandsFreeButtonPalette.DEFAULT_PALETTE;
    private boolean borderEnabled = false;

    private boolean pressed = false;
    private boolean hovered = false;

    private EventHandler<MouseEvent> mousePressedHandler;

    private static final int BORDER_RADIUS = 5;
    private static final int BORDER_WIDTH = 2;

    public HandsFreeButton() {
        setFocusTraversable(false);

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
        setOnMouseEntered(event -> {
            hovered = true;
            updateBackground();
        });

        setOnMouseExited(event -> {
            hovered = false;
            updateBackground();
        });

        setOnMousePressed(event -> {
            pressed = true;
            updateBackground();
            if(mousePressedHandler != null) {
                mousePressedHandler.handle(event);
            }
        });

        setOnMouseReleased(event -> {
            pressed = false;
            updateBackground();
        });

        updateBorder();
        updateBackground(palette.getDefaultColor());
    }

    public void setMousePressedHandler(EventHandler<MouseEvent> eventHandler) {
        this.mousePressedHandler = eventHandler;
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
        if(pressed) {
            updateBackground(palette.getPressColor());
        } else {
            if(hovered) {
                updateBackground(palette.getHoverColor());
            } else {
                updateBackground(palette.getDefaultColor());
            }
        }
    }

    private void updateBackground(Paint paint) {
        int backgroundRadius = borderEnabled ? BORDER_RADIUS : 0;
        BackgroundFill fill = new BackgroundFill(paint, new CornerRadii(backgroundRadius), Insets.EMPTY);
        Background background = new Background(fill);
        setBackground(background);
    }

}
