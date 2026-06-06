package co.vistafoundation.vlearning.subscription.dto;

public class ProductDetailDTO {

	private Long idProduct;
	private String subscriptionPlan;
	private Float amount;

	public ProductDetailDTO() {

	}
	
	public ProductDetailDTO(Long idProduct, String subscriptionPlan, Float amount) {
		this.idProduct = idProduct;
		this.subscriptionPlan = subscriptionPlan;
		this.amount = amount;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}


	
}
