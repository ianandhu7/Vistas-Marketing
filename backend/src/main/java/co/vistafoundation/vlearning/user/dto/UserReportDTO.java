package co.vistafoundation.vlearning.user.dto;

public class UserReportDTO {
	public UserReportDTO(String syllabusName, String stateName, String mediumName, Long userCount) {
		super();
		this.syllabusName = syllabusName;
		this.stateName = stateName;
		this.mediumName = mediumName;
		this.userCount = userCount;
	}
	String syllabusName,stateName, mediumName;
	Long userCount;
	public String getSyllabusName() {
		return syllabusName;
	}
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getMediumName() {
		return mediumName;
	}
	public void setMediumName(String mediumName) {
		this.mediumName = mediumName;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

}
