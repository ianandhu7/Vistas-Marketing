package co.vistafoundation.vlearning.product.dto;

import java.util.List;

import co.vistafoundation.vlearning.product.model.ProductSampleVideo;

public class ProductDTO {
	private Long idProduct;
	private Long idSubject;
	private String productName;
	private Float monthlySubcrAmt;
	private Float qtrSubscrAmt;
	private Float annualSubscrAmt;
	private List<ProductSampleVideo> productSampleVideo;
	
	
	public Float getMonthlySubcrAmt() {
		return monthlySubcrAmt;
	}
	public void setMonthlySubcrAmt(Float monthlySubcrAmt) {
		this.monthlySubcrAmt = monthlySubcrAmt;
	}
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public List<ProductSampleVideo> getProductSampleVideo() {
		return productSampleVideo;
	}
	public void setProductSampleVideo(List<ProductSampleVideo> productSampleVideo) {
		this.productSampleVideo = productSampleVideo;
	}
	public Float getQtrSubscrAmt() {
		return qtrSubscrAmt;
	}
	public void setQtrSubscrAmt(Float qtrSubscrAmt) {
		this.qtrSubscrAmt = qtrSubscrAmt;
	}
	public Float getAnnualSubscrAmt() {
		return annualSubscrAmt;
	}
	public void setAnnualSubscrAmt(Float annualSubscrAmt) {
		this.annualSubscrAmt = annualSubscrAmt;
	}
	
}
