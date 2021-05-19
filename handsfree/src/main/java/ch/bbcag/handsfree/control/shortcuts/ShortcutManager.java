package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.utils.JarFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShortcutManager {

    private HandsFreeContext context;
    private boolean notExistingAlready = true;
    private List<Shortcut> shortcuts = new ArrayList<>();

    public ShortcutManager(HandsFreeContext context) {
        this.context = context;
    }

    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }

    public void addShortcut(Shortcut shortcut) {
        try {
            shortcuts.add(shortcut);
            ShortcutWriter writer = new ShortcutWriter();
            writer.write(shortcut, new File(Const.SHORTCUT_PATH));
            context.getSpeechRecognizer().addListener(shortcut.getName(), () -> runShortcut(shortcut.getName()));
        } catch(IOException e) {
            Error.reportCritical(ErrorMessages.WRITE_SHORTCUT, e);
        }
    }

    public void removeShortcut(String name) throws IOException {
        for(Shortcut shortcut: shortcuts) {
            if(shortcut.getName() != null && shortcut.getName().equalsIgnoreCase(name)) {
                shortcuts.remove(shortcut);
                break;
            }
        }

        deleteShortcut(name);
    }

    private void deleteShortcut(String name) throws IOException {
        for(File file: Objects.requireNonNull(new File(Const.SHORTCUT_PATH).listFiles())) {
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String firstLine = reader.readLine();
            reader.close();

            if(firstLine.equals(name)) {
                file.delete();
                break;
            }
        }
    }

    public void runShortcut(String name) {
        for(Shortcut shortcut: shortcuts) {
            if(shortcut.getName().equals(name)) {
                shortcut.run(context.getRobot());
            }
        }
    }

    public void readShortcuts(File directory) {
        if(directory.listFiles() != null) {
            for(File file: Objects.requireNonNull(directory.listFiles())) {
                addShortcutFromFile(file);
            }
        }
    }

    private void addShortcutFromFile(File file) {
        ShortcutReader reader = new ShortcutReader();
        try {
            Shortcut shortcut = reader.read(file);
            getShortcuts().add(shortcut);
            context.getSpeechControl().addListenerForShortcut(shortcut.getName());
        } catch(IOException e) {
            Error.reportCritical(ErrorMessages.READ_SHORTCUT, e);
        }
    }

    public boolean isNotExistingAlready(String name) {
        checkIfExisting(name);
        return notExistingAlready;
    }

    public void setNotExistingAlready(boolean notExistingAlready) {
        this.notExistingAlready = notExistingAlready;
    }

    public void checkIfExisting(String name) {
        setNotExistingAlready(true);
        if(shortcuts.size() > 1) {
            for(Shortcut shortcut: shortcuts) {
                checkIfNamesAreEqual(shortcut, name);
            }
        }
    }

    private void checkIfNamesAreEqual(Shortcut shortcut, String name) {
        if(shortcut.getName() != null && shortcut.getName().equalsIgnoreCase(name)) {
            setNotExistingAlready(false);
        }
    }
}
