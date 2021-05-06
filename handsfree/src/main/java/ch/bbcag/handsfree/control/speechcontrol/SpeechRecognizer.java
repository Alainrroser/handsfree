package ch.bbcag.handsfree.control.speechcontrol;

import javax.sound.sampled.*;

public class SpeechRecognizer {

    // The audio format sphinx expects to do its thing
    // Signed PCM, 16000 Hz, 16 bit, mono, 2 bytes per frame, little-endian
    private static final AudioFormat SPHINX_AUDIO_FORMAT = new AudioFormat(
            160000,     // sample rate
            16,         // sample size in bits
            1,          // number of channels (1 -> mono)
            true,       // signed
            false       // little endian
    );

    public boolean isSupported() {
        Line.Info info = new DataLine.Info(TargetDataLine.class, SPHINX_AUDIO_FORMAT);
        return AudioSystem.isLineSupported(info);
    }

}
