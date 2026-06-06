/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "USER_CART")
public class UserCart extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idUSER_CART", nullable = false)
	private Long idUserCart;
	
	@Column(name = "idVLUser")
	private Long userSurId;
	
	@Column(name = "idSTUDENT")
	private Long idStudent;
	
	@Column(name = "idBATCH")
	private Long idBatch;
	
	@Column(name = "idPRODUCT")
	private Long idProduct;
	
	@Column(name = "idPRODUCT_GROUP")
	private Long idProductGroup;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory;
	
	// SUBSCRIPTION_TYPE as ANNUAL, QUARTER, MONTH
	@Column(name = "SUBSCRIPTION_TYPE")
	private String subscriptionType;
	
	@Column(name = "PURCHASE_LEVEL")
	private String purchaseLevel;
	
	//PURCHASE_TYPE as NEW or RENEWAL
	@Column(name = "PURCHASE_TYPE")
	private String purchaseType;
	
	@Column(name = "PURCHASE_AMOUNT")
	private Float purchaseAmount;
	
	@Column(name = "idSTUDENT_ORDER")
	private Long idStudentOrder;

	/**
	 * 
	 */
	public UserCart() {
		super();
	}
	
	/**
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
	 */
	public UserCart(Long userSurId, Long idStudent, Long idBatch, Long idProduct, Long idProductGroup,
			String productName, String productCategory, String subscriptionType, String purchaseLevel,
			String purchaseType, Float purchaseAmount, Long idStudentOrder) {
		super();
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCart cart = (UserCart) o;
        return idBatch==cart.idBatch;
    }
    @Override
    public int hashCode() {
        return Objects.hash(idBatch);
    }	
}
