package ch.bbcag.handsfree.gui.button;

public class HandsFreeToggleButton extends HandsFreeDefaultButton {

    private boolean enabled = false;
    private Runnable onEnabled = null;
    private Runnable onDisabled = null;

    private String text;

    public HandsFreeToggleButton(String text) {
        super(text);
        this.text = text;
        updateText();

        setOnAction(event -> setEnabled(!isEnabled()));
    }

    private void updateText() {
        String onOffText = enabled ? "ON" : "OFF";
        setText(text + ": " + onOffText);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateText();

        if(enabled && onEnabled != null) {
            onEnabled.run();
        }

        if(!enabled && onDisabled != null) {
            onDisabled.run();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setOnEnabled(Runnable onEnabled) {
        this.onEnabled = onEnabled;
    }

    public void setOnDisabled(Runnable onDisabled) {
        this.onDisabled = onDisabled;
    }

}
