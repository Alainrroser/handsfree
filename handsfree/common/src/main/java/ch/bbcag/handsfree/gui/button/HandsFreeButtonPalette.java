package ch.bbcag.handsfree.gui.button;

import ch.bbcag.handsfree.gui.HandsFreeColors;
import javafx.scene.paint.Color;

public class HandsFreeButtonPalette {

    public static final HandsFreeButtonPalette DEFAULT_PALETTE = new HandsFreeButtonPalette(
            HandsFreeColors.BUTTON,
            HandsFreeColors.BUTTON_HOVERED,
            HandsFreeColors.BUTTON_PRESSED,
            HandsFreeColors.BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette PRIMARY_PALETTE = new HandsFreeButtonPalette(
            HandsFreeColors.PRIMARY_BUTTON,
            HandsFreeColors.PRIMARY_BUTTON_HOVERED,
            HandsFreeColors.PRIMARY_BUTTON_PRESSED,
            HandsFreeColors.PRIMARY_BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette ULTRA_PRIMARY_PALETTE = new HandsFreeButtonPalette(
            HandsFreeColors.ULTRA_PRIMARY_BUTTON,
            HandsFreeColors.ULTRA_PRIMARY_BUTTON_HOVERED,
            HandsFreeColors.ULTRA_PRIMARY_BUTTON_PRESSED,
            HandsFreeColors.ULTRA_PRIMARY_BUTTON_BORDER
    );

    public static final HandsFreeButtonPalette CLOSE_PALETTE = new HandsFreeButtonPalette(
            HandsFreeColors.BUTTON,
            HandsFreeColors.CLOSE_BUTTON_HOVERED,
            HandsFreeColors.CLOSE_BUTTON_PRESSED,
            HandsFreeColors.BUTTON_BORDER
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
