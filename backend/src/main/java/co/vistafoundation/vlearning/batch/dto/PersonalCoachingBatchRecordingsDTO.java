/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import co.vistafoundation.vlearning.batch.model.BatchRunRecording;

/**
 * @author Ahmed
 *
 */
public class PersonalCoachingBatchRecordingsDTO {

	private BatchRunRecording batchRunRecording;

	/**
	 * @return the batchRunRecording
	 */
	public BatchRunRecording getBatchRunRecording() {
		return batchRunRecording;
	}

	/**
	 * @param batchRunRecording the batchRunRecording to set
	 */
	public void setBatchRunRecording(BatchRunRecording batchRunRecording) {
		this.batchRunRecording = batchRunRecording;
	}

	/**
	 * @param batchRunRecording
	 */
	public PersonalCoachingBatchRecordingsDTO(BatchRunRecording batchRunRecording) {
		super();
		this.batchRunRecording = batchRunRecording;
	}

	/**
	 * 
	 */
	public PersonalCoachingBatchRecordingsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	

}
