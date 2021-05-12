package ch.bbcag.handsfree.utils;

import ch.bbcag.handsfree.error.NativeException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NativeLibraryLoader {

    public static void loadLibraryFromResource(String resource) throws NativeException {
        try {
            tryLoadLibrary(resource);
        } catch(IOException e) {
            throw new NativeException(resource, e.getMessage());
        }
    }

    private static void tryLoadLibrary(String resource) throws NativeException, IOException {
        byte[] buffer = readBytesFromResource(resource);

        String filename = resource.substring(resource.lastIndexOf("/") + 1);
        File temporaryFile = TemporaryFile.create(filename);
        writeBytesToFile(temporaryFile, buffer);

        System.load(temporaryFile.getAbsolutePath());
    }

    private static byte[] readBytesFromResource(String resource) throws NativeException, IOException {
        InputStream inputStream = getInputStreamFromResourceName(resource);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();

        return bytes;
    }

    private static InputStream getInputStreamFromResourceName(String resource) throws NativeException {
        InputStream inputStream = NativeLibraryLoader.class.getResourceAsStream(resource);
        if(inputStream == null) {
            throw new NativeException(resource, "Couldn't find resource: " + resource);
        }

        return inputStream;
    }

    private static void writeBytesToFile(File file, byte[] bytes) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.close();
    }

}
