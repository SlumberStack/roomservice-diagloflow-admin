package roomservice.dialogflow.admin;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(RoomServiceIntentListConfig.PREFIX)
public class RoomServiceIntentListConfig {
    public static final String PREFIX = "roomservice.admin";

    private List<RoomServiceIntentConfig> intents;

    public List<RoomServiceIntentConfig> getIntents() {
        return intents;
    }

    public void setIntents(List<RoomServiceIntentConfig> intents) {
        this.intents = intents;
    }
}
