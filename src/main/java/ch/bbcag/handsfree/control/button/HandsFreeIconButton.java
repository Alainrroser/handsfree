package ch.bbcag.handsfree.control.button;

import ch.bbcag.handsfree.control.HandsFreeStageDecoration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class HandsFreeIconButton extends HandsFreeButton {
    ImageView view;
    
    public HandsFreeIconButton(String iconLocation) {
        Image icon = new Image(Objects.requireNonNull(HandsFreeStageDecoration.class.getResourceAsStream(iconLocation)));
        this.view = new ImageView(icon);
        setGraphic(view);
    }
    
    @Override
    public void setPrefSize(double width, double height) {
        super.setPrefSize(width, height);
        view.setFitWidth(width);
        view.setFitHeight(height);
    }

}
