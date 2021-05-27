package ch.bbcag.handsfree.error;

import ch.bbcag.handsfree.Constants;
import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Error {

    private static final File LOG_FILE = new File(Constants.LOG_FILE);

    public static void initGlobalExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            reportCritical(ErrorMessages.UNKNOWN_ERROR, throwable);
            throwable.printStackTrace();
        });
    }

    public static void reportMinor(String message) {
        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Error", message);
        dialog.show();
    }

    public static void reportCritical(String message, Throwable throwable) {
        String text;

        if(throwable != null) {
            try {
                writeLog(throwable);
                text = message + "\n\nThe error message has been written to \"" + LOG_FILE.getAbsolutePath() + "\"";
            } catch(IOException e) {
                e.printStackTrace();
                text = message + "\n\nThe error message couldn't be written to the log file.";
            }
        } else {
            text = message;
        }

        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Error", text);
        dialog.setSize(600, 300);
        dialog.show();
        dialog.setOnHiding(event -> System.exit(0));
    }

    private static void writeLog(Throwable throwable) throws IOException {
        if(!LOG_FILE.getParentFile().exists()) {
            LOG_FILE.getParentFile().mkdirs();
        }

        if(!LOG_FILE.exists()) {
            LOG_FILE.createNewFile();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logTitle = format.format(new Date());

        PrintWriter printWriter = new PrintWriter(new FileWriter(LOG_FILE, true));
        printWriter.write(logTitle);
        printWriter.write("\n");
        throwable.printStackTrace(printWriter);
        printWriter.write("\n\n");
        printWriter.close();
    }

}
