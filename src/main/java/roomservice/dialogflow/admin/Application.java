package roomservice.dialogflow.admin;

import io.micronaut.configuration.picocli.PicocliRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.IOException;

@Command(name = "roomservice-dialogflow-admin", description = "...",
        mixinStandardHelpOptions = true)
public class Application implements Runnable {
    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Inject
    private Setup setup;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        PicocliRunner.run(Application.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            try {
                LOGGER.info("Creating agent");
                setup.creatAgent();
                LOGGER.info("Creating intents");
                setup.createIntents();
            } catch (IOException e) {
                LOGGER.error("Cannot create intents",e);
            }
        }
    }

}
