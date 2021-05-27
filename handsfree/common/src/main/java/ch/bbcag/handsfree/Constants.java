package ch.bbcag.handsfree;

public class Constants {
    private static final String PATH = System.getProperty("user.home") + "/AppData/Local/HandsFree/";
    public static final String LOG_FILE = PATH + "error.log";
    public static final String CONFIGURATION_FILE = PATH + "config.properties";
    public static final String SHORTCUT_FOLDER = PATH + "shortcuts/";
    public static final String SHORTCUT_FILE_EXTENSION = ".txt";
}
