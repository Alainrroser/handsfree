package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.control.HandsFreeKeyCodes;
import ch.bbcag.handsfree.error.KeyboardLoadingException;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class VirtualKeyboardLayoutLoader {

    private String name;
    private ArrayList<VirtualKeyRow> rows = new ArrayList<>();
    private ArrayList<VirtualKey> row = new ArrayList<>();
    private VirtualKey currentKey;

    private StringBuilder value = new StringBuilder();
    private int index = 0;
    private boolean readingString = false;
    private boolean escaped = false;

    public static VirtualKeyboardLayout loadFromResource(String resource) throws KeyboardLoadingException {
        InputStream inputStream = VirtualKeyboardLayoutLoader.class.getResourceAsStream(resource);
        if(inputStream == null) {
            throw new KeyboardLoadingException("Couldn't load resource: " + resource);
        }

        VirtualKeyboardLayoutLoader loader = new VirtualKeyboardLayoutLoader();
        return loader.load(inputStream);
    }

    public VirtualKeyboardLayout load(InputStream stream) {
        reset();

        try {
            for(String line : readLinesFromStream(stream)) {
                processLine(line);
            }

            return new VirtualKeyboardLayout(name, rows.toArray(new VirtualKeyRow[0]));
        } catch(Exception e) {
            throw new KeyboardLoadingException(e.getMessage());
        }
    }

    private void reset() {
        name = "";
        rows.clear();
    }

    private ArrayList<String> readLinesFromStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        ArrayList<String> lines = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        return lines;
    }

    private void processLine(String line) {
        line = line.strip();

        if(line.equals("finish row")) {
            finishRow();
        } else if(line.startsWith("name")) {
            processName(line);
        } else {
            processKey(line);
        }
    }

    private void finishRow() {
        rows.add(new VirtualKeyRow(row.toArray(new VirtualKey[0])));
        row = new ArrayList<>();
    }

    private void processName(String line) {
        name = line.split(" ")[1];
    }

    private void processKey(String line) {
        resetKeyValues();

        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            processCharacter(c);
        }

        if(value.length() > 0) {
            finishValue();
        }

        row.add(currentKey);
    }

    private void resetKeyValues() {
        currentKey = new VirtualKey();
        value = new StringBuilder();
        index = 0;
        readingString = false;
        escaped = false;
    }

    private void processCharacter(char c) {
        boolean isValueSeparator = c == ',' && !readingString;

        if(isValueSeparator) {
            finishValue();
        } else if(readingString) {
            processStringCharacter(c);
        } else if(c == '\'') {
            readingString = true;
        } else {
            value.append(c);
        }
    }

    private void processStringCharacter(char c) {
        if(c == '\\') {
            escaped = !escaped;
            if(escaped) {
                return;
            }
        }

        processNonEscapingStringCharacter(c);
    }

    private void processNonEscapingStringCharacter(char c) {
        boolean isEndOfString = c == '\'' && !escaped;
        if(isEndOfString) {
            readingString = false;
        } else {
            value.append(c);
        }

        escaped = false;
    }

    private void finishValue() {
        processValue(value.toString().strip());
        value = new StringBuilder();
        index++;
    }

    private void processValue(String value) {
        if(index == 0) {
            processKeyCode(value);
        } else if(index == 1) {
            processHold(value);
        } else if(index >= 2 && index <= 5) {
            processDisplayText(value);
        } else if(index == 6) {
            processWidth(value);
        } else if(index == 7) {
            processHeight(value);
        }
    }

    private void processKeyCode(String value) {
        if(Character.isAlphabetic(value.charAt(0))) {
            try {
                Field field = HandsFreeKeyCodes.class.getDeclaredField(value);
                currentKey.setKeyCode(field.getInt(KeyEvent.class));
            } catch(NoSuchFieldException | IllegalAccessException e) {
                System.out.println("no such field " + value);
                e.printStackTrace();
            }
        } else {
            currentKey.setKeyCode(Integer.parseInt(value));
        }
    }

    private void processHold(String value) {
        currentKey.setHold(Boolean.parseBoolean(value));
    }

    private void processDisplayText(String value) {
        if(currentKey.getDisplayTexts() == null) {
            currentKey.setDisplayTexts(new String[4]);
        }

        currentKey.getDisplayTexts()[index - 2] = value;
    }

    private void processWidth(String value) {
        currentKey.setWidth(Double.parseDouble(value));
    }

    private void processHeight(String value) {
        currentKey.setHeight(Double.parseDouble(value));
    }

}
