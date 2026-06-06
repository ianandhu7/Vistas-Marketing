/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * @author vk
 *
 */
public class ApiResponse {
	
    private Boolean success;
    
    private String message;
    
    private Object data;
    
	/**
	 * @param success
	 * @param message
	 * @param data
	 */
	public ApiResponse(Boolean success, String message, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
