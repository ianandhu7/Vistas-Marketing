package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.Map;

public class ParameterGroup {
    private Map<String, Parameter> parameters;
    private String description; 

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
	// Getters and Setters
    public Map<String, Parameter> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    }
}
