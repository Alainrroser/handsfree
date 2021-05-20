package ch.bbcag.handsfree.gui.onscreenkeyboard;

public class VirtualKey {

    private int keyCode;
    private boolean hold;
    private String[] displayTexts;
    private double width;
    private double height;

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public String[] getDisplayTexts() {
        return displayTexts;
    }

    public void setDisplayTexts(String[] displayTexts) {
        this.displayTexts = displayTexts;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
