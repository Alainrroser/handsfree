package ch.bbcag.handsfree.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class TemporaryFile {

    public static final String TEMP_SUBDIRECTORY = "handsfree";

    static {
        Thread shutdownThread = new Thread(() -> deleteRecursively(getTempSubdirectory()));
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    public static File create(String name) throws IOException {
        Path filePath = Path.of(getTempSubdirectory().getAbsolutePath(), name);
        File file = filePath.toFile();
        createIfNotCreated(file);
        return file;
    }

    private static File getTempSubdirectory() {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path filePath = Path.of(tempDirectoryPath, TEMP_SUBDIRECTORY);
        return filePath.toFile();
    }

    private static void createIfNotCreated(File file) throws IOException {
        if(file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if(!file.exists()) {
            file.createNewFile();
        }
    }

    private static void deleteRecursively(File file) {
        for(File child : Objects.requireNonNull(file.listFiles())) {
            if(child.isDirectory()) {
                deleteRecursively(child);
            } else {
                child.delete();
            }
        }

        file.delete();
    }

}
