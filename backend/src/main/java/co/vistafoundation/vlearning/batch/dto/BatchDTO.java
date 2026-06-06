package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subject.model.Subject;

public class BatchDTO {
	
	private Long idBatch;
	
	private Long idProduct;
	
	private String batchName;
	
	private LocalTime batchFromTime;

	private LocalTime batchToTime;
	
	private int currentVacancy;

	private int currentOccupancy;
	
	private DayOfWeekCode dayOfWeekCode;
	
	private String demoVideoUrl;
	
	private ClassStandard classStandard;

	private Subject subject;
	
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private Product product;

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public LocalTime getBatchFromTime() {
		return batchFromTime;
	}

	public void setBatchFromTime(LocalTime batchFromTime) {
		this.batchFromTime = batchFromTime;
	}

	public LocalTime getBatchToTime() {
		return batchToTime;
	}

	public void setBatchToTime(LocalTime batchToTime) {
		this.batchToTime = batchToTime;
	}

	public int getCurrentVacancy() {
		return currentVacancy;
	}

	public void setCurrentVacancy(int currentVacancy) {
		this.currentVacancy = currentVacancy;
	}

	public int getCurrentOccupancy() {
		return currentOccupancy;
	}

	public void setCurrentOccupancy(int currentOccupancy) {
		this.currentOccupancy = currentOccupancy;
	}

	public DayOfWeekCode getDayOfWeekCode() {
		return dayOfWeekCode;
	}

	public void setDayOfWeekCode(DayOfWeekCode dayOfWeekCode) {
		this.dayOfWeekCode = dayOfWeekCode;
	}

	public String getDemoVideoUrl() {
		return demoVideoUrl;
	}

	public void setDemoVideoUrl(String demoVideoUrl) {
		this.demoVideoUrl = demoVideoUrl;
	}

	public ClassStandard getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(ClassStandard classStandard) {
		this.classStandard = classStandard;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
