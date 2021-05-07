package ch.bbcag.handsfree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    private static final String TEMP_SUBDIRECTORY = "handsfree";

    public static File createTempFile(String name) throws IOException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path grammarFilePath = Paths.get(tempDirectoryPath, TEMP_SUBDIRECTORY, name);
        File file = grammarFilePath.toFile();

        if(file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if(!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

}
