package ch.bbcag.installer.error;

import ch.bbcag.handsfree.error.ErrorMessages;

public class InstallerErrorMessages extends ErrorMessages {

    public static final String APPLICATION_START = "The application couldn't be started";

    public static final String DESKTOP_SHORTCUT = "The desktop shortcut couldn't be created";
    public static final String START_MENU_SHORTCUT = "The start menu shortcut couldn't be created";

    public static final String MISSING_PRIVILEGES =
            "The installer does not have the required privileges for installing " +
            "in this directory. Please select another installation path.";

}
