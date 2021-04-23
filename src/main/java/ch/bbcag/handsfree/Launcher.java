package ch.bbcag.handsfree;

import javafx.application.Application;

import java.nio.file.Files;

public class Launcher {
    
    public static void main(String[] args) throws Exception {
//        new SpeechTest().start();
        new TobiiTest().start();
//        Application.launch(HandsFreeApplication.class);
    }
    
}
