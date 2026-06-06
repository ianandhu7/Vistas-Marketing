package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;

public class StudentSubjectDTO implements Serializable{


	private static final long serialVersionUID = -4019454613213198243L;
	private Long idClassStandard;
	private Long idSyllabus;
	private Long idState;
	
	
	
	public StudentSubjectDTO() {
		
	}



	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}



	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}



	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}



	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}



	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}



	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}
	
	
	@Override
	public String toString() {
		return "StudentSubjectDTO [idClassStandard=" + idClassStandard + ", idSyllabus=" + idSyllabus + ", idState="
				+ idState + "]";
	}
	
	
}
