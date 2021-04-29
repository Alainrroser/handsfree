package ch.bbcag.handsfree.control;

import ch.bbcag.handsfree.control.button.HandsFreeButton;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class HandsFreeScrollPane extends StackPane {

    private Region content;

    private VBox scrollbar;
    private HandsFreeButton scrollBarThumb;
    private double dragLastMouseY;

    public HandsFreeScrollPane() {
        scrollbar = new VBox();
        scrollbar.setMaxHeight(Double.MAX_VALUE);
        scrollbar.setPrefWidth(50);
        scrollbar.setMaxWidth(50);
        scrollbar.setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, null, null)));
        StackPane.setAlignment(scrollbar, Pos.TOP_RIGHT);

        scrollBarThumb = new HandsFreeButton();
        scrollBarThumb.setMaxWidth(Double.MAX_VALUE);

        scrollBarThumb.setOnMousePressed(event -> dragLastMouseY = event.getScreenY());

        scrollBarThumb.setOnMouseDragged(event -> {
            double offset = event.getScreenY() - dragLastMouseY;
            scroll(-offset);
            dragLastMouseY = event.getScreenY();
        });

        setOnScroll(event -> scroll(event.getDeltaY()));

        widthProperty().addListener(observable -> updateClip());
        heightProperty().addListener(observable -> updateClip());
    }

    private void scroll(double amount) {
        scrollBarThumb.setTranslateY(scrollBarThumb.getTranslateY() - amount);
        if(scrollBarThumb.getTranslateY() < 0) scrollBarThumb.setTranslateY(0);
        if(scrollBarThumb.getTranslateY() + scrollBarThumb.getHeight() > scrollbar.getHeight()) {
            scrollBarThumb.setTranslateY(scrollbar.getHeight() - scrollBarThumb.getHeight());
        }

        content.setTranslateY(-scrollBarThumb.getTranslateY());
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

        getChildren().add(scrollbar);
        scrollbar.getChildren().add(scrollBarThumb);

        content.widthProperty().addListener(observable -> updateScrollBar());
        content.heightProperty().addListener(observable -> updateScrollBar());
    }

    private void updateScrollBar() {
        double height = (content.getHeight() - getHeight());
        scrollBarThumb.setMinHeight(height);
        scrollBarThumb.setMaxHeight(height);
    }

}
