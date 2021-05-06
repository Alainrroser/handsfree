package ch.bbcag.handsfree.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class HandsFreeListCell extends ListCell<String> {

    private HandsFreeListView view;
    
    public HandsFreeListCell(HandsFreeListView view) {
        this.view = view;
        
        setFont(HandsFreeFont.getFont(25));
        setTextFill(Colors.FONT);

        updateBackground();
        
        setOnMouseClicked(event -> {
            if(isSelected() && view.getDoubleClickHandler() != null && event.getButton() == MouseButton.SECONDARY) {
                view.getDoubleClickHandler().run(getItem());
            }
        });
    }

    private void updateBackground() {
        Color color = isSelected() ? Colors.LIST_CELL_SELECTED : Colors.LIST_CELL;
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? "" : item);

        updateBackground();
    }
    
}
