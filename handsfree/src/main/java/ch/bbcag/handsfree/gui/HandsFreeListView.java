package ch.bbcag.handsfree.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

public class HandsFreeListView extends ListView<String> {

    private RightClickCallBack rightClickHandler;
    
    public HandsFreeListView() {
        CornerRadii radii = new CornerRadii(5);
        Insets insets = Insets.EMPTY;
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, radii, insets)));

        BorderWidths borderWidths = new BorderWidths(2);
        BorderStroke stroke = new BorderStroke(Colors.BUTTON_BORDER, BorderStrokeStyle.SOLID, radii, borderWidths);
        setBorder(new Border(stroke));

        setCellFactory(param -> new HandsFreeListCell(this));
    }
    
    public void setRightClickHandler(RightClickCallBack rightClickHandler) {
        this.rightClickHandler = rightClickHandler;
    }
    
    protected RightClickCallBack getRightClickHandler() {
        return rightClickHandler;
    }
    
    public interface RightClickCallBack {
        void run(String item);
    }
}
