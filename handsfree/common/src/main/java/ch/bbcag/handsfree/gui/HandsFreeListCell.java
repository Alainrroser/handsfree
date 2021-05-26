package ch.bbcag.handsfree.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HandsFreeListCell extends Label {

    private String item;
    private boolean selected = false;

    public static final double HEIGHT = 45.0;

    public HandsFreeListCell(String item, HandsFreeListView view) {
        this.item = item;

        setFont(HandsFreeFont.getFont(25));
        setTextFill(Colors.FONT);
        setPadding(new Insets(0, 0, 0, 10));
        setPrefHeight(HEIGHT);
        setMaxWidth(Double.MAX_VALUE);

        setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                view.selectCell(this);
            }
        });

        setOnMouseClicked(event -> {
            if(selected && view.getRightClickHandler() != null && event.getButton() == MouseButton.SECONDARY) {
                view.getRightClickHandler().run(item);
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
        Color color = selected ? Colors.LIST_CELL_SELECTED : Colors.LIST_CELL;
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
