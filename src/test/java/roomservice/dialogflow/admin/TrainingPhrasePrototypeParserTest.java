package roomservice.dialogflow.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingPhrasePrototypeParserTest {

    @Test
    void parse() {
        List<TrainingPhrasePrototype> prototypeList = TrainingPhrasePrototypeParser.parse("Macaroni");
        assertNotNull(prototypeList);
        assertEquals(1, prototypeList.size());
        assertEquals("Macaroni", prototypeList.get(0).getText());

        prototypeList = TrainingPhrasePrototypeParser.parse("Rigatonni hummus ");
        assertNotNull(prototypeList);
        assertEquals(1, prototypeList.size());
        assertEquals("Rigatonni hummus ", prototypeList.get(0).getText());
    }

    @Test
    void parse1() {
        List<TrainingPhrasePrototype> prototypeList = TrainingPhrasePrototypeParser.parse("Paella |#{\"text\":\"Carne\",\"userDefined\":true,\"entityType\":\"$sys.date\"}");
        assertNotNull(prototypeList);
        assertEquals(2, prototypeList.size());
        assertEquals("Paella ", prototypeList.get(0).getText());
        assertEquals("Carne", prototypeList.get(1).getText());
        assertEquals("$sys.date", prototypeList.get(1).getEntityType());
        assertTrue(prototypeList.get(1).isUserDefined());
    }

    @Test
    void parse4() {
        List<TrainingPhrasePrototype> prototypeList = TrainingPhrasePrototypeParser.parse(
                "I will have the |#{\"text\":\"food-option\",\"userDefined\":true,\"entityType\":\"@sys.any\",\"alias\":\"food-option\"}");
        assertNotNull(prototypeList);
        assertEquals(2, prototypeList.size());
        assertEquals("I will have the ", prototypeList.get(0).getText());
        assertEquals("food-option", prototypeList.get(1).getText());
        assertEquals("@sys.any", prototypeList.get(1).getEntityType());
        assertTrue(prototypeList.get(1).isUserDefined());
    }

    @Test
    void parse3() {
        List<TrainingPhrasePrototype> prototypeList = TrainingPhrasePrototypeParser.parse("Chilli con carne|#{\"text\":\"Italian\",\"userDefined\":true,\"entityType\":\"$sys.date\"}|for two");
        assertNotNull(prototypeList);
        assertEquals(3, prototypeList.size());
        assertEquals("Chilli con carne", prototypeList.get(0).getText());
        assertEquals("Italian", prototypeList.get(1).getText());
        assertEquals("$sys.date", prototypeList.get(1).getEntityType());
        assertTrue(prototypeList.get(1).isUserDefined());
        assertEquals("for two", prototypeList.get(2).getText());

    }

    @Test
    void parse2() {
        List<TrainingPhrasePrototype> prototypeList = TrainingPhrasePrototypeParser.parse("#{\"text\":\"Biloba\"}");
        assertNotNull(prototypeList);
        assertEquals(1, prototypeList.size());
        assertEquals("Biloba", prototypeList.get(0).getText());
    }

    @Test
    void buildEntity() {
        Gson gson = new GsonBuilder().create();

        TrainingPhrasePrototype prototype = TrainingPhrasePrototypeParser.buildEntity("#{\"abcd\":12}", gson);
        assertNotNull(prototype);
        assertNull(prototype.getText());

        prototype = TrainingPhrasePrototypeParser.buildEntity("#{\"text\":\"T12\",\"userDefined\":true}", gson);
        assertNotNull(prototype);
        assertEquals("T12", prototype.getText());
        assertTrue(prototype.isUserDefined());

        prototype = TrainingPhrasePrototypeParser.buildEntity("#{\"text\":\"Asparagus\",\"userDefined\":false,\"entityType\":\"$sys.date\" }", gson);
        assertNotNull(prototype);
        assertEquals("Asparagus", prototype.getText());
        assertEquals("$sys.date", prototype.getEntityType());
        assertFalse(prototype.isUserDefined());

    }
}