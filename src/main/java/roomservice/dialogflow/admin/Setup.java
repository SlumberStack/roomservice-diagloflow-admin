package roomservice.dialogflow.admin;

import com.google.cloud.dialogflow.v2.Agent;

import java.io.IOException;

public interface Setup {
    void createIntents() throws IOException;

    Agent creatAgent() throws IOException;
}
