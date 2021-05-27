package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.*;
import ch.bbcag.handsfree.error.NativeException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;

@ExtendWith(ApplicationExtension.class)
class ShortcutManagerTest extends ApplicationTest {

    private ShortcutManager shortcutManager;

    private static final File SHORTCUT_FOLDER = new File(ApplicationConstants.SHORTCUT_FOLDER);

    @Override
    public void start(Stage primaryStage) throws NativeException {
        HandsFreeContext context = new HandsFreeContext(primaryStage);
        shortcutManager = new ShortcutManager(context, primaryStage);
    }

    @Test
    public void when_shortcut_added_file_appears() {
        deleteShortcutFiles();

        Shortcut shortcut = new Shortcut();
        shortcut.setName("Test");
        shortcutManager.addShortcut(shortcut);

        Assertions.assertThat(countShortcutFiles()).isEqualTo(1);
    }

    @Test
    public void when_shortcut_added_file_contains_data() throws IOException {
        deleteShortcutFiles();

        Shortcut shortcut = new Shortcut();
        shortcut.setName("Test");
        shortcut.getClicks().add(new Click(InputEvent.BUTTON1_DOWN_MASK, 1000, new Point(0, 100)));
        shortcut.getClicks().add(new Click(InputEvent.BUTTON1_DOWN_MASK, 2000, new Point(1000, 0)));
        shortcut.setKeyboardVisible(true);
        shortcut.setKeyboardX(100);
        shortcut.setKeyboardY(50);
        shortcutManager.addShortcut(shortcut);

        File shortcutFile = SHORTCUT_FOLDER.listFiles()[0];
        FileInputStream in = new FileInputStream(shortcutFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Assertions.assertThat(reader.readLine()).isEqualTo("Test");
        Assertions.assertThat(Boolean.parseBoolean(reader.readLine())).isEqualTo(true);
        Assertions.assertThat(Double.parseDouble(reader.readLine())).isEqualTo(100.0);
        Assertions.assertThat(Double.parseDouble(reader.readLine())).isEqualTo(50.0);

        reader.close();
    }

    private void deleteShortcutFiles() {
        for(File child : SHORTCUT_FOLDER.listFiles()) {
            child.delete();
        }
    }

    private int countShortcutFiles() {
        return SHORTCUT_FOLDER.listFiles().length;
    }

}