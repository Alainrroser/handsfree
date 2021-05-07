package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.Utils;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.SpeechRecognizerException;
import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private LiveSpeechRecognizer recognizer;
    private Map<String, SpeechListener> listeners = new HashMap<>();
    private File grammarFile;

    private boolean running = false;
    private boolean created = false;

    private static final String GRAMMAR_NAME = "commands";
    private static final String GRAMMAR_FILE_EXTENSION = "gram";

    public void start() throws SpeechRecognizerException {
        if(isSupported() && !running) {
            running = true;

            if(!created) {
                createRecognizer();
                new Thread(this::doListening).start();
                created = true;
            }
        }
    }

    public void stop() {
        if(running) {
            running = false;
        }
    }

    public void addListener(String command, SpeechListener listener) {
        listeners.put(command, listener);
    }

    public void removeListener(String command) {
        listeners.remove(command);
    }

    private void createRecognizer() throws SpeechRecognizerException {
        try {
            grammarFile = Utils.createTempFile(GRAMMAR_NAME + "." + GRAMMAR_FILE_EXTENSION);
            writeGrammarFile();

            Configuration configuration = new Configuration();
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setGrammarPath(grammarFile.getParentFile().toURI().toURL().toExternalForm());
            configuration.setGrammarName(GRAMMAR_NAME);
            configuration.setUseGrammar(true);

            disableLogging();
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch(IOException e) {
            throw new SpeechRecognizerException(e.getMessage());
        }
    }

    private void writeGrammarFile() throws IOException {
        List<String> commands = new ArrayList<>(listeners.keySet());

        GrammarFileWriter writer = new GrammarFileWriter();
        writer.write(commands, GRAMMAR_NAME, grammarFile);
    }

    private void disableLogging() {
        Logger rootLogger = Logger.getLogger("default.config");
        rootLogger.setLevel(Level.OFF);
        String configurationFile = System.getProperty("java.util.logging.config.file");
        if(configurationFile == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }
    }

    private void doListening() {
        recognizer.startRecognition(true);

        while(true) {
            SpeechResult result = recognizer.getResult();

            if(result != null && running) {
                String text = result.getHypothesis();
                SpeechListener listener = listeners.get(text);

                if(listener != null) {
                    listener.run();
                }
            }
        }

//        recognizer.stopRecognition();
    }

    public boolean isSupported() {
        Line.Info info = new DataLine.Info(TargetDataLine.class, SPHINX_AUDIO_FORMAT);
        return AudioSystem.isLineSupported(info);
    }

}
