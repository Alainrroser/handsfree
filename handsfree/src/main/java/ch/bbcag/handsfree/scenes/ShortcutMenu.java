package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.shortcuts.ShortcutRecorder;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.gui.*;
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

import java.io.IOException;

public class ShortcutMenu extends HandsFreeScene {
    
    private ShortcutManager shortcutManager;
    private ShortcutRecorder recorder;
    
    public ShortcutMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration());
        shortcutManager = context.getShortcutManager();
        recorder = new ShortcutRecorder(shortcutManager);
    
        VBox vBox = new VBox();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBox.setMaxHeight(Double.MAX_VALUE);
    
        VBox vBoxTop = new VBox();
        HBox hBoxBack = new HBox();
        HBox hBoxTitle = new HBox();
    
        HandsFreeIconButton back = new HandsFreeIconButton("/images/back.png");
//        back.setPalette(new HandsFreeButtonPalette(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT));
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
        list.setRightClickHandler(item -> shortcutManager.runShortcut(item));
        list.setMinHeight(600);
        addShortcuts(list);
        
        HandsFreeToggleButton recordShortcut = new HandsFreeToggleButton("Recording");
        recordShortcut.setOnEnabled(() -> recorder.start());
        recordShortcut.setOnDisabled(() -> {
            if(recorder.isRunning()) {
                HandsFreeInputDialog input = new HandsFreeInputDialog("Name", "Enter a name for this shortcut");
                input.setOnOk(value -> {
                    boolean doesNotEqualCommand = true;
                    for(String command : context.getSpeechRecognizer().getCommands()) {
                        if(command.equals(value)) {
                            doesNotEqualCommand = false;
                            break;
                        }
                    }
                    if(shortcutManager.isNotExistingAlready(value) && !value.equals("") && doesNotEqualCommand) {
                        list.getItems().add(value);
                        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Move files", "Notice that you won't be able to start shortcuts if you either move the jar file or the shortcut files");
                        dialog.show();
                        recorder.stopAndSave(value);
                    } else {
                        Error.reportMinor("A Shortcut or a command with this name exists already or you entered no name!");
                    }
                });
                input.setOnCanceled(recorder::stopAndDiscard);
                input.show();
            }
        });
        recordShortcut.setEnabled(false);

        HandsFreeDefaultButton deleteShortcut = new HandsFreeDefaultButton("Delete Shortcut");
        deleteShortcut.setOnAction(event -> {
            if(list.getSelectionModel().getSelectedItem() != null) {
                HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Delete Shortcut", "Do you really want to delete \"Name\"? This shortcut will be lost forever! (A long time!)");
                dialog.setOnConfirmed(() -> {
                    try {
                        shortcutManager.removeShortcut(list.getSelectionModel().getSelectedItem());
                        context.getSpeechRecognizer().removeListener(list.getSelectionModel().getSelectedItem());
                        list.getItems().remove(list.getSelectionModel().getSelectedIndex());
                    } catch(IOException e) {
                        Error.reportMinor(ErrorMessages.DELETE_SHORTCUT);
                    }
                });
                dialog.show();
            } else {
                Error.reportMinor("No shortcut selected!");
            }
        });
    
        vBox.getChildren().addAll(vBoxTop, recordShortcut, deleteShortcut, list);
    
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();
        scrollPane.setMinSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setMaxSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setContent(vBox);
    }
    
    private void addShortcuts(HandsFreeListView list) {
        for(Shortcut shortcut : shortcutManager.getShortcuts()) {
            list.getItems().add(shortcut.getName());
        }
    }
}
