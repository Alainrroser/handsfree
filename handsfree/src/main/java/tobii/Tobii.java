package tobii;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

public class Tobii {

    private static boolean verbose = true;
    private static boolean loaded = false;
    private static boolean errorReported = false;

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static float[] getGazePosition() {
        return getValueFromNativeFunction(Tobii::jniGetGazePosition, new float[]{ 0.5f, 0.5f });
    }

    public static float getGazeX() {
        return getGazePosition()[0];
    }

    public static float getGazeY() {
        return getGazePosition()[1];
    }

    public static float[] getHeadRotation() {
        return getValueFromNativeFunction(Tobii::jniGetHeadRotation, new float[]{ 0.0f, 0.0f, 0.0f });
    }

    public static boolean isLeftEyePresent() {
        return getValueFromNativeFunction(Tobii::jniIsLeftEyePresent, false);
    }

    public static boolean isRightEyePresent() {
        return getValueFromNativeFunction(Tobii::jniIsRightEyePresent, false);
    }

    private static <T> T getValueFromNativeFunction(Callable<T> nativeFunction, T defaultValue) {
        try {
            loadIfNotLoaded();
            return nativeFunction.call();
        } catch(Throwable e) {
            reportErrorIfNotReported(e);
            return defaultValue;
        }
    }

    private static void loadIfNotLoaded() throws Exception {
        if(loaded) return;
        loaded = true;
        loadNeededLibraries();
        int code = jniInit();
        printIfVerbose("Init code error " + code);
    }

    private static void loadNeededLibraries() throws Exception {
        String dataDirectoryPath = getDataDirectoryPath();
        printIfVerbose("Loading needed libraries using directory " + dataDirectoryPath);
        loadTobiiLibraries(dataDirectoryPath);
    }

    private static String getDataDirectoryPath() {
        String appDataDirectoryPath = "";

        if(isWindows()) {
            appDataDirectoryPath = System.getenv("LocalAppData");
        } else if(isUnix()) {
            appDataDirectoryPath = System.getProperty("user.home");
        } else if(isMac()) {
            //TODO test following commented lines on Mac
            /*appDataDirectoryPath = System.getProperty("user.home");
            appDataDirectoryPath += "/Library/Application Support";*/
        }

        appDataDirectoryPath += "/.tobiiStreamEngineForJava";
        return appDataDirectoryPath;
    }

    private static void loadTobiiLibraries(String dataDirectoryPath) throws Exception {
        if(isWindows()) {
            copyResourceIntoDir("/lib/tobii/x64/tobii_stream_engine.dll", dataDirectoryPath);
            copyResourceIntoDir("/lib/tobii/x64/tobii_jni_stream_engine.dll", dataDirectoryPath);

            loadLibrary(dataDirectoryPath, "/lib/tobii/x64/tobii_stream_engine.dll");
            loadLibrary(dataDirectoryPath, "/lib/tobii/x64/tobii_jni_stream_engine.dll");
        } else if(isUnix()) {
            copyResourceIntoDir("/lib/tobii/x64/libtobii_stream_engine.so", dataDirectoryPath);
            copyResourceIntoDir("/lib/tobii/x64/libtobii_jni_stream_engine.so", dataDirectoryPath);

            loadLibrary(dataDirectoryPath, "/lib/tobii/x64/libtobii_stream_engine.so");
            loadLibrary(dataDirectoryPath, "/lib/tobii/x64/libtobii_jni_stream_engine.so");
        } else if(isMac()) {
            //TODO Compile and add MacOS libraries here
        }
    }

    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    private static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    private static boolean isMac() {
        return (OS.contains("mac"));
    }

    private static void copyResourceIntoDir(String resourceFilePath, String dirPath) throws Exception {
        printIfVerbose("Copying " + resourceFilePath + " into " + dirPath);
        InputStream in = Tobii.class.getResourceAsStream(resourceFilePath);
        File tmpFile = new File(dirPath, resourceFilePath);
        tmpFile.getParentFile().mkdirs();
        Files.copy(in, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }

    private static void loadLibrary(String dirPath, String filePath) {
        File tmpFile = new File(dirPath, filePath);
        printIfVerbose("Loading library " + tmpFile.getAbsolutePath());
        System.load(tmpFile.getAbsolutePath());
    }

    private static void printIfVerbose(String what) {
        if(verbose) {
            System.out.println("Tobii: " + what);
        }
    }

    private static void reportErrorIfNotReported(Throwable e) {
        if(!errorReported) {
            e.printStackTrace();
            errorReported = true;
        }
    }

    private static native int jniInit();

    private static native float[] jniGetGazePosition();
    private static native float[] jniGetHeadRotation();

    private static native boolean jniIsLeftEyePresent();
    private static native boolean jniIsRightEyePresent();

}
