package co.vistafoundation.vlearning.auth.dto;

public class SubscriptionClickDTO {

    private String name;

    private String mobileNumber;
 
    private Long vlUserId;

    private String source;
    
    private String status;

	public SubscriptionClickDTO() {
	
	}



	public SubscriptionClickDTO(String name, String mobileNumber, Long vlUserId, String source, String status) {
		super();
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.vlUserId = vlUserId;
		this.source = source;
		this.status = status;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getVlUserId() {
		return vlUserId;
	}

	public void setVlUserId(Long vlUserId) {
		this.vlUserId = vlUserId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
    
    
}
