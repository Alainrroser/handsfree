package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.gui.*;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import ch.bbcag.handsfree.gui.button.HandsFreeToggleButton;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.gui.dialog.HandsFreeInputDialog;
import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;
import ch.bbcag.handsfree.control.shortcuts.Shortcut;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ShortcutMenu extends HandsFreeScene {
    
    private ShortcutManager shortcutManager;
    
    public ShortcutMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration());
        shortcutManager = context.getShortcutManager();

        VBox vBox = new VBox();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBox.setMaxHeight(Double.MAX_VALUE);
    
        VBox vBoxTop = new VBox();
        HBox hBoxBack = new HBox();
        HBox hBoxTitle = new HBox();
    
        HandsFreeIconButton back = new HandsFreeIconButton("/images/back.png");
        back.setPalette(new HandsFreeButtonPalette(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT));
        back.setPrefSize(64, 48);
        back.setOnAction(event -> application.getNavigator().navigateTo(SceneType.MAIN_MENU));
        hBoxBack.getChildren().add(back);
    
        Label title = new Label("Shortcuts");
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
        hBoxTitle.getChildren().add(title);
        hBoxTitle.setAlignment(Pos.CENTER);
    
        vBoxTop.getChildren().addAll(hBoxBack, hBoxTitle);
    
        HandsFreeListView list = new HandsFreeListView();
        list.setMaxWidth(Double.MAX_VALUE);
        for(Shortcut shortcut : shortcutManager.getShortcuts()) {
            list.getItems().add(shortcut.getName());
        }
        list.setDoubleClickHandler(item -> shortcutManager.runShortcut(item));
        list.setMinHeight(600);
        
        HandsFreeToggleButton recordShortcut = new HandsFreeToggleButton("Recording");
        recordShortcut.setOnEnabled(() -> shortcutManager.start());
        recordShortcut.setOnDisabled(() -> {
            if(shortcutManager.hasBeenStartedBefore()) {
                HandsFreeInputDialog input = new HandsFreeInputDialog("Name", "Enter a name for this shortcut");
                input.setOnOk(value -> {
                    shortcutManager.getShortcut().setName(value);
                    list.getItems().add(value);
                    HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Move files", "Notice that you won't be able to start shortcuts if you either move the jar file or the shortcut files");
                    dialog.show();
                    shortcutManager.stop();
                });
                input.setOnCanceled(shortcutManager::stop);
                input.show();
            }
        });
        recordShortcut.setEnabled(false);
    
        HandsFreeDefaultButton deleteShortcut = new HandsFreeDefaultButton("Delete Shortcut");
        deleteShortcut.setOnAction(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Delete Shortcut", "Do you really want to delete \"Name\"? This shortcut will be lost forever! (A long time!)");
            dialog.setOnConfirmed(() -> list.getItems().remove(list.getFocusModel().getFocusedIndex()));
            dialog.show();
        });
    
        vBox.getChildren().addAll(vBoxTop, recordShortcut, deleteShortcut, list);
    
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();
        scrollPane.setMinSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setMaxSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setContent(vBox);
    }
}
