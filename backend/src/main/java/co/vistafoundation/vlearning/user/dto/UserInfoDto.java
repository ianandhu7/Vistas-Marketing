package co.vistafoundation.vlearning.user.dto;

import java.time.Instant;

public class UserInfoDto {

	private Long userSurid;
	private String userRole;
	private Boolean isSubscribed;
	private Instant subscriptionStartDate;
	private Instant subscriptionEndDate;
	private Long idClassStandard;
	private String classStandard;
	private Long idSecondaryLanguage;
	private String secondaryLanguage;
	private Long idSyllabus;
	private String syllabusType;
	private Long idState;
	private String state;
	private String name;
	private String remarks;
	private String userPicURL;

	public Long getUserSurid() {
		return userSurid;
	}

	public void setUserSurid(Long userSurid) {
		this.userSurid = userSurid;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSecondaryLanguage() {
		return idSecondaryLanguage;
	}

	public void setIdSecondaryLanguage(Long idSecondaryLanguage) {
		this.idSecondaryLanguage = idSecondaryLanguage;
	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isSubscribed() {
		return isSubscribed;
	}

	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	public Instant getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Instant subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Instant getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Instant subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public String getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}

	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}

	public String getSyllabusType() {
		return syllabusType;
	}

	public void setSyllabusType(String syllabusType) {
		this.syllabusType = syllabusType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param isSubscribed the isSubscribed to set
	 */
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param studentRemarks the studentRemarks to set
	 */

	
	@Override
	public String toString() {
		return "UserInfoDto [userSurid=" + userSurid + ", userRole=" + userRole + ", isSubscribed=" + isSubscribed
				+ ", subscriptionStartDate=" + subscriptionStartDate + ", subscriptionEndDate=" + subscriptionEndDate
				+ ", idClassStandard=" + idClassStandard + ", classStandard=" + classStandard + ", idSecondaryLanguage="
				+ idSecondaryLanguage + ", secondaryLanguage=" + secondaryLanguage + ", idSyllabus=" + idSyllabus
				+ ", syllabusType=" + syllabusType + ", idState=" + idState + ", state=" + state + ", name=" + name
				+ ", remarks=" + remarks + "]";
	}

	/**
	 * @return the userPicURL
	 */
	public String getUserPicURL() {
		return userPicURL;
	}

	/**
	 * @param userPicURL the userPicURL to set
	 */
	public void setUserPicURL(String userPicURL) {
		this.userPicURL = userPicURL;
	}

	public UserInfoDto() {
		super();
	}

}