package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.user.model.State;

/**
 * @author sarfaraz
 *
 */

public class ProductDTO {

	private String productName;
	private String subscriptionType;
	private String purchaseType;
	private Instant nextPaymentDate;
	private Boolean subscriptionActive;

	private Product product;

	private Batch batch;

	private StagingStudentSubscription subscription;

	private ClassStandard classStandard;

	private Subject subject;

	private Syllabus syllabus;

	private State state;

	private BatchGroup batchGroup;

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
	 * @return the nextPaymentDate
	 */
	public Instant getNextPaymentDate() {
		return nextPaymentDate;
	}

	/**
	 * @param nextPaymentDate the nextPaymentDate to set
	 */
	public void setNextPaymentDate(Instant nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	/**
	 * @return the subscriptionActive
	 */
	public Boolean getSubscriptionActive() {
		return subscriptionActive;
	}

	/**
	 * @param subscriptionActive the subscriptionActive to set
	 */
	public void setSubscriptionActive(Boolean subscriptionActive) {
		this.subscriptionActive = subscriptionActive;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
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
	 * @return the subscription
	 */
	/**
	 * @return the subscription
	 */
	public StagingStudentSubscription getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(StagingStudentSubscription subscription) {
		this.subscription = subscription;
	}

	/**
	 * @return the classStandard
	 */
	public ClassStandard getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(ClassStandard classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the syllabus
	 */
	public Syllabus getSyllabus() {
		return syllabus;
	}

	/**
	 * @param syllabus the syllabus to set
	 */
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the batchGroup
	 */
	public BatchGroup getBatchGroup() {
		return batchGroup;
	}

	/**
	 * @param batchGroup the batchGroup to set
	 */
	public void setBatchGroup(BatchGroup batchGroup) {
		this.batchGroup = batchGroup;
	}
	

}
