package co.vistafoundation.vlearning.subscription.model;

public class MannualSubscriptionDTO {
	private String phoneNumber;
	private Integer offerGiven;
	private Float plan;
	private String type;
	private String planType;
		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MannualSubscriptionDTO() {
		super();
	}

	public MannualSubscriptionDTO(String phoneNumber, Integer offerGiven, Float plan, String type, String planType) {
		super();
		this.phoneNumber = phoneNumber;
		this.offerGiven = offerGiven;
		this.plan = plan;
		this.type = type;
		this.planType = planType;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public MannualSubscriptionDTO(String phoneNumber, Integer offerGiven, Float plan) {
		super();
		this.phoneNumber = phoneNumber;
		this.offerGiven = offerGiven;
		this.plan = plan;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getOfferGiven() {
		return offerGiven;
	}

	public void setOfferGiven(Integer offerGiven) {
		this.offerGiven = offerGiven;
	}

	public Float getPlan() {
		return plan;
	}

	public void setPlan(Float plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "MannualSubscriptionDTO [phoneNumber=" + phoneNumber + ", offerGiven=" + offerGiven + ", plan=" + plan
				+ "]";
	}

}
