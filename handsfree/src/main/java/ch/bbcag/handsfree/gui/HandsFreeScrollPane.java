package ch.bbcag.handsfree.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class HandsFreeScrollPane extends StackPane {

    private Region content;
    private HandsFreeScrollBar scrollBar;

    public HandsFreeScrollPane() {
        scrollBar = new HandsFreeScrollBar();
        scrollBar.setMaxHeight(600);
        scrollBar.setScrollCallback(scroll -> content.setTranslateY(-scroll));

        widthProperty().addListener(observable -> updateClip());
        heightProperty().addListener(observable -> updateClip());

        setOnScroll(scrollBar.getOnScroll());
    }

    public void updateClip() {
        Rectangle clip = new Rectangle();
        clip.setWidth(getWidth());
        clip.setHeight(getHeight());
        setClip(clip);
    }

    public void setContent(Region content) {
        this.content = content;
        getChildren().add(content);
        StackPane.setAlignment(content, Pos.TOP_CENTER);

        StackPane.setAlignment(scrollBar, Pos.TOP_RIGHT);
        getChildren().add(scrollBar);

        content.heightProperty().addListener(observable -> scrollBar.setContentHeight(content.getHeight()));
    }

}
