/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * @author NAVEEN
 *
 */
public class UserMetaClaim {

	Long idClassStandard ;
	
	Long idSyllabus;
	
	Long idState;
	
	Boolean isSubscribed;

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

	/**
	 * @return the isSubscribed
	 */
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}

	/**
	 * @param isSubscribed the isSubscribed to set
	 */
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	/**
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @param isSubscribed
	 */
	public UserMetaClaim(Long idClassStandard, Long idSyllabus, Long idState, Boolean isSubscribed) {
		super();
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.isSubscribed = isSubscribed;
	}

	/**
	 * 
	 */
	public UserMetaClaim() {
		super();
		// TODO Auto-generated constructor stub
	}


	
}
