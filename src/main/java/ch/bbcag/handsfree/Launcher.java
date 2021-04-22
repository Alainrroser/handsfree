package ch.bbcag.handsfree;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new SpeechTest().start();
        new TobiiTest().start();
        //Application.launch(HandsFreeApplication.class);
    }

}
