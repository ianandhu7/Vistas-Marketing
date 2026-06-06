package co.vistafoundation.vlearning.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;


/**
 * @author Meghana
 * 
 **/

@Entity
@Table(name = "PRODUCT_GROUP",uniqueConstraints= { @UniqueConstraint(columnNames={"idPRODUCT_LINE", "idCLASS_STANDARD","EXTRA_CURR_CATEGORY","idSYLLABUS"})})
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ProductGroup extends UserDateAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT_GROUP", nullable = false)
	private Long idProductGroup;
	
	@Column(name = "PRODUCT_GROUP_NAME", length = 45)
	private String productGroupName;

	
	@Column(name = "MONTHLY_SUBSCR_AMT")
	private Float monthlySubscrAmt;
	
	@Column(name = "ANNUAL_SUBSCR_AMT")
	private Float annualSubscrAmt;
	
	@Column(name = "QTR_SUBSCR_AMT")
	private Float qtrSubscrAmt;
	
	@Column(name = "idPRODUCT_LINE", nullable = false)
	private Long idProductLine;

	
	@Column(name = "idCLASS_STANDARD", nullable = false)
	private Long idClassStandard;
	
	@Column(name = "EXTRA_CURR_CATEGORY", length = 45, columnDefinition= "varchar(45) default 'NA' " )
	private String extraCurrCategory;
	
	@Column(name = "idSYLLABUS", length = 10)
	private Long idSyllabus;	
	

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Long getIdProductGroup() {
		return idProductGroup;
	}

	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}
	
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public Float getMonthlySubscrAmt() {
		return monthlySubscrAmt;
	}

	public void setMonthlySubscrAmt(Float monthlySubscrAmt) {
		this.monthlySubscrAmt = monthlySubscrAmt;
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

	public Long getIdProductLine() {
		return idProductLine;
	}

	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public String getExtraCurrCategory() {
		return extraCurrCategory;
	}

	public void setExtraCurrCategory(String extraCurrCategory) {
		this.extraCurrCategory = extraCurrCategory;
	}

	

	public ProductGroup(String productGroupName, Float monthlySubscrAmt, Float annualSubscrAmt, Float qtrSubscrAmt,
			Long idProductLine, Long idClassStandard, String extraCurrCategory, Long idsyllabus) {
		super();
		this.productGroupName = productGroupName;
		this.monthlySubscrAmt = monthlySubscrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.idProductLine = idProductLine;
		this.idClassStandard = idClassStandard;
		this.extraCurrCategory = extraCurrCategory;
		this.idSyllabus = idsyllabus;
	}

	public ProductGroup() {
		
	}
	
}

