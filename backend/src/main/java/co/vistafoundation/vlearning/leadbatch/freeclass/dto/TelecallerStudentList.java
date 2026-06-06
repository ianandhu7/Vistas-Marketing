package co.vistafoundation.vlearning.leadbatch.freeclass.dto;


/**
 * 
 * @author Meghana
 *
 */

public class TelecallerStudentList {
	private Long idLeadBatchDetails;
	private String firstName;
	private String mobileNumber;
	
	public Long getIdLeadBatchDetails() {
		return idLeadBatchDetails;
	}
	public void setIdLeadBatchDetails(Long idLeadBatchDetails) {
		this.idLeadBatchDetails = idLeadBatchDetails;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public TelecallerStudentList(Long idLeadBatchDetails, String firstName, String mobileNumber) {
		super();
		this.idLeadBatchDetails = idLeadBatchDetails;
		this.firstName = firstName;
		this.mobileNumber = mobileNumber;
		
	}
//	public TelecallerStudentList(Long idLeadBatchDetails, String firstName, String mobileNumber, LocalTime fromTime,
//			LocalTime toTime) {
//		super();
//		this.idLeadBatchDetails = idLeadBatchDetails;
//		this.firstName = firstName;
//		this.mobileNumber = mobileNumber;
//		this.fromTime = fromTime;
//		this.toTime = toTime;
//	}
	public TelecallerStudentList() {
		// TODO Auto-generated constructor stub
	}
	
	
}
