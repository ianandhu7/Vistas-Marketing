package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.Map;

public class RequestParameter {

	private Map<String, Boolean> defaultValueMap;
    private Map<String,Map<String, Boolean>> conditionalValuesMap;
    private String valueType;
    private String description;
	/**
	 * @return the defaultValueMap
	 */
	public Map<String, Boolean> getDefaultValueMap() {
		return defaultValueMap;
	}
	/**
	 * @param defaultValueMap the defaultValueMap to set
	 */
	public void setDefaultValueMap(Map<String, Boolean> defaultValueMap) {
		this.defaultValueMap = defaultValueMap;
	}
	/**
	 * @return the conditionalValuesMap
	 */
	public Map<String, Map<String, Boolean>> getConditionalValuesMap() {
		return conditionalValuesMap;
	}
	/**
	 * @param conditionalValuesMap the conditionalValuesMap to set
	 */
	public void setConditionalValuesMap(Map<String, Map<String, Boolean>> conditionalValuesMap) {
		this.conditionalValuesMap = conditionalValuesMap;
	}
	/**
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}
	/**
	 * @param valueType the valueType to set
	 */
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
