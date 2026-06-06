/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

import co.vistafoundation.vlearning.batch.model.Batch;

/**
 * @author vk
 *
 */
public class UserCartResponseDTO {
	
	private Long idUserCart;
	
	private Long userSurId;
	
	private Long idStudent;
	
	private Long idBatch;
	
	private Long idProduct;
	
	private Long idProductGroup;
	
	private String productName;
	
	private String productCategory;
	
	private String subscriptionType;
	
	private String purchaseLevel;
	
	private String purchaseType;
	
	private Float purchaseAmount;
	
	private Long idStudentOrder;
	
	private Batch batch;
	
	private Long idSpecialOffer;
	
	private Long idProductPricing;
	
	/**
	 * 
	 */
	public UserCartResponseDTO() {
		super();
	}

	/**
	 * @param idUserCart
	 * @param userSurId
	 * @param idStudent
	 * @param idBatch
	 * @param idProduct
	 * @param idProductGroup
	 * @param productName
	 * @param productCategory
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param purchaseAmount
	 * @param idStudentOrder
	 * @param idSpecialOffer
	 */
	public UserCartResponseDTO(Long idUserCart, Long userSurId, Long idStudent, Long idBatch, Long idProduct,
			Long idProductGroup, String productName, String productCategory, String subscriptionType,
			String purchaseLevel, String purchaseType, Float purchaseAmount, Long idStudentOrder, Batch batch,
			Long idSpecialOffer, Long idProductPricing) {
		super();
		this.idUserCart = idUserCart;
		this.userSurId = userSurId;
		this.idStudent = idStudent;
		this.idBatch = idBatch;
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.productName = productName;
		this.productCategory = productCategory;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.purchaseAmount = purchaseAmount;
		this.idStudentOrder = idStudentOrder;
		this.batch = batch;
		this.idSpecialOffer = idSpecialOffer;
		this.idProductPricing = idProductPricing;
	}

	/**
	 * @return the idUserCart
	 */
	public Long getIdUserCart() {
		return idUserCart;
	}

	

	/**
	 * @param idUserCart the idUserCart to set
	 */
	public void setIdUserCart(Long idUserCart) {
		this.idUserCart = idUserCart;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the idProductGroup
	 */
	public Long getIdProductGroup() {
		return idProductGroup;
	}

	/**
	 * @param idProductGroup the idProductGroup to set
	 */
	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * @return the subscriptionType
	 */
	public String getSubscriptionType() {
		return subscriptionType;
	}

	/**
	 * @param subscriptionType the subscriptionType to set
	 */
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	/**
	 * @return the purchaseLevel
	 */
	public String getPurchaseLevel() {
		return purchaseLevel;
	}

	/**
	 * @param purchaseLevel the purchaseLevel to set
	 */
	public void setPurchaseLevel(String purchaseLevel) {
		this.purchaseLevel = purchaseLevel;
	}

	/**
	 * @return the purchaseType
	 */
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * @param purchaseType the purchaseType to set
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * @return the purchaseAmount
	 */
	public Float getPurchaseAmount() {
		return purchaseAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setPurchaseAmount(Float purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	/**
	 * @return the idStudentOrder
	 */
	public Long getIdStudentOrder() {
		return idStudentOrder;
	}

	/**
	 * @param idStudentOrder the idStudentOrder to set
	 */
	public void setIdStudentOrder(Long idStudentOrder) {
		this.idStudentOrder = idStudentOrder;
	}

	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	/**
	 * @return the idSpecialOffer
	 */
	public Long getIdSpecialOffer() {
		return idSpecialOffer;
	}

	/**
	 * @param idSpecialOffer the idSpecialOffer to set
	 */
	public void setIdSpecialOffer(Long idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
	}

	public Long getIdProductPricing() {
		return idProductPricing;
	}

	public void setIdProductPricing(Long idProductPricing) {
		this.idProductPricing = idProductPricing;
	}
	
	
	
}
