package ch.bbcag.handsfree.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TemporaryFileTest {

    @ParameterizedTest
    @ValueSource(strings = { "apple.txt", "subdirectory/banana.txt", "sub/sub/directory/pear.txt" })
    public void when_create_temp_file_file_is_in_temp_folder(String name) throws IOException {
        TemporaryFile.create(name);

        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path path = Path.of(tempDirectoryPath, TemporaryFile.TEMP_SUBDIRECTORY, name);
        File file = path.toFile();

        assertTrue(file.exists());
    }

}