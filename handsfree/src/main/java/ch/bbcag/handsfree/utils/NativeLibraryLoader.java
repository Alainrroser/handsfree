package ch.bbcag.handsfree.utils;

import ch.bbcag.handsfree.error.NativeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class NativeLibraryLoader {

    public static void loadLibraryFromResource(String resource) throws NativeException {
        try {
            tryLoadLibrary(resource);
        } catch(IOException e) {
            throw new NativeException(resource, e.getMessage());
        }
    }

    private static void tryLoadLibrary(String resource) throws NativeException, IOException {
        System.out.println("loading " + resource + "...");

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
