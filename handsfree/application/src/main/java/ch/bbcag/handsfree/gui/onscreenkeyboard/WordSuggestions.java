package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ApplicationErrorMessages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordSuggestions {

    private String currentlyTypedWord = "";
    private List<String> allWords = new ArrayList<>();

    private ObservableList<String> currentSuggestions = FXCollections.observableArrayList();

    private static final int MAX_SUGGESTED_WORDS = 5;

    public WordSuggestions() {
        try {
            loadAllWordsFromFile();
        } catch(IOException e) {
            Error.reportCritical(ApplicationErrorMessages.WORD_LIST, e);
        }
    }

    private void loadAllWordsFromFile() throws IOException {
        InputStream in = HandsFreeOnScreenKeyboard.class.getResourceAsStream("/words.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while((line = reader.readLine()) != null) {
            allWords.add(line);
        }

        reader.close();
    }

    public ObservableList<String> getCurrentSuggestions() {
        return currentSuggestions;
    }

    public int getCurrentlyTypedCharacterCount() {
        return currentlyTypedWord.length();
    }

    public void writeCharacter(char c) {
        currentlyTypedWord += c;
        updateSuggestions();
    }

    private void updateSuggestions() {
        currentSuggestions.clear();

        for(String word : allWords) {
            tryAddToSuggestions(word);
            if(currentSuggestions.size() == MAX_SUGGESTED_WORDS) {
                break;
            }
        }
    }

    private void tryAddToSuggestions(String word) {
        if(canWordBeSuggested(word)) {
            currentSuggestions.add(word);
        }
    }

    private boolean canWordBeSuggested(String word) {
        return word.toLowerCase().startsWith(currentlyTypedWord.toLowerCase());
    }

    public void resetSuggestions() {
        currentlyTypedWord = "";
        currentSuggestions.clear();
    }

}
