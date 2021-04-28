package ch.bbcag.handsfree.control.button;

import ch.bbcag.handsfree.control.HandsFreeStageDecoration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HandsFreeIconButton extends HandsFreeButton {

    public HandsFreeIconButton(String iconLocation) {
        Image icon = new Image(HandsFreeStageDecoration.class.getResourceAsStream(iconLocation));
        ImageView view = new ImageView(icon);
        setGraphic(view);
    }

}
