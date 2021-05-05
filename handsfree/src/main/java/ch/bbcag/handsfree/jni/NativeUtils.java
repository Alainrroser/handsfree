package ch.bbcag.handsfree.jni;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NativeUtils {

    public static void loadLibraryFromResource(String resource) throws IOException {
        InputStream in = NativeUtils.class.getResourceAsStream(resource);
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        in.close();

        String resourceName = resource.substring(resource.lastIndexOf("/") + 1);
        String prefix = resourceName.split("\\.")[0];
        String suffix = "." + resourceName.split("\\.")[1];

        File temporaryFile = File.createTempFile(prefix, suffix);
        temporaryFile.deleteOnExit();
        if(!temporaryFile.exists()) {
            temporaryFile.getParentFile().mkdirs();
            temporaryFile.createNewFile();
        }
        temporaryFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(temporaryFile);
        out.write(buffer);
        out.close();

        System.load(temporaryFile.getAbsolutePath());
    }

}
