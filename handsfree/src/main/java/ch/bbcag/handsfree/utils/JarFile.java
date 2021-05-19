package ch.bbcag.handsfree.utils;

import ch.bbcag.handsfree.config.Autorun;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class JarFile {
    public static File getJarFile() {
        try {
            URI jarURI = Autorun.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            System.out.println(jarURI);
            return new File(jarURI);
        } catch(URISyntaxException e) {
            Error.reportCritical(ErrorMessages.SAVE_FILE, e);
        }
        return new File(".");
    }
}
