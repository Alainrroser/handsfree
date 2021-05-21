package ch.bbcag.handsfree.gui.button;

import ch.bbcag.handsfree.gui.HandsFreeStageDecoration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class HandsFreeIconButton extends HandsFreeButton {

    private ImageView view;

    public HandsFreeIconButton() {
        addBorder();
        setGraphic(view = new ImageView());
    }

    public HandsFreeIconButton(String iconLocation) {
        Image icon = new Image(Objects.requireNonNull(HandsFreeStageDecoration.class.getResourceAsStream(iconLocation)));
        setGraphic(view = new ImageView(icon));
    }

    public Image getImage() {
        return view.getImage();
    }

    public void setImage(Image image) {
        view.setImage(image);
    }

    public void setContentSize(double width, double height) {
        view.setFitWidth(width);
        view.setFitHeight(height);
    }

    public void setButtonSize(double width, double height) {
        setMinSize(width, height);
        setMaxSize(width, height);
    }

}
