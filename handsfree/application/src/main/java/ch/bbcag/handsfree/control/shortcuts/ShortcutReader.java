package ch.bbcag.handsfree.control.shortcuts;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

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
        String shortcutName = file.getName().substring(0, dotIndex);
        shortcut.setName(shortcutName);
    }

    private void processFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        processLines(reader);
        reader.close();
    }

    private void processLines(BufferedReader reader) throws IOException {
        String line;
        int lineIndex = 0;
        while((line = reader.readLine()) != null) {
            processLine(line, lineIndex);
            lineIndex++;
        }
    }

    private void processLine(String line, int lineIndex) {
        if(lineIndex == 0) {
            shortcut.setName(line);
        } else if(lineIndex == 1) {
            shortcut.setKeyboardVisible(Boolean.parseBoolean(line));
        } else if(lineIndex == 2) {
            shortcut.setKeyboardX(Double.parseDouble(line));
        } else if(lineIndex == 3) {
            shortcut.setKeyboardY(Double.parseDouble(line));
        } else {
            String[] components = line.split(";");
            int x = Integer.parseInt(components[0]);
            int y = Integer.parseInt(components[1]);
            int button = Integer.parseInt(components[2]);
            int time = Integer.parseInt(components[3]);

            Click click = new Click(button, time, new Point(x, y));
            shortcut.getClicks().add(click);
        }
    }

}
