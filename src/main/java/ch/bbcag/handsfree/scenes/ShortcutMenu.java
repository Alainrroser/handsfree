package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ShortcutMenu extends HandsFreeScene {
    public ShortcutMenu(Navigator navigator, HandsFreeApplication application) {
        super(application.getPrimaryStage(), application.getVBox(), application.getConfiguration());
        
        VBox vBox = application.getVBox();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBox.setMinSize(Const.WIDTH, Const.HEIGHT);
        
        String titleText = "Shortcuts";
        
        Label title = new Label(titleText);
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
    
        String recordShortcutText = "Record Shortcut";
        String deleteShortcutText = "Delete Shortcut";
        
        HandsFreeDefaultButton recordShortcut = new HandsFreeDefaultButton(recordShortcutText);
        HandsFreeDefaultButton deleteShortcut = new HandsFreeDefaultButton(deleteShortcutText);
        
        vBox.getChildren().addAll(title, recordShortcut, deleteShortcut);
    }
}
