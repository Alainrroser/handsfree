package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.error.KeyboardLoadingException;

import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

// It just works!
//          ~ Todd Howard
public class VirtualKeyboardLayoutLoader {

    private String name;
    private ArrayList<VirtualKeyRow> rows = new ArrayList<>();
    private ArrayList<VirtualKey> row = new ArrayList<>();
    private VirtualKey currentKey;

    private StringBuilder value = new StringBuilder();
    private int index = 0;
    private boolean readingString = false;

    private int backslashCounter = 0;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

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
        currentKey = new VirtualKey();
        value = new StringBuilder();
        index = 0;
        readingString = false;

        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            boolean isValueSeparator = c == ',' && !readingString;

            if(isValueSeparator) {
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

        if(value.length() > 0) {
            finishValue();
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
