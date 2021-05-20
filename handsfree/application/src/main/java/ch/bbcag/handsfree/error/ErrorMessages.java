package ch.bbcag.handsfree.error;

public class ErrorMessages {

    private static final String TIP_REINSTALL = "Try reinstalling the application.";

    public static final String LIBRARY = "Couldn't load dependent library.\n" + TIP_REINSTALL;
    public static final String ROBOT = "The application is not allowed to generate mouse and keyboard input. Please check that your system supports input generation.";
    public static final String KEYBOARD_LAYOUT = "Failed to load keyboard layout.\n" + TIP_REINSTALL;

    public static final String WRITE_SHORTCUT = "The shortcut couldn't be saved.";
    public static final String READ_SHORTCUT = "The shortcuts couldn't be loaded.";
    public static final String DELETE_SHORTCUT = "The shortcut couldn't be deleted.";

    public static final String AUTORUN = "Autorun couldn't be enabled.";
    public static final String NO_MICROPHONE = "No microphone that supports speech recognition could be detected!";

    public static final String SAVE_FILE = "A file couldn't be saved.";

    public static final String SAVE_CONFIGURATION = "Your configurations couldn't be saved.";
    public static final String LOAD_CONFIGURATION = "Your configuration couldn't be loaded";

}
