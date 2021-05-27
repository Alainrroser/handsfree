package ch.bbcag.handsfree.config;

import ch.bbcag.handsfree.ApplicationConstants;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ApplicationErrorMessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Properties configurations = new Properties();
    private static File file = new File(ApplicationConstants.CONFIGURATION_FILE);

    public static void writeConfiguration(boolean eyeTrackingState, boolean headTrackingState, boolean speechControlState, boolean autorunState) {
        try {
            createFileAndParentsIfNotCreated();

            configurations.put(ApplicationConstants.EYE_TRACKING_STATE, String.valueOf(eyeTrackingState));
            configurations.put(ApplicationConstants.HEAD_TRACKING_STATE, String.valueOf(headTrackingState));
            configurations.put(ApplicationConstants.SPEECH_CONTROL_STATE, String.valueOf(speechControlState));
            configurations.put(ApplicationConstants.AUTORUN_STATE, String.valueOf(autorunState));

            FileOutputStream outputStream = new FileOutputStream(file);
            configurations.store(outputStream, "");
            outputStream.close();
        } catch(Exception e) {
            Error.reportCritical(ApplicationErrorMessages.SAVE_CONFIGURATION, e);
        }
    }

    private static void createFileAndParentsIfNotCreated() {
        try {
            if(file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            if(!file.exists()) {
                file.createNewFile();
                writeConfiguration(false, false, false, false);
            }
        } catch(IOException e) {
            Error.reportCritical(ApplicationErrorMessages.LOAD_CONFIGURATION, e);
        }

    }

    public static boolean readConfiguration(String key) {
        createFileAndParentsIfNotCreated();

        try {
            FileInputStream inputStream = new FileInputStream(file);
            configurations.load(inputStream);
            String value = (String) configurations.get(key);
            return "true".equals(value);
        } catch(Exception e) {
            Error.reportCritical(ApplicationErrorMessages.LOAD_CONFIGURATION, e);
        }

        return false;
    }

}
