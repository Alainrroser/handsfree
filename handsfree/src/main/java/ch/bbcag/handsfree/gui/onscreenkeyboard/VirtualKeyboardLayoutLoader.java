package ch.bbcag.handsfree.gui.onscreenkeyboard;

import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class VirtualKeyboardLayoutLoader {

    private ArrayList<VirtualKeyRow> rows = new ArrayList<>();
    private ArrayList<VirtualKey> row = new ArrayList<>();
    private VirtualKey currentKey;

    private StringBuilder value = new StringBuilder();
    private int index = 0;
    private boolean readingString = false;

    private int backslashCounter = 0;

    public static VirtualKeyboardLayout loadFromResource(String resource) throws IOException {
        InputStream inputStream = VirtualKeyboardLayoutLoader.class.getResourceAsStream(resource);
        if(inputStream == null) {
            throw new IOException("Couldn't load resource: " + resource);
        }

        VirtualKeyboardLayoutLoader loader = new VirtualKeyboardLayoutLoader();
        return loader.load(inputStream);
    }

    public VirtualKeyboardLayout load(InputStream stream) throws IOException {
        rows.clear();

        for(String line : readLinesFromStream(stream)) {
            processLine(line);
        }

        return new VirtualKeyboardLayout("swiss", rows.toArray(new VirtualKeyRow[0]));
    }

    private ArrayList<String> readLinesFromStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        ArrayList<String> lines = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null) {
            lines.add(line + "\n");
        }
        reader.close();

        return lines;
    }

    private void processLine(String line) {
        if(line.strip().equals("finish row")) {
            finishRow();
        } else {
            processKey(line);
        }
    }

    private void finishRow() {
        rows.add(new VirtualKeyRow(row.toArray(new VirtualKey[0])));
        row = new ArrayList<>();
    }

    private void processKey(String line) {
        currentKey = new VirtualKey();
        value = new StringBuilder();
        index = 0;
        readingString = false;

        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            boolean isValueSeparator = c == ',' && !readingString;

            if(isValueSeparator || c == '\n') {
                finishValue();
            } else {
                if(readingString) {
                    processStringCharacter(c);
                } else {
                    if(c == '\'') {
                        readingString = true;
                    } else {
                        value.append(c);
                    }
                }
            }
        }

        row.add(currentKey);
    }

    private void processStringCharacter(char c) {
        if(c == '\\') {
            backslashCounter++;
            if(isEscaped()) {
                return;
            }
        }

        if(c == '\'' && !isEscaped()) {
            readingString = false;
            backslashCounter = 0;
        } else {
            value.append(c);
        }

        backslashCounter = 0;
    }

    private boolean isEscaped() {
        return backslashCounter % 2 == 1;
    }

    private void finishValue() {
        processValue(value.toString().strip());
        value = new StringBuilder();
        index++;
    }

    private void processValue(String value) {
        if(index == 0) {
            if(value.startsWith("VK_")) {
                try {
                    Field field = KeyEvent.class.getDeclaredField(value);
                    currentKey.setKeyCode(field.getInt(KeyEvent.class));
                } catch(NoSuchFieldException | IllegalAccessException e) {
                    System.out.println("no such field " + value);
                    e.printStackTrace();
                }
            } else {
                currentKey.setKeyCode(Integer.parseInt(value));
            }
        }  else if(index == 1) {
            currentKey.setHold(Boolean.parseBoolean(value));
        } else if(index >= 2 && index <= 5) {
            if(currentKey.getDisplayTexts() == null) {
                currentKey.setDisplayTexts(new String[4]);
            }

            currentKey.getDisplayTexts()[index - 2] = value;
        } else if(index == 6) {
            currentKey.setWidth(Double.parseDouble(value));
        } else if(index == 7) {
            currentKey.setHeight(Double.parseDouble(value));
        }
    }

}
