package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.error.SpeechRecognizerException;
import ch.bbcag.handsfree.utils.TemporaryFile;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
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

    private List<Command> commands = new ArrayList<>();

    public void start() throws SpeechRecognizerException {
        if(isSupported() && !running) {
            running = true;

            if(!created) {
                createRecognizer();

                Thread thread = new Thread(this::doListening);
                thread.setDaemon(true);
                thread.start();

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
        listeners.put(command.toLowerCase(), listener);
    }

    public void removeListener(String command) {
        listeners.remove(command);
    }

    private void createRecognizer() throws SpeechRecognizerException {
        try {
            grammarFile = TemporaryFile.create(GRAMMAR_NAME + "." + GRAMMAR_FILE_EXTENSION);
            writeGrammarFile();

            Configuration configuration = new Configuration();
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setGrammarPath(grammarFile.getParentFile().toURI().toURL().toExternalForm());
            configuration.setGrammarName(GRAMMAR_NAME);
            configuration.setUseGrammar(true);

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
        // Yes, the recognizer backend is always running, even when the recognizer is stopped.
        // This is because the method getResult().
        // getResult() is a blocking method that waits for the user to say something.
        // We can't stop the recognizer while getResult() is blocking, it will just throw an exception.
        // This is why we have decided to just let it running.

        recognizer.startRecognition(true);
        disableLogging();

        while(true) {
            initListeningLoop();
        }
    }

    private void initListeningLoop() {
        SpeechResult result = recognizer.getResult();

        if(result != null && running) {
            processResult(result);
        }
    }

    private void processResult(SpeechResult result) {
        String text = result.getHypothesis();
        SpeechListener listener = listeners.get(text);

        if(listener != null) {
            listener.run();
        }
    }

    public boolean isSupported() {
        Line.Info info = new DataLine.Info(TargetDataLine.class, SPHINX_AUDIO_FORMAT);
        return AudioSystem.isLineSupported(info);
    }

    public Set<String> getCommandsAndShortcuts() {
        return listeners.keySet();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }
}
