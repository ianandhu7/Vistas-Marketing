/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.util.List;
import java.util.TreeMap;

import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;

/**
 * @author vk
 *
 */
public class CartSummaryDTO {
	
	private StudentOrder studentOrder;
	
	private List<StagingStudentSubscription> stagingStudentSubscriptions;
	
	private List<UserCartResponseDTO> userCarts;
	
	private TreeMap<String, String> paymentParameters;
	
	private Float cartNetPrice;
	
	private Float cartGSTPrice;
	
	private Float cartTotalPrice;
	
	private Float cartTotalDiscount;
	
	private String purchaseType;

	/**
	 * @return the studentOrder
	 */
	public StudentOrder getStudentOrder() {
		return studentOrder;
	}

	/**
	 * @param studentOrder the studentOrder to set
	 */
	public void setStudentOrder(StudentOrder studentOrder) {
		this.studentOrder = studentOrder;
	}

	/**
	 * @return the stagingStudentSubscriptions
	 */
	public List<StagingStudentSubscription> getStagingStudentSubscriptions() {
		return stagingStudentSubscriptions;
	}

	/**
	 * @param stagingStudentSubscriptions the stagingStudentSubscriptions to set
	 */
	public void setStagingStudentSubscriptions(List<StagingStudentSubscription> stagingStudentSubscriptions) {
		this.stagingStudentSubscriptions = stagingStudentSubscriptions;
	}

	/**
	 * @return the userCarts
	 */
	public List<UserCartResponseDTO> getUserCarts() {
		return userCarts;
	}

	/**
	 * @param userCarts the userCarts to set
	 */
	public void setUserCarts(List<UserCartResponseDTO> userCarts) {
		this.userCarts = userCarts;
	}

	/**
	 * @return the paymentParameters
	 */
	public TreeMap<String, String> getPaymentParameters() {
		return paymentParameters;
	}

	/**
	 * @param paymentParameters the paymentParameters to set
	 */
	public void setPaymentParameters(TreeMap<String, String> paymentParameters) {
		this.paymentParameters = paymentParameters;
	}

	/**
	 * @return the cartNetPrice
	 */
	public Float getCartNetPrice() {
		return cartNetPrice;
	}

	/**
	 * @param cartNetPrice the cartNetPrice to set
	 */
	public void setCartNetPrice(Float cartNetPrice) {
		this.cartNetPrice = cartNetPrice;
	}

	/**
	 * @return the cartGSTPrice
	 */
	public Float getCartGSTPrice() {
		return cartGSTPrice;
	}

	/**
	 * @param cartGSTPrice the cartGSTPrice to set
	 */
	public void setCartGSTPrice(Float cartGSTPrice) {
		this.cartGSTPrice = cartGSTPrice;
	}

	/**
	 * @return the cartTotalPrice
	 */
	public Float getCartTotalPrice() {
		return cartTotalPrice;
	}

	/**
	 * @param cartTotalPrice the cartTotalPrice to set
	 */
	public void setCartTotalPrice(Float cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}
	
	/**
	 * @return the cartTotalDiscount
	 */
	public Float getCartTotalDiscount() {
		return cartTotalDiscount;
	}

	/**
	 * @param cartTotalDiscount the cartTotalDiscount to set
	 */
	public void setCartTotalDiscount(Float cartTotalDiscount) {
		this.cartTotalDiscount = cartTotalDiscount;
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
	
}
