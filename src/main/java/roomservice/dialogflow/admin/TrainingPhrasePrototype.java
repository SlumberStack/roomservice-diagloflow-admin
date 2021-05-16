package roomservice.dialogflow.admin;

public class TrainingPhrasePrototype {
    private String text;
    private boolean userDefined;
    private String entityType;
    private String alias;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUserDefined() {
        return userDefined;
    }

    public void setUserDefined(boolean userDefined) {
        this.userDefined = userDefined;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public static TrainingPhrasePrototype fromEntity(EntityPrototype entity) {
        TrainingPhrasePrototype prototype = new TrainingPhrasePrototype();
        prototype.setText(entity.getText());
        prototype.setAlias(entity.getText());
        prototype.setUserDefined(entity.isUserDefined());
        prototype.setEntityType(entity.getEntityType());
        return prototype;
    }
}
