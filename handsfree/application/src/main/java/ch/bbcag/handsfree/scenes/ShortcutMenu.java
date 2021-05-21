package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.shortcuts.Shortcut;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import ch.bbcag.handsfree.control.shortcuts.ShortcutRecorder;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.gui.*;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.handsfree.gui.button.HandsFreeToggleButton;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.gui.dialog.HandsFreeInputDialog;
import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.regex.Pattern;

public class ShortcutMenu extends ScrollScene {

    private ShortcutManager shortcutManager;
    private ShortcutRecorder recorder;

    private static final String SHORTCUT_NAMING_REGEX = "[a-zA-Z]*";

    public ShortcutMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration(), "Shortcuts");

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        shortcutManager = context.getShortcutManager();
        recorder = new ShortcutRecorder(shortcutManager);

        initGUI(application, context);
    }

    private void initGUI(HandsFreeApplication application, HandsFreeContext context) {
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();

        VBox vBox = initVBox();
        VBox vBoxTop = initTop(application);
        HandsFreeListView list = initList();
        list.setRightClickHandler(item -> shortcutManager.runShortcut(item));
        addShortcuts(list);

        HandsFreeToggleButton recordShortcut = initRecordShortcutButton(context, list);
        HandsFreeTextButton deleteShortcut = initDeleteShortcutButton(context, list);

        vBox.getChildren().addAll(vBoxTop, recordShortcut, deleteShortcut, list);
        scrollPane.setContent(vBox);
    }

    private HandsFreeTextButton initDeleteShortcutButton(HandsFreeContext context, HandsFreeListView list) {
        HandsFreeTextButton deleteShortcut = new HandsFreeTextButton("Delete Shortcut");
        deleteShortcut.setOnAction(event -> manageDeleteShortcut(list, context));
        return deleteShortcut;
    }

    private HandsFreeToggleButton initRecordShortcutButton(HandsFreeContext context, HandsFreeListView list) {
        HandsFreeToggleButton recordShortcut = new HandsFreeToggleButton("Recording");
        recordShortcut.setOnEnabled(() -> recorder.start());
        recordShortcut.setOnDisabled(() -> manageShortcutName(context, list));
        recordShortcut.setEnabled(false);
        return recordShortcut;
    }

    private void manageDeleteShortcut(HandsFreeListView list, HandsFreeContext context) {
        if(list.getSelectionModel().getSelectedItem() != null) {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Delete Shortcut", "Do you really want to delete \"Name\"? This shortcut" +
                                                                                          " will be lost forever! (A long time!)");
            dialog.setOnConfirmed(() -> tryToRemoveShortcut(list, context));
            dialog.show();
        } else {
            Error.reportMinor("No shortcut selected!");
        }
    }

    private void tryToRemoveShortcut(HandsFreeListView list, HandsFreeContext context) {
        try {
            shortcutManager.removeShortcut(list.getSelectionModel().getSelectedItem());
            context.getSpeechRecognizer().removeListener(list.getSelectionModel().getSelectedItem());
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        } catch(IOException e) {
            Error.reportMinor(ErrorMessages.DELETE_SHORTCUT);
        }
    }

    private void manageShortcutName(HandsFreeContext context, HandsFreeListView list) {
        if(recorder.isRunning()) {
            HandsFreeInputDialog input = new HandsFreeInputDialog("Name", "Enter a name for this shortcut");
            input.setOnOk(value -> {
                boolean doesNotEqualCommand = checkIfEqualsCommand(value, context);

                checkShortcutName(value, doesNotEqualCommand, list);
            });
            input.setOnCanceled(recorder::stopAndDiscard);
            input.show();
        }
    }

    private void checkShortcutName(String value, boolean doesNotEqualCommand, HandsFreeListView list) {
        if(!shortcutManager.isNotExistingAlready(value)) {
            Error.reportMinor("A shortcut with this name already exists!");
        } else if(value.equals("")) {
            Error.reportMinor("No name specified!");
        } else if(!doesNotEqualCommand) {
            Error.reportMinor("The name of the shortcut is identical to a speech command!");
        } else if(!Pattern.matches(SHORTCUT_NAMING_REGEX, value)) {
            Error.reportMinor("Your shortcut contains invalid characters (only letters are allowed)!");
        } else {
            list.getItems().add(value);
            HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Move files", "Notice that you won't be able to start shortcuts " +
                                                                                     "if you either move the jar file or the shortcut files");
            dialog.show();
            recorder.stopAndSave(value);
        }
    }

    private boolean checkIfEqualsCommand(String value, HandsFreeContext context) {
        boolean doesNotEqualCommand = true;
        for(String command: context.getSpeechRecognizer().getCommandsAndShortcuts()) {
            if(command.equals(value)) {
                doesNotEqualCommand = false;
                break;
            }
        }
        return doesNotEqualCommand;
    }

    private void addShortcuts(HandsFreeListView list) {
        for(Shortcut shortcut: shortcutManager.getShortcuts()) {
            list.getItems().add(shortcut.getName());
        }
    }
}
