package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.Map;

public class ConditionalValues {
    private Map<String, ParameterValue> conditionalValues;

    // Getters and Setters
    public Map<String, ParameterValue> getConditionalValues() {
        return conditionalValues;
    }
    public void setConditionalValues(Map<String, ParameterValue> conditionalValues) {
        this.conditionalValues = conditionalValues;
    }
}

