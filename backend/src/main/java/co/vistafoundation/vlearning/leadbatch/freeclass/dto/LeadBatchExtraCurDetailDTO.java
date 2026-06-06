package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalTime;

/**
 * 
 * @author Sajini
 *
 */

public class LeadBatchExtraCurDetailDTO {
	
	private Long idSubjectExtraCurricular;
	private Long idLevelExtraCurricular;
	private Long idLanguage;
	private Long idAvailableSlot;
	private int count;
	private String subjectName;
	private String level;
	private String language;
	private String dayOfWeek;
	private LocalTime fromTime;
	private LocalTime toTime;
	public Long getIdSubjectExtraCurricular() {
		return idSubjectExtraCurricular;
	}
	public void setIdSubjectExtraCurricular(Long idSubjectExtraCurricular) {
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
	}
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
	}
	public Long getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}
	public Long getIdAvailableSlot() {
		return idAvailableSlot;
	}
	public void setIdAvailableSlot(Long idAvailableSlot) {
		this.idAvailableSlot = idAvailableSlot;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public LocalTime getFromTime() {
		return fromTime;
	}
	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}
	public LocalTime getToTime() {
		return toTime;
	}
	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}
	public LeadBatchExtraCurDetailDTO(Long idSubjectExtraCurricular, Long idLevelExtraCurricular, Long idLanguage,
			Long idAvailableSlot, int count, String subjectName, String level, String language, String dayOfWeek,
			LocalTime fromTime, LocalTime toTime) {
		super();
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idLanguage = idLanguage;
		this.idAvailableSlot = idAvailableSlot;
		this.count = count;
		this.subjectName = subjectName;
		this.level = level;
		this.language = language;
		this.dayOfWeek = dayOfWeek;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}
	public LeadBatchExtraCurDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeadBatchExtraCurDetailDTO(Long idSubjectExtraCurricular, Long idLevelExtraCurricular, Long idLanguage,
			Long idAvailableSlot) {
		super();
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idLanguage = idLanguage;
		this.idAvailableSlot = idAvailableSlot;
	}
	
	
	

}
