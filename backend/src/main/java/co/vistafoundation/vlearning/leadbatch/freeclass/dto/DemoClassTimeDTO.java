/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author vk
 *
 */
public class DemoClassTimeDTO {
	
	private String fromTime;
	
	private String toTime;

	/**
	 * @param fromTime
	 * @param toTime
	 */
	public DemoClassTimeDTO(String fromTime, String toTime) {
		super();
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

}
