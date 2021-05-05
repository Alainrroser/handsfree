package ch.bbcag.handsfree.control.shortcuts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShortcutWriter {

    private Shortcut shortcut;

    public void write(Shortcut shortcut, File directory) throws IOException {
        this.shortcut = shortcut;
        File file = getFile(directory);
        writeFile(file);
    }

    private File getFile(File directory) throws IOException {
        String path = directory.getAbsolutePath() + "/" + getFilename();
        File file = new File(path);
        createFileAndParentsIfNotCreated(file);
        return file;
    }

    private String getFilename() {
        return shortcut.getName() + ".txt";
    }

    private void createFileAndParentsIfNotCreated(File file) throws IOException {
        file.getParentFile().mkdirs();
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    private void writeFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writeClicks(writer);
        writer.close();
    }

    private void writeClicks(FileWriter writer) throws IOException {
        for(Click click : shortcut.getClicks()) {
            writeClick(click, writer);
        }
    }

    private void writeClick(Click click, FileWriter writer) throws IOException {
        writer.write(click.getPosition().x);
        writer.write(";");
        writer.write(click.getPosition().y);
        writer.write(";");
        writer.write(click.getButton());
        writer.write("\n");
    }

}
