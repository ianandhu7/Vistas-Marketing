package co.vistafoundation.vlearning.batch.dto;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import co.vistafoundation.vlearning.batch.model.BatchCalender;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;

public class BatchInfoDTO {

	private String batchName;
	private Long idBatch;
	private String subjectName;
	private Long idTeacher;
	private int batchSize;
	private String teacherName;
	private DayOfWeekCode dayOfWeekCode;
	private LocalTime batchFromTime;
	private LocalTime batchToTime;
	private String attendeeMeetingUrl;
	private Instant nextPaymentDate;
	private String meetingPassword;
	private Long idSubscription;
	private int currentVacancy;
	private String classStandard;	
	private Long idProduct;
	private String productName;
	private Float monthlySubcrAmt;
	private List<BatchCalender> batchCalender;
	
	
	

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getMonthlySubcrAmt() {
		return monthlySubcrAmt;
	}

	public void setMonthlySubcrAmt(Float monthlySubcrAmt) {
		this.monthlySubcrAmt = monthlySubcrAmt;
	}

	public String getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	public int getCurrentVacancy() {
		return currentVacancy;
	}

	public void setCurrentVacancy(int currentVacancy) {
		this.currentVacancy = currentVacancy;
	}

	public Long getIdSubscription() {
		return idSubscription;
	}

	public void setIdSubscription(Long idSubscription) {
		this.idSubscription = idSubscription;
	}

	public Instant getNextPaymentDate() {
		return nextPaymentDate;
	}

	public void setNextPaymentDate(Instant nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	public String getAttendeeMeetingUrl() {
		return attendeeMeetingUrl;
	}

	public void setAttendeeMeetingUrl(String attendeeMeetingUrl) {
		this.attendeeMeetingUrl = attendeeMeetingUrl;
	}

	public DayOfWeekCode getDayOfWeekCode() {
		return dayOfWeekCode;
	}

	public void setDayOfWeekCode(DayOfWeekCode dayOfWeekCode) {
		this.dayOfWeekCode = dayOfWeekCode;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public BatchInfoDTO() {

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

	public String getMeetingPassword() {
		return meetingPassword;
	}

	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}
	
	

	/**
	 * @return the batchCalender
	 */
	public List<BatchCalender> getBatchCalender() {
		return batchCalender;
	}

	/**
	 * @param batchCalender the batchCalender to set
	 */
	public void setBatchCalender(List<BatchCalender> batchCalender) {
		this.batchCalender = batchCalender;
	}

	public BatchInfoDTO(String batchName, Long idBatch, String subjectName, Long idTeacher, int batchSize,
			String teacherName, DayOfWeekCode dayOfWeekCode, LocalTime batchFromTime, LocalTime batchToTime,
			String attendeeMeetingUrl, Instant nextPaymentDate, String meetingPassword) {
		super();
		this.batchName = batchName;
		this.idBatch = idBatch;
		this.subjectName = subjectName;
		this.idTeacher = idTeacher;
		this.batchSize = batchSize;
		this.teacherName = teacherName;
		this.dayOfWeekCode = dayOfWeekCode;
		this.batchFromTime = batchFromTime;
		this.batchToTime = batchToTime;
		this.attendeeMeetingUrl = attendeeMeetingUrl;
		this.nextPaymentDate = nextPaymentDate;
		this.meetingPassword = meetingPassword;
	}

}
