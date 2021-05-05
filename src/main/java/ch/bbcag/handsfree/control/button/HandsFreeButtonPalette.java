package ch.bbcag.handsfree.control.button;

import ch.bbcag.handsfree.control.Colors;
import javafx.scene.paint.Color;

public class HandsFreeButtonPalette {

    public static final HandsFreeButtonPalette DEFAULT_PALETTE = new HandsFreeButtonPalette(
            Colors.BUTTON,
            Colors.BUTTON_HOVERED,
            Colors.BUTTON_PRESSED,
            Colors.BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette PRIMARY_PALETTE = new HandsFreeButtonPalette(
            Colors.PRIMARY_BUTTON,
            Colors.PRIMARY_BUTTON_HOVERED,
            Colors.PRIMARY_BUTTON_PRESSED,
            Colors.PRIMARY_BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette ULTRA_PRIMARY_PALETTE = new HandsFreeButtonPalette(
            Colors.ULTRA_PRIMARY_BUTTON,
            Colors.ULTRA_PRIMARY_BUTTON_HOVERED,
            Colors.ULTRA_PRIMARY_BUTTON_PRESSED,
            Colors.ULTRA_PRIMARY_BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette CLOSE_PALETTE = new HandsFreeButtonPalette(
            Colors.BUTTON,
            Colors.CLOSE_BUTTON_HOVERED,
            Colors.CLOSE_BUTTON_PRESSED,
            Colors.BUTTON_BORDER
    );

    private Color defaultColor;
    private Color hoverColor;
    private Color pressColor;
    private Color borderColor;

    public HandsFreeButtonPalette(Color defaultColor, Color hoverColor, Color pressColor, Color borderColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
        this.borderColor = borderColor;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public Color getPressColor() {
        return pressColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

}
