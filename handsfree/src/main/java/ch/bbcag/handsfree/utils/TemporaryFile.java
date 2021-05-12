package ch.bbcag.handsfree.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemporaryFile {

    private static final String TEMP_SUBDIRECTORY = "handsfree";

    public static File create(String name) throws IOException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path filePath = Paths.get(tempDirectoryPath, TEMP_SUBDIRECTORY, name);
        File file = filePath.toFile();
        createIfNotCreated(file);
        return file;
    }

    private static void createIfNotCreated(File file) throws IOException {
        if(file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if(!file.exists()) {
            file.createNewFile();
        }
    }

}
