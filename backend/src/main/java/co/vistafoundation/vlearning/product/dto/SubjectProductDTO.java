/**
 * 
 */
package co.vistafoundation.vlearning.product.dto;

/**
 * @author NAVEEN This class will reatin same info product entity including
 *         additional parameter subject name
 *
 */
public class SubjectProductDTO {

	private Long idProduct;

	private Long idProductGroup;

	private Long idClassStandard;

	private Long idSubject;

	private Float monthlySubcrAmt;

	private Float annualSubscrAmt;

	private Float qtrSubscrAmt;

	private String ageGroup;

	private String productName;

	private String productCd;

	private int batchSize;

	private String extraCurrCategory;

	private Long idProductLine;

	private Long idSyllabus;

	private Integer totalVideoCount;

	private Long idState;

	private String subjectName;

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
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
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
	 * @return the idProductLine
	 */
	public Long getIdProductLine() {
		return idProductLine;
	}

	/**
	 * @param idProductLine the idProductLine to set
	 */
	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the totalVideoCount
	 */
	public Integer getTotalVideoCount() {
		return totalVideoCount;
	}

	/**
	 * @param totalVideoCount the totalVideoCount to set
	 */
	public void setTotalVideoCount(Integer totalVideoCount) {
		this.totalVideoCount = totalVideoCount;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
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
	 * @param idProduct
	 * @param idProductGroup
	 * @param idClassStandard
	 * @param idSubject
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param batchSize
	 * @param extraCurrCategory
	 * @param idProductLine
	 * @param idSyllabus
	 * @param totalVideoCount
	 * @param idState
	 * @param subjectName
	 */
	public SubjectProductDTO(Long idProduct, Long idProductGroup, Long idClassStandard, Long idSubject,
			Float monthlySubcrAmt, Float annualSubscrAmt, Float qtrSubscrAmt, String ageGroup, String productName,
			String productCd, int batchSize, String extraCurrCategory, Long idProductLine, Long idSyllabus,
			Integer totalVideoCount, Long idState, String subjectName) {
		super();
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.batchSize = batchSize;
		this.extraCurrCategory = extraCurrCategory;
		this.idProductLine = idProductLine;
		this.idSyllabus = idSyllabus;
		this.totalVideoCount = totalVideoCount;
		this.idState = idState;
		this.subjectName = subjectName;
	}

	/**
	 * 
	 */
	public SubjectProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
