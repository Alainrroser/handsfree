package ch.bbcag.handsfree.error;

import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Error {

    private static final File LOG_FILE = new File("error.log");

    public static void reportAndExit(String title, String message, Exception exception) {
        String text;

        try {
            writeLog(exception);
            text = message + "\n\nThe error message has been written to \"" + LOG_FILE.getAbsolutePath() + "\"";
        } catch(IOException e) {
            e.printStackTrace();
            text = message + "\n\nThe error message couldn't be written to a file.";
        }

        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog(title, text);
        dialog.setSize(600, 300);
        dialog.show();
        dialog.setOnHiding(event -> System.exit(0));
    }

    private static void writeLog(Exception exception) throws IOException {
        if(!LOG_FILE.exists()) {
            LOG_FILE.createNewFile();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logTitle = format.format(new Date());

        PrintWriter printWriter = new PrintWriter(new FileWriter(LOG_FILE, true));
        printWriter.write(logTitle);
        printWriter.write("\n");
        exception.printStackTrace(printWriter);
        printWriter.write("\n\n");
        printWriter.close();
    }

}
