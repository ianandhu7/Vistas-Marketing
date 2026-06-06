package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.Map;

public class Parameter {
    private ParameterValue defaultValue;
    private Map<String, ParameterValue> conditionalValues;
    private String valueType;
    private String description; 

    // Getters and Setters
    public ParameterValue getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(ParameterValue defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Map<String, ParameterValue> getConditionalValues() {
        return conditionalValues;
    }
    public void setConditionalValues(Map<String, ParameterValue> conditionalValues) {
        this.conditionalValues = conditionalValues;
    }
    public String getValueType() {
        return valueType;
    }
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
    
    
}

