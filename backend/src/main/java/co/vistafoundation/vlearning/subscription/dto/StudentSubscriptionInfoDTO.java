/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author NAVEEN
 *
 */
public class StudentSubscriptionInfoDTO {

	private Long idStudentOrder;

	private Instant purchaseDate;

	private Instant nextPaymentDate;

	private Instant lastPaymentDate;

	private Instant subscriptionEndDate;

	private Boolean activeFlag;

	private String purchaseAmount;

	private String subscriptionType;

	private String purchaseLevel;

	private String purchaseType;

	private Long userSurId;

	private Boolean specialOfferFlag;

	private Long idSpecialOffer;

	private Float monthlySubcrAmt;

	private Float annualSubscrAmt;

	private Float qtrSubscrAmt;

	private String ageGroup;

	private String productName;

	private String productCd;

	private int batchSize;

	private String extraCurrCategory;

	private String classStandardName;

	private String syllabusName;

	private String stateName;

	private String subjectName;

	private String batchName;

	private LocalDate batchEndDate;

	private LocalDate batchDeactivateDate;

	private Boolean batchSpecialOfferFlag;

	private String batchGroupName;
	
	
	private String bankName;

	
	private String bankTransactionId;

	
	private String orderId;

	
	private String paymentMode;

	
	private String paymentStatus;

	
	private Float transactionAmount;


	private Instant transactionDate;
	
	private Long idStagingStudentSubscription;
	
	private String couponCode;
	
	private Float actualAmount;
	

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
	 * @return the purchaseDate
	 */
	public Instant getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Instant purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	 * @return the lastPaymentDate
	 */
	public Instant getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Instant lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public Instant getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Instant subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the purchaseAmount
	 */
	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
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
	 * @return the specialOfferFlag
	 */
	public Boolean getSpecialOfferFlag() {
		return specialOfferFlag;
	}

	/**
	 * @param specialOfferFlag the specialOfferFlag to set
	 */
	public void setSpecialOfferFlag(Boolean specialOfferFlag) {
		this.specialOfferFlag = specialOfferFlag;
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

	/**
	 * @return the monthlySubcrAmt
	 */
	public Float getMonthlySubcrAmt() {
		return monthlySubcrAmt;
	}

	/**
	 * @param monthlySubcrAmt the monthlySubcrAmt to set
	 */
	public void setMonthlySubcrAmt(Float monthlySubcrAmt) {
		this.monthlySubcrAmt = monthlySubcrAmt;
	}

	/**
	 * @return the annualSubscrAmt
	 */
	public Float getAnnualSubscrAmt() {
		return annualSubscrAmt;
	}

	/**
	 * @param annualSubscrAmt the annualSubscrAmt to set
	 */
	public void setAnnualSubscrAmt(Float annualSubscrAmt) {
		this.annualSubscrAmt = annualSubscrAmt;
	}

	/**
	 * @return the qtrSubscrAmt
	 */
	public Float getQtrSubscrAmt() {
		return qtrSubscrAmt;
	}

	/**
	 * @param qtrSubscrAmt the qtrSubscrAmt to set
	 */
	public void setQtrSubscrAmt(Float qtrSubscrAmt) {
		this.qtrSubscrAmt = qtrSubscrAmt;
	}

	/**
	 * @return the ageGroup
	 */
	public String getAgeGroup() {
		return ageGroup;
	}

	/**
	 * @param ageGroup the ageGroup to set
	 */
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
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
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}

	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the extraCurrCategory
	 */
	public String getExtraCurrCategory() {
		return extraCurrCategory;
	}

	/**
	 * @param extraCurrCategory the extraCurrCategory to set
	 */
	public void setExtraCurrCategory(String extraCurrCategory) {
		this.extraCurrCategory = extraCurrCategory;
	}

	/**
	 * @return the classStandardName
	 */
	public String getClassStandardName() {
		return classStandardName;
	}

	/**
	 * @param classStandardName the classStandardName to set
	 */
	public void setClassStandardName(String classStandardName) {
		this.classStandardName = classStandardName;
	}

	/**
	 * @return the syllabusName
	 */
	public String getSyllabusName() {
		return syllabusName;
	}

	/**
	 * @param syllabusName the syllabusName to set
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * @return the batchEndDate
	 */
	public LocalDate getBatchEndDate() {
		return batchEndDate;
	}

	/**
	 * @param batchEndDate the batchEndDate to set
	 */
	public void setBatchEndDate(LocalDate batchEndDate) {
		this.batchEndDate = batchEndDate;
	}

	/**
	 * @return the batchDeactivateDate
	 */
	public LocalDate getBatchDeactivateDate() {
		return batchDeactivateDate;
	}

	/**
	 * @param batchDeactivateDate the batchDeactivateDate to set
	 */
	public void setBatchDeactivateDate(LocalDate batchDeactivateDate) {
		this.batchDeactivateDate = batchDeactivateDate;
	}

	/**
	 * @return the batchSpecialOfferFlag
	 */
	public Boolean getBatchSpecialOfferFlag() {
		return batchSpecialOfferFlag;
	}

	/**
	 * @param batchSpecialOfferFlag the batchSpecialOfferFlag to set
	 */
	public void setBatchSpecialOfferFlag(Boolean batchSpecialOfferFlag) {
		this.batchSpecialOfferFlag = batchSpecialOfferFlag;
	}

	/**
	 * @return the batchGroupName
	 */
	public String getBatchGroupName() {
		return batchGroupName;
	}

	/**
	 * @param batchGroupName the batchGroupName to set
	 */
	public void setBatchGroupName(String batchGroupName) {
		this.batchGroupName = batchGroupName;
	}

	/**
	 * @param idStudentOrder
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param userSurId
	 * @param specialOfferFlag
	 * @param idSpecialOffer
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param batchSize
	 * @param extraCurrCategory
	 * @param classStandardName
	 * @param syllabusName
	 * @param stateName
	 * @param subjectName
	 * @param batchName
	 * @param batchEndDate
	 * @param batchDeactivateDate
	 * @param batchSpecialOfferFlag
	 * @param batchGroupName
	 */
	public StudentSubscriptionInfoDTO(Long idStudentOrder, Instant purchaseDate, Instant nextPaymentDate,
			Instant lastPaymentDate, Instant subscriptionEndDate, Boolean activeFlag, String purchaseAmount,
			String subscriptionType, String purchaseLevel, String purchaseType, Long userSurId,
			Boolean specialOfferFlag, Long idSpecialOffer, Float monthlySubcrAmt, Float annualSubscrAmt,
			Float qtrSubscrAmt, String ageGroup, String productName, String productCd, int batchSize,
			String extraCurrCategory, String classStandardName, String syllabusName, String stateName,
			String subjectName, String batchName, LocalDate batchEndDate, LocalDate batchDeactivateDate,
			Boolean batchSpecialOfferFlag, String batchGroupName) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.userSurId = userSurId;
		this.specialOfferFlag = specialOfferFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.batchSize = batchSize;
		this.extraCurrCategory = extraCurrCategory;
		this.classStandardName = classStandardName;
		this.syllabusName = syllabusName;
		this.stateName = stateName;
		this.subjectName = subjectName;
		this.batchName = batchName;
		this.batchEndDate = batchEndDate;
		this.batchDeactivateDate = batchDeactivateDate;
		this.batchSpecialOfferFlag = batchSpecialOfferFlag;
		this.batchGroupName = batchGroupName;
	}

	
	
	/**
	 * @param idStudentOrder
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param userSurId
	 * @param specialOfferFlag
	 * @param idSpecialOffer
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param batchSize
	 * @param extraCurrCategory
	 * @param classStandardName
	 * @param syllabusName
	 * @param stateName
	 * @param subjectName
	 * @param batchName
	 * @param batchEndDate
	 * @param batchDeactivateDate
	 * @param batchSpecialOfferFlag
	 * @param batchGroupName
	 * @param bankName
	 * @param bankTransactionId
	 * @param orderId
	 * @param paymentMode
	 * @param paymentStatus
	 * @param transactionAmount
	 * @param transactionDate
	 */
	public StudentSubscriptionInfoDTO(Long idStudentOrder, Instant purchaseDate, Instant nextPaymentDate,
			Instant lastPaymentDate, Instant subscriptionEndDate, Boolean activeFlag, String purchaseAmount,
			String subscriptionType, String purchaseLevel, String purchaseType, Long userSurId,
			Boolean specialOfferFlag, Long idSpecialOffer, Float monthlySubcrAmt, Float annualSubscrAmt,
			Float qtrSubscrAmt, String ageGroup, String productName, String productCd, int batchSize,
			String extraCurrCategory, String classStandardName, String syllabusName, String stateName,
			String subjectName, String batchName, LocalDate batchEndDate, LocalDate batchDeactivateDate,
			Boolean batchSpecialOfferFlag, String batchGroupName, String bankName, String bankTransactionId,
			String orderId, String paymentMode, String paymentStatus, Float transactionAmount,
			Instant transactionDate) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.userSurId = userSurId;
		this.specialOfferFlag = specialOfferFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.batchSize = batchSize;
		this.extraCurrCategory = extraCurrCategory;
		this.classStandardName = classStandardName;
		this.syllabusName = syllabusName;
		this.stateName = stateName;
		this.subjectName = subjectName;
		this.batchName = batchName;
		this.batchEndDate = batchEndDate;
		this.batchDeactivateDate = batchDeactivateDate;
		this.batchSpecialOfferFlag = batchSpecialOfferFlag;
		this.batchGroupName = batchGroupName;
		this.bankName = bankName;
		this.bankTransactionId = bankTransactionId;
		this.orderId = orderId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
	}

	/**
	 * 
	 */
	public StudentSubscriptionInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankTransactionId
	 */
	public String getBankTransactionId() {
		return bankTransactionId;
	}

	/**
	 * @param bankTransactionId the bankTransactionId to set
	 */
	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the transactionAmount
	 */
	public Float getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionDate
	 */
	public Instant getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Instant transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the idStagingStudentSubscription
	 */
	public Long getIdStagingStudentSubscription() {
		return idStagingStudentSubscription;
	}

	/**
	 * @param idStagingStudentSubscription the idStagingStudentSubscription to set
	 */
	public void setIdStagingStudentSubscription(Long idStagingStudentSubscription) {
		this.idStagingStudentSubscription = idStagingStudentSubscription;
	}

	/**
	 * @param idStudentOrder
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param userSurId
	 * @param specialOfferFlag
	 * @param idSpecialOffer
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param batchSize
	 * @param extraCurrCategory
	 * @param classStandardName
	 * @param syllabusName
	 * @param stateName
	 * @param subjectName
	 * @param batchName
	 * @param batchEndDate
	 * @param batchDeactivateDate
	 * @param batchSpecialOfferFlag
	 * @param batchGroupName
	 * @param bankName
	 * @param bankTransactionId
	 * @param orderId
	 * @param paymentMode
	 * @param paymentStatus
	 * @param transactionAmount
	 * @param transactionDate
	 * @param idStagingStudentSubscription
	 */
	public StudentSubscriptionInfoDTO(Long idStudentOrder, Instant purchaseDate, Instant nextPaymentDate,
			Instant lastPaymentDate, Instant subscriptionEndDate, Boolean activeFlag, String purchaseAmount,
			String subscriptionType, String purchaseLevel, String purchaseType, Long userSurId,
			Boolean specialOfferFlag, Long idSpecialOffer, Float monthlySubcrAmt, Float annualSubscrAmt,
			Float qtrSubscrAmt, String ageGroup, String productName, String productCd, int batchSize,
			String extraCurrCategory, String classStandardName, String syllabusName, String stateName,
			String subjectName, String batchName, LocalDate batchEndDate, LocalDate batchDeactivateDate,
			Boolean batchSpecialOfferFlag, String batchGroupName, String bankName, String bankTransactionId,
			String orderId, String paymentMode, String paymentStatus, Float transactionAmount, Instant transactionDate,
			Long idStagingStudentSubscription) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.userSurId = userSurId;
		this.specialOfferFlag = specialOfferFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.batchSize = batchSize;
		this.extraCurrCategory = extraCurrCategory;
		this.classStandardName = classStandardName;
		this.syllabusName = syllabusName;
		this.stateName = stateName;
		this.subjectName = subjectName;
		this.batchName = batchName;
		this.batchEndDate = batchEndDate;
		this.batchDeactivateDate = batchDeactivateDate;
		this.batchSpecialOfferFlag = batchSpecialOfferFlag;
		this.batchGroupName = batchGroupName;
		this.bankName = bankName;
		this.bankTransactionId = bankTransactionId;
		this.orderId = orderId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.idStagingStudentSubscription = idStagingStudentSubscription;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the actualAmount
	 */
	public Float getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(Float actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @param idStudentOrder
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param userSurId
	 * @param specialOfferFlag
	 * @param idSpecialOffer
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param batchSize
	 * @param extraCurrCategory
	 * @param classStandardName
	 * @param syllabusName
	 * @param stateName
	 * @param subjectName
	 * @param batchName
	 * @param batchEndDate
	 * @param batchDeactivateDate
	 * @param batchSpecialOfferFlag
	 * @param batchGroupName
	 * @param bankName
	 * @param bankTransactionId
	 * @param orderId
	 * @param paymentMode
	 * @param paymentStatus
	 * @param transactionAmount
	 * @param transactionDate
	 * @param idStagingStudentSubscription
	 * @param couponCode
	 * @param actualAmount
	 */
	public StudentSubscriptionInfoDTO(Long idStudentOrder, Instant purchaseDate, Instant nextPaymentDate,
			Instant lastPaymentDate, Instant subscriptionEndDate, Boolean activeFlag, String purchaseAmount,
			String subscriptionType, String purchaseLevel, String purchaseType, Long userSurId,
			Boolean specialOfferFlag, Long idSpecialOffer, Float monthlySubcrAmt, Float annualSubscrAmt,
			Float qtrSubscrAmt, String ageGroup, String productName, String productCd, int batchSize,
			String extraCurrCategory, String classStandardName, String syllabusName, String stateName,
			String subjectName, String batchName, LocalDate batchEndDate, LocalDate batchDeactivateDate,
			Boolean batchSpecialOfferFlag, String batchGroupName, String bankName, String bankTransactionId,
			String orderId, String paymentMode, String paymentStatus, Float transactionAmount, Instant transactionDate,
			Long idStagingStudentSubscription, String couponCode, Float actualAmount) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.userSurId = userSurId;
		this.specialOfferFlag = specialOfferFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.batchSize = batchSize;
		this.extraCurrCategory = extraCurrCategory;
		this.classStandardName = classStandardName;
		this.syllabusName = syllabusName;
		this.stateName = stateName;
		this.subjectName = subjectName;
		this.batchName = batchName;
		this.batchEndDate = batchEndDate;
		this.batchDeactivateDate = batchDeactivateDate;
		this.batchSpecialOfferFlag = batchSpecialOfferFlag;
		this.batchGroupName = batchGroupName;
		this.bankName = bankName;
		this.bankTransactionId = bankTransactionId;
		this.orderId = orderId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.idStagingStudentSubscription = idStagingStudentSubscription;
		this.couponCode = couponCode;
		this.actualAmount = actualAmount;
	}

	
	
	

}
