package ch.bbcag.handsfree.gui;

import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public class HandsFreeCheckBox extends HBox {

    private boolean selected = false;

    private HandsFreeIconButton checkBox;
    private Label label;

    private static final Image ICON = new Image(HandsFreeCheckBox.class.getResourceAsStream("/images/check-mark.png"));

    public HandsFreeCheckBox(String text) {
        super(10);

        checkBox = new HandsFreeIconButton();
        checkBox.setButtonSize(32, 32);
        getChildren().add(checkBox);

        label = new Label(text);
        label.setFont(HandsFreeFont.getFont(22));
        label.setTextFill(Colors.FONT);
        getChildren().add(label);

        checkBox.setOnAction(event -> switchState());
        label.setOnMousePressed(event -> switchState());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if(selected) {
            checkBox.setImage(ICON);
        } else {
            checkBox.setImage(null);
        }
    }

    private void switchState() {
        setSelected(!isSelected());
    }

}
