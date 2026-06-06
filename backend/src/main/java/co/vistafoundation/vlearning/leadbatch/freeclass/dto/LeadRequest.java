/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class LeadRequest {

	private String leadName;
	private String leadEmail;
	private String leadPhone;

	public LeadRequest(String leadName, String leadEmail, String leadPhone) {
		super();
		this.leadName = leadName;
		this.leadEmail = leadEmail;
		this.leadPhone = leadPhone;
	}

	public LeadRequest() {

	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getLeadEmail() {
		return leadEmail;
	}

	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}

	public String getLeadPhone() {
		return leadPhone;
	}

	public void setLeadPhone(String leadPhone) {
		this.leadPhone = leadPhone;
	}

}
