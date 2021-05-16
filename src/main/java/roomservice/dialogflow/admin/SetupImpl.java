package roomservice.dialogflow.admin;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.Agent;
import com.google.cloud.dialogflow.v2.AgentName;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.Intent.Message;
import com.google.cloud.dialogflow.v2.IntentsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Singleton
public class SetupImpl implements Setup {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetupImpl.class);

    private RoomServiceIntentListConfig roomServiceIntentListConfig;
    private RoomServiceAgentConfig roomServiceAgentConfig;

    public SetupImpl(RoomServiceIntentListConfig roomServiceIntentListConfig, RoomServiceAgentConfig roomServiceAgentConfig) {
        this.roomServiceIntentListConfig = roomServiceIntentListConfig;
        this.roomServiceAgentConfig = roomServiceAgentConfig;
    }

    @Override
    public void createIntents() throws IOException {
        LOGGER.info("Deleting intents, [project='{}'] ...", roomServiceAgentConfig.getProjectId());
        deleteIntents(roomServiceAgentConfig.getProjectId());
        LOGGER.info("Deleted intent, [project='{}']", roomServiceAgentConfig.getProjectId());

        for (RoomServiceIntentConfig roomServiceIntentConfig : roomServiceIntentListConfig.getIntents()) {

            Intent intent = createIntent(
                    roomServiceIntentConfig.getDisplayName(),
                    roomServiceAgentConfig.getProjectId(),
                    roomServiceIntentConfig.getTrainingPhraseObjects(),
                    roomServiceIntentConfig.getMessageTexts());
            LOGGER.info("Created intent, [name='{}']", intent.getName());
        }
    }

    /**
     * Create an intent of the given intent type
     *
     * @param displayName          The display name of the intent.
     * @param projectId            Project or Agent
     * @param trainingPhrasesParts Training phrases.
     * @param messageTexts         Message texts for the agent's response when the intent is detected.
     * @return The created Intent.
     */
    private Intent createIntent(
            String displayName,
            String projectId,
            List<List<TrainingPhrasePrototype>> trainingPhrasesParts,
            List<String> messageTexts)
            throws ApiException, IOException {
        // Instantiates a client
        try (IntentsClient intentsClient = IntentsClient.create()) {

            // Set the project agent name using the projectID (my-project-id)
            AgentName parent = AgentName.of(projectId);

            // Build the trainingPhrases from the trainingPhrasesParts
            List<Intent.TrainingPhrase> trainingPhrases = new ArrayList<>();
            for (List<TrainingPhrasePrototype> trainingPhrase : trainingPhrasesParts) {
                trainingPhrases.add(buildTrainingPhrase(trainingPhrase));
            }

            // Build the message texts for the agent's response
            Message message =
                    Message
                            .newBuilder()
                            .setText(
                                    Intent.Message.Text.newBuilder().addAllText(messageTexts).build())
                            .build();

            // Build the intent
            Intent.Builder intentBuilder = Intent.newBuilder();

            intentBuilder
                    .setDisplayName(displayName)
                    .addMessages(message)
                    .addAllTrainingPhrases(trainingPhrases);


            Intent intent = intentBuilder.build();

            // Performs the create intent request
            Intent response = intentsClient.createIntent(parent, intent);
            LOGGER.info("Intent created: {}", response);

            return response;
        }
    }

    @Override
    public Agent creatAgent() throws IOException {

        Agent.Builder agentBuilder = Agent.newBuilder();
        return agentBuilder
                .setDisplayName(this.roomServiceAgentConfig.getDisplayName())
                .setApiVersion(Agent.ApiVersion.valueOf(this.roomServiceAgentConfig.getApiVersion()))
                .setDescription(this.roomServiceAgentConfig.getDescription())
                .setEnableLogging(this.roomServiceAgentConfig.getEnable_logging())
                .setTier(Agent.Tier.valueOf(this.roomServiceAgentConfig.getTier()))
                .setParent(roomServiceAgentConfig.getName())
                .build();

    }

    private void deleteIntents(String projectId) throws IOException {
        try (IntentsClient intentsClient = IntentsClient.create()) {
            Iterator<Intent> intentsIter = intentsClient.listIntents(AgentName.of(projectId)).iterateAll().iterator();
            while (intentsIter.hasNext()) {
                Intent oldIntent = intentsIter.next();
                intentsClient.deleteIntent(oldIntent.getName());
            }
        }
    }

    private Intent.TrainingPhrase buildTrainingPhrase(List<TrainingPhrasePrototype> trainingPhrasePrototypeList) {
        Intent.TrainingPhrase.Builder trainingPhrase = Intent.TrainingPhrase.newBuilder();

        for (TrainingPhrasePrototype trainingPhrasePrototype : trainingPhrasePrototypeList) {
            LOGGER.info("Creating training phrase [entityType='{}', text='{}', alias='{}', userDefined='{}']",
                    trainingPhrasePrototype.getEntityType(),
                    trainingPhrasePrototype.getText(),
                    trainingPhrasePrototype.getAlias(),
                    trainingPhrasePrototype.isUserDefined()
            );
            if (trainingPhrasePrototype.getEntityType() == null) {
                trainingPhrase
                        .addParts(
                                Intent
                                        .TrainingPhrase
                                        .Part
                                        .newBuilder()
                                        .setText(trainingPhrasePrototype.getText())
                                        .build());
            } else {
                trainingPhrase
                        .addParts(
                                Intent
                                        .TrainingPhrase
                                        .Part
                                        .newBuilder()
                                        .setText(trainingPhrasePrototype.getText())
                                        .setAlias(trainingPhrasePrototype.getAlias())
                                        .setUserDefined(trainingPhrasePrototype.isUserDefined())
                                        .setEntityType(trainingPhrasePrototype.getEntityType())
                                        .build());

            }
        }
        return trainingPhrase.build();
    }
}
