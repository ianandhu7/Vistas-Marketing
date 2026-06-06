/**
 * 
 */
package co.vistafoundation.vlearning.videocipher.dto;

/**
 * @author Naveen Kumar
 *
 */
public class VideoCipherLicenseRules {
	
	private boolean canPersist;
	
	private int rentalDuration;

	/**
	 * @return the canPersist
	 */
	public boolean isCanPersist() {
		return canPersist;
	}

	/**
	 * @param canPersist the canPersist to set
	 */
	public void setCanPersist(boolean canPersist) {
		this.canPersist = canPersist;
	}

	/**
	 * @return the rentalDuration
	 */
	public int getRentalDuration() {
		return rentalDuration;
	}

	/**
	 * @param rentalDuration the rentalDuration to set
	 */
	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	/**
	 * @param canPersist
	 * @param rentalDuration
	 */
	public VideoCipherLicenseRules(boolean canPersist, int rentalDuration) {
		super();
		this.canPersist = canPersist;
		this.rentalDuration = rentalDuration;
	}

	/**
	 * 
	 */
	public VideoCipherLicenseRules() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
