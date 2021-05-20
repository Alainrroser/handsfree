package ch.bbcag.handsfree.gui;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class HandsFreeTextField extends TextField {

    public HandsFreeTextField() {
        CornerRadii radii = new CornerRadii(2);

        BackgroundFill backgroundFill = new BackgroundFill(
                Colors.PANEL_BACKGROUND,
                radii,
                Insets.EMPTY
        );
        setBackground(new Background(backgroundFill));

        BorderStroke borderStroke = new BorderStroke(
                Colors.BUTTON_BORDER,
                BorderStrokeStyle.SOLID,
                radii,
                new BorderWidths(2)
        );
        setBorder(new Border(borderStroke));

        setFont(HandsFreeFont.getFont(20));

        StringBuilder style = new StringBuilder();
        style.append("-fx-text-fill: ");
        style.append(Colors.colorToCssColor(Colors.FONT));
        style.append("; ");
        style.append("-fx-accent: ");
        style.append(Colors.colorToCssColor(Colors.PRIMARY_BUTTON));
        style.append("; ");
        setStyle(style.toString());
    }

}
