package ch.bbcag.handsfree.config;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;

import java.io.File;
import java.io.FileWriter;

public class Autorun {
    
    public static final String AUTORUN_PATH = "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
    
    private static File file;
    private static String path = System.getProperty("user.home") + AUTORUN_PATH + "\\handsfree.bat";
    
    private static boolean hasBeenCreatedBefore = false;
    
    public static void saveToAutorunFolder() {
        try {
            file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            hasBeenCreatedBefore = true;
            writeScript();
        } catch(Exception e) {
            Error.reportCritical(ErrorMessages.AUTORUN, e);
        }
    }
    
    public static void deleteFromAutorunFolder() {
        if(hasBeenCreatedBefore) {
            if(file.exists()) {
                file.delete();
            }
        }
    }
    
    private static void writeScript() throws Exception {
        String jarPath = Autorun.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        FileWriter writer = new FileWriter(file);
        writer.write("java -jar " + jarPath);
        writer.close();
    }
    
}