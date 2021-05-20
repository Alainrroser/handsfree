package ch.bbcag.handsfree.error;

public class NativeException extends Exception {

    private String library;

    public NativeException(String library, String message) {
        super(message);
        this.library = library;
    }

    public String getLibrary() {
        return library;
    }

}
