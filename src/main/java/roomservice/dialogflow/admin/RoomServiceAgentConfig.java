package roomservice.dialogflow.admin;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(RoomServiceAgentConfig.PREFIX)
public class RoomServiceAgentConfig {
    public static final String PREFIX = "roomservice.admin.agent";

    private String displayName;
    private String name;
    private String defaultLanguageCode;
    private String description;
    private Boolean enable_logging;
    private String matchMode;
    private String apiVersion;
    private String tier;
    private String projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDefaultLanguageCode() {
        return defaultLanguageCode;
    }

    public void setDefaultLanguageCode(String defaultLanguageCode) {
        this.defaultLanguageCode = defaultLanguageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnable_logging() {
        return enable_logging;
    }

    public void setEnable_logging(Boolean enable_logging) {
        this.enable_logging = enable_logging;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
