package ch.bbcag.handsfree.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HandsFreeListCell extends Label {

    private String item;
    private boolean selected = false;

    public static final double HEIGHT = 45.0;

    public HandsFreeListCell(String item, HandsFreeListView view) {
        this.item = item;

        setFont(HandsFreeFont.getFont(25));
        setTextFill(HandsFreeColors.FONT);
        setPadding(new Insets(0, 0, 0, 10));
        setPrefHeight(HEIGHT);
        setMaxWidth(Double.MAX_VALUE);

        setOnMouseClicked(event -> {
            if(selected) {
                if(view.getClickListener() != null) {
                    view.getClickListener().click(item);
                }
            } else {
                view.selectCell(this);
            }
        });

        update();
    }

    public String getItem() {
        return item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        update();
    }

    private void update() {
        setText(item);
        Color color = selected ? HandsFreeColors.LIST_CELL_SELECTED : HandsFreeColors.LIST_CELL;
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
