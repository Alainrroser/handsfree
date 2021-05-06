package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.shortcuts.ShortcutReader;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
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

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ShortcutMenu extends HandsFreeScene {
    
    private ShortcutManager shortcutManager;
    
    private File directory = new File(Const.SHORTCUT_PATH);
    
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
        list.setDoubleClickHandler(item -> shortcutManager.runShortcut(item));
        readShortcuts();
        list.setMinHeight(600);
        addShortcuts(list);
    
        String[] allowedCharacters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-"};
        String[] reservedFileNames = {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM4", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
        HandsFreeToggleButton recordShortcut = new HandsFreeToggleButton("Recording");
        recordShortcut.setOnEnabled(() ->  shortcutManager.start());
        recordShortcut.setOnDisabled(() -> {
            if(shortcutManager.isRunning()) {
                HandsFreeInputDialog input = new HandsFreeInputDialog("Name", "Enter a name for this shortcut");
                input.setOnOk(value -> {
                    boolean containsOnlyAllowedCharacters = true;
                    boolean isNoReservedFileName = true;
                    boolean doesNotExistAlready = true;
                    boolean isValid = false;
                    
                    for(String allowedCharacter : allowedCharacters) {
                        if(!value.toLowerCase().contains(allowedCharacter)) {
                            containsOnlyAllowedCharacters = false;
                            break;
                        }
                    }
                    for(String reservedFileName : reservedFileNames) {
                        if(value.equals(reservedFileName)) {
                            isNoReservedFileName = false;
                            break;
                        }
                    }
                    for(Shortcut shortcut : shortcutManager.getShortcuts()) {
                        if(value.equals(shortcut.getName())) {
                            doesNotExistAlready = false;
                            break;
                        }
                    }
                    
                    if(value.length() <= 20 && !value.startsWith("-") && doesNotExistAlready && containsOnlyAllowedCharacters && isNoReservedFileName) {
                        isValid = true;
                    }
                    
                    if(isValid) {
                        shortcutManager.getShortcut().setName(value);
                        list.getItems().add(value);
                        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Move files", "Notice that you won't be able to start shortcuts if you either move the jar file or the shortcut files");
                        dialog.show();
                        shortcutManager.stopAndSave();
                    } else {
                        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Shortcut Name", "The shortcut name can only contain letters, numbers and hyphens, has to be smaller than 20 characters and isn't allowed to start with a hyphen!");
                        dialog.show();
                    }
                });
                input.setOnCanceled(shortcutManager::stopAndDiscard);
                input.show();
            }
        });
        recordShortcut.setEnabled(false);
    
        HandsFreeDefaultButton deleteShortcut = new HandsFreeDefaultButton("Delete Shortcut");
        deleteShortcut.setOnAction(event -> {
            if(list.getItems().size() != 0) {
                HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Delete Shortcut", "Do you really want to delete \"Name\"? This shortcut will be lost forever! (A long time!)");
                dialog.setOnConfirmed(() -> list.getItems().remove(list.getFocusModel().getFocusedIndex()));
                deleteShortcutFile(list);
                dialog.show();
            } else {
                HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("No Shortcuts", "You have to record a Shortcut before you can delete it again!");
                dialog.show();
            }
        });
    
        vBox.getChildren().addAll(vBoxTop, recordShortcut, deleteShortcut, list);
    
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();
        scrollPane.setMinSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setMaxSize(Const.WIDTH, Const.HEIGHT);
        scrollPane.setContent(vBox);
    }
    
    private void readShortcuts() {
        ShortcutReader reader = new ShortcutReader();
        
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            try {
                shortcutManager.getShortcuts().add(reader.read(file));
            } catch(IOException e) {
                Error.reportCritical(ErrorMessages.READ_SHORTCUT, e);
            }
        }
    }
    
    private void addShortcuts(HandsFreeListView list) {
        for(Shortcut shortcut : shortcutManager.getShortcuts()) {
            list.getItems().add(shortcut.getName());
        }
    }
    
    private void deleteShortcutFile(HandsFreeListView list) {
        String fileName = list.getFocusModel().getFocusedItem() + Const.SHORTCUT_FILE_EXTENSION;
        
        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(fileName.equals(file.getName())) {
                file.delete();
            }
        }
    }
}
