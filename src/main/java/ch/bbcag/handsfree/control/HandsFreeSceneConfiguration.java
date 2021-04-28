package ch.bbcag.handsfree.control;

public class HandsFreeSceneConfiguration {

    private String title = "Title";
    private boolean hasMinimizeButton = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasMinimizeButton() {
        return hasMinimizeButton;
    }

    public void setHasMinimizeButton(boolean hasMinimizeButton) {
        this.hasMinimizeButton = hasMinimizeButton;
    }
}
