package co.vistafoundation.vlearning.firebase.config.dto;

public class Condition {
    private String name;
    private String expression;
    private String tagColor;

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public String getTagColor() {
        return tagColor;
    }
    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}

