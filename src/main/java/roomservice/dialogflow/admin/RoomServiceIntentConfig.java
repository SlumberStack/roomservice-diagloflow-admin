package roomservice.dialogflow.admin;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceIntentConfig {

    private List<String> trainingPhrases;
    private List<List<TrainingPhrasePrototype>> trainingPhraseObjects = new ArrayList<>();
    private String displayName;
    private List<String> messageTexts;

    public List<String> getTrainingPhrases() {
        return trainingPhrases;
    }

    public void setTrainingPhrases(List<String> trainingPhrases) {
        this.trainingPhrases = trainingPhrases;
        this.buildTrainingPhrases(trainingPhrases);
    }

    public List<List<TrainingPhrasePrototype>> getTrainingPhraseObjects() {
        return trainingPhraseObjects;
    }

    public void buildTrainingPhrases(List<String> trainingPhrases) {
        this.trainingPhraseObjects.clear();
        for (String phrase : trainingPhrases) {
            this.trainingPhraseObjects.add(TrainingPhrasePrototypeParser.parse(phrase));
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getMessageTexts() {
        return messageTexts;
    }

    public void setMessageTexts(List<String> messageTexts) {
        this.messageTexts = messageTexts;
    }
}
