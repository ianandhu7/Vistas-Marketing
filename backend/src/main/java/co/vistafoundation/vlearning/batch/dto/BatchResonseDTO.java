package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subject.model.Subject;

public class BatchResonseDTO {

	private Long idBatch;

	private DayOfWeekCode dayOfWeekCode;

	private ClassStandard classStandard;

	private Subject subject;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private Product product;

	private String teacherName;

	private String teacherExpLevel;

	private int teacherRating;

	private LocalTime batchFromTime;

	private LocalTime batchToTime;

	private int currentVacancy;

	private int currentOccupancy;

	private String batchName;

	private String meetingDescription;

	private String demoVideoUrl;

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherExpLevel() {
		return teacherExpLevel;
	}

	public void setTeacherExpLevel(String teacherExpLevel) {
		this.teacherExpLevel = teacherExpLevel;
	}

	public int getTeacherRating() {
		return teacherRating;
	}

	public void setTeacherRating(int teacherRating) {
		this.teacherRating = teacherRating;
	}

	public DayOfWeekCode getDayOfWeekCode() {
		return dayOfWeekCode;
	}

	public void setDayOfWeekCode(DayOfWeekCode dayOfWeekCode) {
		this.dayOfWeekCode = dayOfWeekCode;
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


	public String getMeetingDescription() {
		return meetingDescription;
	}

	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}

	public String getDemoVideoUrl() {
		return demoVideoUrl;
	}

	public void setDemoVideoUrl(String demoVideoUrl) {
		this.demoVideoUrl = demoVideoUrl;
	}

	/**
	 * @return the batchFromTime
	 */
	public LocalTime getBatchFromTime() {
		return batchFromTime;
	}

	/**
	 * @param batchFromTime the batchFromTime to set
	 */
	public void setBatchFromTime(LocalTime batchFromTime) {
		this.batchFromTime = batchFromTime;
	}

	/**
	 * @return the batchToTime
	 */
	public LocalTime getBatchToTime() {
		return batchToTime;
	}

	/**
	 * @param batchToTime the batchToTime to set
	 */
	public void setBatchToTime(LocalTime batchToTime) {
		this.batchToTime = batchToTime;
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
	
	

}
