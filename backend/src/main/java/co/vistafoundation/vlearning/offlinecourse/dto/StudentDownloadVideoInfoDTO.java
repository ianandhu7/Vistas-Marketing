/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

import java.time.Instant;

/**
 * @author Naveen Kumar
 *
 */
public class StudentDownloadVideoInfoDTO {
	
	private Long idOfflineVideoCourse;
	
	private Long idSubject;
	
	private Long idSubjectChapter;
	
	private Long idStudentAssignedCourse;

	private String subjectName;
	
	private String chapterName;
	
	private String heading;
	
	private String question;
	
	private String answer;
	
    private String secondLangHeading;
	
	private String secondLangQuestion;
	
	private String secondLangAnswer;
	
	private Long idLanguage;
	
	private String languageName;
	
	private Long idVlUser;
	
	private String videoEnLink;
	
	private String videoSecondLangLink;
	
	private String videoDnObjectEn;
	
	private String videoDnObjectSecondLang;
	
	private String pctComplete;
	
	private int videoDuration;
	
	private int videoCoverageDuration;
	
	private Boolean completeFlag;
	
	private Instant lastAccess;
	
	private Boolean isSynced;

	/**
	 * @return the idOfflineVideoCourse
	 */
	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	/**
	 * @param idOfflineVideoCourse the idOfflineVideoCourse to set
	 */
	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	/**
	 * @return the idSubjectChapter
	 */
	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	/**
	 * @param idSubjectChapter the idSubjectChapter to set
	 */
	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	/**
	 * @return the idStudentAssignedCourse
	 */
	public Long getIdStudentAssignedCourse() {
		return idStudentAssignedCourse;
	}

	/**
	 * @param idStudentAssignedCourse the idStudentAssignedCourse to set
	 */
	public void setIdStudentAssignedCourse(Long idStudentAssignedCourse) {
		this.idStudentAssignedCourse = idStudentAssignedCourse;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the chapterName
	 */
	public String getChapterName() {
		return chapterName;
	}

	/**
	 * @param chapterName the chapterName to set
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the secondLangHeading
	 */
	public String getSecondLangHeading() {
		return secondLangHeading;
	}

	/**
	 * @param secondLangHeading the secondLangHeading to set
	 */
	public void setSecondLangHeading(String secondLangHeading) {
		this.secondLangHeading = secondLangHeading;
	}

	/**
	 * @return the secondLangQuestion
	 */
	public String getSecondLangQuestion() {
		return secondLangQuestion;
	}

	/**
	 * @param secondLangQuestion the secondLangQuestion to set
	 */
	public void setSecondLangQuestion(String secondLangQuestion) {
		this.secondLangQuestion = secondLangQuestion;
	}

	/**
	 * @return the secondLangAnswer
	 */
	public String getSecondLangAnswer() {
		return secondLangAnswer;
	}

	/**
	 * @param secondLangAnswer the secondLangAnswer to set
	 */
	public void setSecondLangAnswer(String secondLangAnswer) {
		this.secondLangAnswer = secondLangAnswer;
	}

	/**
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @return the languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName the languageName to set
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @return the videoEnLink
	 */
	public String getVideoEnLink() {
		return videoEnLink;
	}

	/**
	 * @param videoEnLink the videoEnLink to set
	 */
	public void setVideoEnLink(String videoEnLink) {
		this.videoEnLink = videoEnLink;
	}

	/**
	 * @return the videoSecondLangLink
	 */
	public String getVideoSecondLangLink() {
		return videoSecondLangLink;
	}

	/**
	 * @param videoSecondLangLink the videoSecondLangLink to set
	 */
	public void setVideoSecondLangLink(String videoSecondLangLink) {
		this.videoSecondLangLink = videoSecondLangLink;
	}

	/**
	 * @return the videoDnObjectEn
	 */
	public String getVideoDnObjectEn() {
		return videoDnObjectEn;
	}

	/**
	 * @param videoDnObjectEn the videoDnObjectEn to set
	 */
	public void setVideoDnObjectEn(String videoDnObjectEn) {
		this.videoDnObjectEn = videoDnObjectEn;
	}

	/**
	 * @return the videoDnObjectSecondLang
	 */
	public String getVideoDnObjectSecondLang() {
		return videoDnObjectSecondLang;
	}

	/**
	 * @param videoDnObjectSecondLang the videoDnObjectSecondLang to set
	 */
	public void setVideoDnObjectSecondLang(String videoDnObjectSecondLang) {
		this.videoDnObjectSecondLang = videoDnObjectSecondLang;
	}

	/**
	 * @return the pctComplete
	 */
	public String getPctComplete() {
		return pctComplete;
	}

	/**
	 * @param pctComplete the pctComplete to set
	 */
	public void setPctComplete(String pctComplete) {
		this.pctComplete = pctComplete;
	}

	/**
	 * @return the videoDuration
	 */
	public int getVideoDuration() {
		return videoDuration;
	}

	/**
	 * @param videoDuration the videoDuration to set
	 */
	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}

	/**
	 * @return the videoCoverageDuration
	 */
	public int getVideoCoverageDuration() {
		return videoCoverageDuration;
	}

	/**
	 * @param videoCoverageDuration the videoCoverageDuration to set
	 */
	public void setVideoCoverageDuration(int videoCoverageDuration) {
		this.videoCoverageDuration = videoCoverageDuration;
	}

	/**
	 * @return the completeFlag
	 */
	public Boolean getCompleteFlag() {
		return completeFlag;
	}

	/**
	 * @param completeFlag the completeFlag to set
	 */
	public void setCompleteFlag(Boolean completeFlag) {
		this.completeFlag = completeFlag;
	}

	/**
	 * @return the lastAccess
	 */
	public Instant getLastAccess() {
		return lastAccess;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(Instant lastAccess) {
		this.lastAccess = lastAccess;
	}

	/**
	 * @return the isSynced
	 */
	public Boolean getIsSynced() {
		return isSynced;
	}

	/**
	 * @param isSynced the isSynced to set
	 */
	public void setIsSynced(Boolean isSynced) {
		this.isSynced = isSynced;
	}
	
}
