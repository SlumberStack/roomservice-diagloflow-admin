package roomservice.dialogflow.admin;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TrainingPhrasePrototypeParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingPhrasePrototypeParser.class);

    public static List<TrainingPhrasePrototype> parse(String prototypeText) {

        LOGGER.info("Parsing training phrase parts from [text='{}']", prototypeText);

        List<TrainingPhrasePrototype> trainingPhrasePrototypeList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();

        Scanner scanner = new Scanner(prototypeText);
        scanner.useDelimiter("\\|");
        Pattern entityPattern = Pattern.compile("\\#\\{[\\s\\w:\\@,.\\$=\"'-]+\\}+");
        Pattern textPattern = Pattern.compile("[\\w\\s$£,.\"'()?-]+");
        boolean done = false;

        do {
            if (scanner.hasNext(textPattern)) {
                TrainingPhrasePrototype prototype = new TrainingPhrasePrototype();

                String token = scanner.next(textPattern);
                LOGGER.info("Parse token [text='{}']",token);
                prototype.setText(token);
                trainingPhrasePrototypeList.add(prototype);
            } else if (scanner.hasNext(entityPattern)) {
                String token = scanner.next(entityPattern);
                LOGGER.info("Parse token [text='{}']",token);
                TrainingPhrasePrototype prototype = buildEntity(token, gson);
                trainingPhrasePrototypeList.add(prototype);
            } else {
                 done = true;
            }
        } while (!done);

        return trainingPhrasePrototypeList;
    }

    public static TrainingPhrasePrototype buildEntity(String entityText, Gson gson) {
        LOGGER.info("Building entity from [text='{}']",entityText);
        EntityPrototype entityPrototype = gson.fromJson(entityText.substring(1), EntityPrototype.class);
        return TrainingPhrasePrototype.fromEntity(entityPrototype);
    }
}
