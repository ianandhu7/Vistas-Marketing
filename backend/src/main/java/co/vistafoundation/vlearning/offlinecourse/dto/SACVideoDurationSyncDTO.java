/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Naveen Kumar
 *
 */
public class SACVideoDurationSyncDTO {
	
	@NotNull
	private Long idStudentAssignedCourse;
	
	@NotNull
	private String videoCoverageDuration;

	/**
	 * @return the idStudentAssignedCourse
	 */
	public Long getIdStudentAssignedCourse() {
		return idStudentAssignedCourse;
	}

	/**
	 * @param idStudentAssignedCourse the idStudentAssignedCourse to set
	 */
	public void setIdStudentAssignedCourse(Long idStudentAssignedCourse) {
		this.idStudentAssignedCourse = idStudentAssignedCourse;
	}

	/**
	 * @return the videoCoverageDuration
	 */
	public String getVideoCoverageDuration() {
		return videoCoverageDuration;
	}

	/**
	 * @param videoCoverageDuration the videoCoverageDuration to set
	 */
	public void setVideoCoverageDuration(String videoCoverageDuration) {
		this.videoCoverageDuration = videoCoverageDuration;
	}



}
