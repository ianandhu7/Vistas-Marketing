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

@Table(name = "PRODUCT_LINE", uniqueConstraints = @UniqueConstraint(columnNames = { "PRODUCT_LINE",
		"PRODUCT_CATEGORY"}))

@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })

public class ProductLine extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idPRODUCT_LINE", nullable = false)
	private Long idProductLine;

	@Column(name = "PRODUCT_LINE_CD")
	private String productLineCd;

	@Column(name = "PRODUCT_LINE")
	private String productLine;

	@Column(name = "PRODUCT_CATEGORY_CD")
	private String productCategoryCd;

	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory;
	
	public Long getIdProductLine() {
		return idProductLine;
	}

	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	public String getProductLineCd() {
		return productLineCd;
	}

	public void setProductLineCd(String productLineCd) {
		this.productLineCd = productLineCd;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getProductCategoryCd() {
		return productCategoryCd;
	}

	public void setProductCategoryCd(String productCategoryCd) {
		this.productCategoryCd = productCategoryCd;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	

	public ProductLine(String productLineCd, String productLine, String productCategoryCd, String productCategory) {
		super();
		this.productLineCd = productLineCd;
		this.productLine = productLine;
		this.productCategoryCd = productCategoryCd;
		this.productCategory = productCategory;

	}

	public ProductLine() {

	}

}
