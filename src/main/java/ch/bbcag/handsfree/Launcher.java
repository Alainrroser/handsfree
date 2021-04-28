package ch.bbcag.handsfree;

import ch.bbcag.handsfree.eyetracking.EyeTracking;

public class Launcher {
    
    public static void main(String[] args) throws Exception {
//        new SpeechTest().start();
        new EyeTracking().start();
//        Application.launch(HandsFreeApplication.class);
    }
    
}
