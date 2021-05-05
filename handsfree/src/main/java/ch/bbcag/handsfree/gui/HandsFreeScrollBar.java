package ch.bbcag.handsfree.gui;

import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HandsFreeScrollBar extends VBox {

    private VBox track;
    private HandsFreeButton thumb;
    private double dragLastMouseY;

    private HandsFreeScrollCallback callback;
    private double contentHeight;

    private static final double WIDTH = 50;
    private static final double BUTTON_SCROLL_AMOUNT = 40;

    public HandsFreeScrollBar() {
        setMaxWidth(WIDTH);
        setMinWidth(WIDTH);
        setMaxHeight(Double.MAX_VALUE);

        HandsFreeButton incrementButton = new HandsFreeIconButton("/images/down.png");
        incrementButton.setMaxWidth(Double.MAX_VALUE);
        incrementButton.setMinHeight(WIDTH);
        incrementButton.setOnAction(event -> scroll(BUTTON_SCROLL_AMOUNT));

        HandsFreeButton decrementButton = new HandsFreeIconButton("/images/up.png");
        decrementButton.setMaxWidth(Double.MAX_VALUE);
        decrementButton.setMinHeight(WIDTH);
        decrementButton.setOnAction(event -> scroll(-BUTTON_SCROLL_AMOUNT));

        track = new VBox();
        track.setMaxHeight(Double.MAX_VALUE);
        track.setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, null, null)));
        VBox.setVgrow(track, Priority.ALWAYS);

        thumb = new HandsFreeButton();
        thumb.setMaxWidth(Double.MAX_VALUE);
        track.getChildren().add(thumb);

        getChildren().addAll(decrementButton, track, incrementButton);

        thumb.setMousePressedHandler(event -> dragLastMouseY = event.getScreenY());

        thumb.setOnMouseDragged(event -> {
            double offset = event.getScreenY() - dragLastMouseY;
            scroll(offset);
            dragLastMouseY = event.getScreenY();
        });

        setOnScroll(event -> scroll(-event.getDeltaY()));

        heightProperty().addListener(observable -> update());
        track.heightProperty().addListener(observable -> update());
        thumb.heightProperty().addListener(observable -> update());
    }

    private void scroll(double amount) {
        thumb.setTranslateY(thumb.getTranslateY() + amount);

        if(thumb.getTranslateY() < 0) {
            thumb.setTranslateY(0);
        }
        if(thumb.getTranslateY() + thumb.getHeight() > track.getHeight()) {
            thumb.setTranslateY(track.getHeight() - thumb.getHeight());
        }

        double progress = thumb.getTranslateY() / (track.getHeight() - thumb.getHeight());
        double scroll = progress * (contentHeight - getHeight());
        if(callback != null) {
            callback.handle(scroll);
        }
    }

    public void setScrollCallback(HandsFreeScrollCallback callback) {
        this.callback = callback;
    }

    public void setContentHeight(double contentHeight) {
        this.contentHeight = contentHeight;
        update();
    }

    public void update() {
        double height = getHeight() * (getHeight() / contentHeight);
        height = Math.max(height, WIDTH);

        thumb.setMinHeight(height);
        thumb.setMaxHeight(height);
    }

}
