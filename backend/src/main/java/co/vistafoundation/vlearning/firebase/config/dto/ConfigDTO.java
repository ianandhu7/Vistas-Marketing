package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.List;
import java.util.Map;

public class ConfigDTO {
	
	private String etag;
    private List<Condition> conditions;
    private Map<String, Parameter> parameters;
    private Version version;
    private Map<String, ParameterGroup> parameterGroups;

    
    /**
	 * @return the etag
	 */
	public String getEtag() {
		return etag;
	}
	/**
	 * @param etag the etag to set
	 */
	public void setEtag(String etag) {
		this.etag = etag;
	}
	// Getters and Setters
    public List<Condition> getConditions() {
        return conditions;
    }
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    public Map<String, Parameter> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, Parameter> parameters) {
        this.parameters = parameters;
    }
    public Version getVersion() {
        return version;
    }
    public void setVersion(Version version) {
        this.version = version;
    }
    public Map<String, ParameterGroup> getParameterGroups() {
        return parameterGroups;
    }
    public void setParameterGroups(Map<String, ParameterGroup> parameterGroups) {
        this.parameterGroups = parameterGroups;
    }
}
