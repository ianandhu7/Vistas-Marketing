package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalTime;

public class BatchInformationDTO {
	private Long idBatch;
	private String batchName;
	private int batchSize;
	private String subjectName;
	private Long idTeacher;
	private String teacherName;
	//private List<BatchScheduleDTO> batchScheduleList;
	private String batchScheduleDays;
	private LocalTime batchFromTime;
	private LocalTime batchToTime;
	private int currentVacancy;
	private String classStandard;
	private String state;
	private String syllabus;
	private Long idProduct;
	private String productName;
	private Float monthlySubcrAmt;
	private Float quaterlySubcrAmt;
	private Float annualySubcrAmt;
	private Long idProductGroup;
	private String introVideoUrl;
	
	public Long getIdBatch() {
		return idBatch;
	}
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getIdTeacher() {
		return idTeacher;
	}
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getBatchScheduleDays() {
		return batchScheduleDays;
	}
	public void setBatchScheduleDays(String batchScheduleDays) {
		this.batchScheduleDays = batchScheduleDays;
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
	public String getClassStandard() {
		return classStandard;
	}
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSyllabus() {
		return syllabus;
	}
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}
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
	public Float getQuaterlySubcrAmt() {
		return quaterlySubcrAmt;
	}
	public void setQuaterlySubcrAmt(Float quaterlySubcrAmt) {
		this.quaterlySubcrAmt = quaterlySubcrAmt;
	}
	public Float getAnnualySubcrAmt() {
		return annualySubcrAmt;
	}
	public void setAnnualySubcrAmt(Float annualySubcrAmt) {
		this.annualySubcrAmt = annualySubcrAmt;
	}
	public Long getIdProductGroup() {
		return idProductGroup;
	}
	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}
	public String getIntroVideoUrl() {
		return introVideoUrl;
	}
	public void setIntroVideoUrl(String introVideoUrl) {
		this.introVideoUrl = introVideoUrl;
	}
	
}
