package co.vistafoundation.vlearning.user.dto;

import java.util.List;

import co.vistafoundation.vlearning.subscription.dto.AcademicSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.BatchSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.ExtraCurricularSubscriptionDTO;

public class KidsDTO {
	
	private StudentDetailsDTO student;
	
	private List<BatchSubscriptionDTO> batchSubscriptionDTOs;
	
	private List<AcademicSubscriptionDTO> academicSubscriptionDTOs;
	
	private List<ExtraCurricularSubscriptionDTO> extraCurricularSubscriptionDTOs;

	/**
	 * @return the student
	 */
	public StudentDetailsDTO getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(StudentDetailsDTO student) {
		this.student = student;
	}

	/**
	 * @return the batchSubscriptionDTOs
	 */
	public List<BatchSubscriptionDTO> getBatchSubscriptionDTOs() {
		return batchSubscriptionDTOs;
	}

	/**
	 * @param batchSubscriptionDTOs the batchSubscriptionDTOs to set
	 */
	public void setBatchSubscriptionDTOs(List<BatchSubscriptionDTO> batchSubscriptionDTOs) {
		this.batchSubscriptionDTOs = batchSubscriptionDTOs;
	}

	/**
	 * @return the academicSubscriptionDTOs
	 */
	public List<AcademicSubscriptionDTO> getAcademicSubscriptionDTOs() {
		return academicSubscriptionDTOs;
	}

	/**
	 * @param academicSubscriptionDTOs the academicSubscriptionDTOs to set
	 */
	public void setAcademicSubscriptionDTOs(List<AcademicSubscriptionDTO> academicSubscriptionDTOs) {
		this.academicSubscriptionDTOs = academicSubscriptionDTOs;
	}

	/**
	 * @return the extraCurricularSubscriptionDTOs
	 */
	public List<ExtraCurricularSubscriptionDTO> getExtraCurricularSubscriptionDTOs() {
		return extraCurricularSubscriptionDTOs;
	}

	/**
	 * @param extraCurricularSubscriptionDTOs the extraCurricularSubscriptionDTOs to set
	 */
	public void setExtraCurricularSubscriptionDTOs(List<ExtraCurricularSubscriptionDTO> extraCurricularSubscriptionDTOs) {
		this.extraCurricularSubscriptionDTOs = extraCurricularSubscriptionDTOs;
	}
	
}
