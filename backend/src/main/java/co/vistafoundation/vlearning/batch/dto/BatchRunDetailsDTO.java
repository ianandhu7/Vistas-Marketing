/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

/**
 * @author Ahmed
 *
 */
public class BatchRunDetailsDTO {

	private Long idBatch;
	private String batchRundate;
	private String actualStartTime;
	private String actualEndTime;

	/**
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the batchRundate
	 */
	public String getBatchRundate() {
		return batchRundate;
	}

	/**
	 * @param batchRundate the batchRundate to set
	 */
	public void setBatchRundate(String batchRundate) {
		this.batchRundate = batchRundate;
	}

	/**
	 * @return the actualStartTime
	 */
	public String getActualStartTime() {
		return actualStartTime;
	}

	/**
	 * @param actualStartTime the actualStartTime to set
	 */
	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	/**
	 * @return the actualEndTime
	 */
	public String getActualEndTime() {
		return actualEndTime;
	}

	/**
	 * @param actualEndTime the actualEndTime to set
	 */
	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	/**
	 * @param idBatch
	 * @param batchRundate
	 * @param actualStartTime
	 * @param actualEndTime
	 */
	public BatchRunDetailsDTO(Long idBatch, String batchRundate, String actualStartTime, String actualEndTime) {
		super();
		this.idBatch = idBatch;
		this.batchRundate = batchRundate;
		this.actualStartTime = actualStartTime;
		this.actualEndTime = actualEndTime;
	}

	/**
	 * 
	 */
	public BatchRunDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
