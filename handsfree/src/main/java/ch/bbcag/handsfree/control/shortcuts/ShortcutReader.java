package ch.bbcag.handsfree.control.shortcuts;

import java.awt.*;
import java.io.*;

public class ShortcutReader {

    private Shortcut shortcut;

    public Shortcut read(File file) throws IOException {
        shortcut = new Shortcut();
        setShortcutNameFromFileName(file);
        processFile(file);
        return shortcut;
    }

    private void setShortcutNameFromFileName(File file) {
        int dotIndex = file.getName().lastIndexOf(".");
        String shortcutName = file.getName().substring(dotIndex + 1);
        shortcut.setName(shortcutName);
    }

    private void processFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        processLines(reader);
        reader.close();
    }

    private void processLines(BufferedReader reader) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            processLine(line);
        }
    }

    private void processLine(String line) {
        String[] components = line.split(";");
        int x = (int) Double.parseDouble(components[0]);
        int y = (int) Double.parseDouble(components[1]);
        int button = Integer.parseInt(components[2]);

        Click click = new Click(button, new Point(x, y));
        shortcut.getClicks().add(click);
    }

}
