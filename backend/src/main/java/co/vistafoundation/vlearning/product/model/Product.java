package co.vistafoundation.vlearning.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "PRODUCT" ,uniqueConstraints= @UniqueConstraint(
		columnNames={"idPRODUCT_GROUP", "idCLASS_STANDARD","idSUBJECT","idSYLLABUS","idSTATE","EXTRA_CURR_CATEGORY"}))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class Product extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT", nullable = false)
	private Long idProduct;

	@Column(name = "idPRODUCT_GROUP", nullable = false)
	private Long idProductGroup;

	@Column(name = "idCLASS_STANDARD", nullable = false)
	private Long idClassStandard;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "MONTHLY_SUBSCR_AMT")
	private Float monthlySubcrAmt;
	
	@Column(name = "ANNUAL_SUBSCR_AMT")
	private Float annualSubscrAmt;
	
	@Column(name = "QTR_SUBSCR_AMT")
	private Float qtrSubscrAmt;

	@Column(name = "AGE_GROUP", length = 45)
	private String ageGroup;
	
	@Column(name = "PRODUCT_NAME", length = 45)
	private String productName;
	
	@Column(name = "PRODUCT_CD", length = 45)
	private String productCd;
	
	@Column(name = "BATCH_SIZE")
	private int batchSize;
	
	@Column(name = "EXTRA_CURR_CATEGORY", length = 45)
	private String extraCurrCategory;
	
	@Column(name = "idPRODUCT_LINE")
	private Long idProductLine;
	
	@Column(name = "idSYLLABUS", length = 10)
	private Long idSyllabus;
	
    @JsonIgnore
	@Column(name = "TOTAL_VIDEO_COUNT")
	private Integer totalVideoCount;
    
    @Column(name = "idSTATE")
    private Long idState;
    
    @Column(name ="ACTIVE_FLAG")
	private Boolean activeFlag;  
	

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public String getExtraCurrCategory() {
		return extraCurrCategory;
	}

	public void setExtraCurrCategory(String extraCurrCategory) {
		this.extraCurrCategory = extraCurrCategory;
	}

	public Long getIdProductLine() {
		return idProductLine;
	}

	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	

	public Long getIdProductGroup() {
		return idProductGroup;
	}

	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}

	public Float getMonthlySubcrAmt() {
		return monthlySubcrAmt;
	}

	public void setMonthlySubcrAmt(Float monthlySubcrAmt) {
		this.monthlySubcrAmt = monthlySubcrAmt;
	}

	public Float getAnnualSubscrAmt() {
		return annualSubscrAmt;
	}

	public void setAnnualSubscrAmt(Float annualSubscrAmt) {
		this.annualSubscrAmt = annualSubscrAmt;
	}

	public Float getQtrSubscrAmt() {
		return qtrSubscrAmt;
	}

	public void setQtrSubscrAmt(Float qtrSubscrAmt) {
		this.qtrSubscrAmt = qtrSubscrAmt;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}



	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
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

	

	public Product(Long idProductGroup, Long idClassStandard, Long idSubject, Float monthlySubcrAmt,
			Float annualSubscrAmt, Float qtrSubscrAmt, String ageGroup, String productName, String productCd,
			int batchSize, String extraCurrCategory, Long idProductLine, Long idSyllabus, Integer totalVideoCount,
			Long idState) {
		super();
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
		this.activeFlag = true;
	}
	
	

	public Product(Long idProductGroup, Long idClassStandard, Long idSubject, Float monthlySubcrAmt,
			Float annualSubscrAmt, Float qtrSubscrAmt, String ageGroup, String productName, String productCd,
			int batchSize, String extraCurrCategory, Long idProductLine, Long idSyllabus, Integer totalVideoCount,
			Long idState, Boolean activeFlag) {
		super();
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
		this.activeFlag = activeFlag;
	}

	public Product() {

	}
	
	

}
