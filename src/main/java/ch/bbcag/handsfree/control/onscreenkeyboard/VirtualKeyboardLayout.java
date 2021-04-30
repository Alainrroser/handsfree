package ch.bbcag.handsfree.control.onscreenkeyboard;

public class VirtualKeyboardLayout {

    private String name;
    private VirtualKeyRow[] keyRows;

    public VirtualKeyboardLayout(String name, VirtualKeyRow[] keyRows) {
        this.name = name;
        this.keyRows = keyRows;
    }

    public String getName() {
        return name;
    }

    public VirtualKeyRow[] getKeyRows() {
        return keyRows;
    }
}
