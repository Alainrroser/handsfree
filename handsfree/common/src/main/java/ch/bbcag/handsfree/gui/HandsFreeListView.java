package ch.bbcag.handsfree.gui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class HandsFreeListView extends VBox {

    private List<HandsFreeListCell> cells = new CopyOnWriteArrayList<>();
    private HandsFreeListCell selectedCell;

    private HandsFreeListViewClickListener clickListener;

    public HandsFreeListView() {
        CornerRadii radii = new CornerRadii(5);
        Insets insets = Insets.EMPTY;
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, radii, insets)));

        BorderWidths borderWidths = new BorderWidths(2);
        BorderStroke stroke = new BorderStroke(Colors.BUTTON_BORDER, BorderStrokeStyle.SOLID, radii, borderWidths);
        setBorder(new Border(stroke));

        updateHeight();

        setFocusTraversable(true); // We need this so we can catch key events

        setOnKeyPressed(event -> {
            if(selectedCell != null) {
                int selectedIndex = cells.indexOf(selectedCell);

                if(event.getCode() == KeyCode.DOWN && selectedIndex < cells.size() - 1) {
                    selectCell(cells.get(selectedIndex + 1));
                } else if(event.getCode() == KeyCode.UP && selectedIndex > 0) {
                    selectCell(cells.get(selectedIndex - 1));
                }
            }
        });
    }

    public String getSelectedItem() {
        return selectedCell == null ? null : selectedCell.getItem();
    }

    public void addItem(String item) {
        HandsFreeListCell cell = new HandsFreeListCell(item, this);
        getChildren().add(cell);
        cells.add(cell);

        updateHeight();
    }

    public void removeItem(String item) {
        for(HandsFreeListCell cell : cells) {
            if(cell.getItem().equals(item)) {
                getChildren().remove(cell);
                cells.remove(cell);
            }
        }

        updateHeight();
    }

    private void updateHeight() {
        if(cells.isEmpty()) {
            setMinHeight(100);
        } else {
            setMinHeight(cells.size() * HandsFreeListCell.HEIGHT);
        }
    }

    protected void selectCell(HandsFreeListCell cell) {
        if(selectedCell != null) {
            selectedCell.setSelected(false);
        }

        cell.setSelected(true);
        selectedCell = cell;
    }

    public void setClickListener(HandsFreeListViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    protected HandsFreeListViewClickListener getClickListener() {
        return clickListener;
    }

}
