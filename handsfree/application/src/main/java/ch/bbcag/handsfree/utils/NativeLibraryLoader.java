package ch.bbcag.handsfree.utils;

import ch.bbcag.handsfree.error.NativeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class NativeLibraryLoader {

    private static final List<String> loadedLibraries = new ArrayList<>();

    public static void loadLibraryFromResource(String resource) throws NativeException {
        if(loadedLibraries.contains(resource)) {
            return;
        }

        try {
            tryLoadLibrary(resource);
            loadedLibraries.add(resource);
        } catch(IOException e) {
            throw new NativeException(resource, e.getMessage());
        }
    }

    private static void tryLoadLibrary(String resource) throws NativeException, IOException {
        InputStream inputStream = getInputStreamFromResourceName(resource);

        String filename = resource.substring(resource.lastIndexOf("/") + 1);
        File temporaryFile = TemporaryFile.create(filename);
        Files.copy(inputStream, temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        inputStream.close();

        System.load(temporaryFile.getAbsolutePath());
    }

    private static InputStream getInputStreamFromResourceName(String resource) throws NativeException {
        InputStream inputStream = NativeLibraryLoader.class.getResourceAsStream(resource);
        if(inputStream == null) {
            throw new NativeException(resource, "Couldn't find resource: " + resource);
        }

        return inputStream;
    }

}
