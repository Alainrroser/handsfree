package ch.bbcag.handsfree.control.speechcontrol;

import java.util.ArrayList;
import java.util.List;

public class Command {

    private String name;
    private String description;
    private SpeechListener speechListener;

    public Command(String name, String description, SpeechListener speechListener) {
        this.name = name;
        this.description = description;
        this.speechListener = speechListener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SpeechListener getSpeechListener() {
        return speechListener;
    }

    public void setSpeechListener(SpeechListener speechListener) {
        this.speechListener = speechListener;
    }

}
