package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.Launcher;
import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ShortcutRecorderTest extends ApplicationTest {

    @Test
    public void when_shortcut_recorded_file_appears() {
        Launcher.main(new String[]{});

//        clickOn()

        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}