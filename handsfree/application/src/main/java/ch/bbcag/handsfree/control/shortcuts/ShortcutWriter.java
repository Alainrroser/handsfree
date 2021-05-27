package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.ApplicationConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ShortcutWriter {

    private Shortcut shortcut;

    public void write(Shortcut shortcut, File directory) throws IOException {
        this.shortcut = shortcut;
        File file = getFile(directory);
        writeFile(file);
    }

    private File getFile(File directory) throws IOException {
        String path = directory.getAbsolutePath() + "\\" + getFilename();
        File file = new File(path);
        createFileAndParentsIfNotCreated(file);
        return file;
    }

    private String getFilename() {
        return UUID.randomUUID() + ApplicationConstants.SHORTCUT_FILE_EXTENSION;
    }

    private void createFileAndParentsIfNotCreated(File file) throws IOException {
        if(file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if(!file.exists()) {
            file.createNewFile();
        }
    }

    private void writeFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
        writer.write(shortcut.getName());
        writer.write("\n");
        writer.write(Boolean.toString(shortcut.isKeyboardVisible()));
        writer.write("\n");
        writer.write(Double.toString(shortcut.getKeyboardX()));
        writer.write("\n");
        writer.write(Double.toString(shortcut.getKeyboardY()));
        writer.write("\n");
        writeClicks(writer);
        writer.close();
    }

    private void writeClicks(FileWriter writer) throws IOException {
        for(Click click : shortcut.getClicks()) {
            writeClick(click, writer);
        }
    }

    private void writeClick(Click click, FileWriter writer) throws IOException {
        writer.write(Integer.toString(click.getPosition().x));
        writer.write(";");
        writer.write(Integer.toString(click.getPosition().y));
        writer.write(";");
        writer.write(Integer.toString(click.getButton()));
        writer.write(";");
        writer.write(Integer.toString(click.getTime()));
        writer.write("\n");
    }

}
