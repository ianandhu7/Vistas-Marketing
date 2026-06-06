package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;

public class StudentVideoStreamingDTO implements Serializable{


	private static final long serialVersionUID = 3278721907140810212L;

	private Long idOfflineVideoCourse;
	
	private String videoPoster1Location;
	
	private String videoEnLink;
	
	private String videoOtp;
	
	private String pdfURL;
	
	private String techerNoteURL;
	
    private String question;
	
	private String answer;
	
	private Integer videoSeqNumber;
	
	private Long idStudentAssignedCourse;
	
	private Boolean completeFlag;
	
	private Integer videoCoverageDuration;
	
	private Integer videoDuration;
	
	private String pctComplete;
	
	private Boolean isCurrentlyStreaming;

	/**
	 * 
	 */
	public StudentVideoStreamingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	




	/**
	 * @param idOfflineVideoCourse
	 * @param videoPoster1Location
	 * @param videoEnLink
	 * @param videoOtp
	 * @param pdfURL
	 * @param techerNoteURL
	 * @param question
	 * @param answer
	 * @param videoSeqNumber
	 * @param idStudentAssignedCourse
	 * @param completeFlag
	 * @param videoCoverageDuration
	 * @param videoDuration
	 * @param pctComplete
	 */
	public StudentVideoStreamingDTO(Long idOfflineVideoCourse, String videoPoster1Location, String videoEnLink,
			String videoOtp, String pdfURL, String techerNoteURL, String question, String answer,
			Integer videoSeqNumber, Long idStudentAssignedCourse, Boolean completeFlag, Integer videoCoverageDuration,
			Integer videoDuration, String pctComplete) {
		super();
		this.idOfflineVideoCourse = idOfflineVideoCourse;
		this.videoPoster1Location = videoPoster1Location;
		this.videoEnLink = videoEnLink;
		this.videoOtp = videoOtp;
		this.pdfURL = pdfURL;
		this.techerNoteURL = techerNoteURL;
		this.question = question;
		this.answer = answer;
		this.videoSeqNumber = videoSeqNumber;
		this.idStudentAssignedCourse = idStudentAssignedCourse;
		this.completeFlag = completeFlag;
		this.videoCoverageDuration = videoCoverageDuration;
		this.videoDuration = videoDuration;
		this.pctComplete = pctComplete;
	}








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
	 * @return the videoPoster1Location
	 */
	public String getVideoPoster1Location() {
		return videoPoster1Location;
	}

	/**
	 * @param videoPoster1Location the videoPoster1Location to set
	 */
	public void setVideoPoster1Location(String videoPoster1Location) {
		this.videoPoster1Location = videoPoster1Location;
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
	 * @return the videoOtp
	 */
	public String getVideoOtp() {
		return videoOtp;
	}

	/**
	 * @param videoOtp the videoOtp to set
	 */
	public void setVideoOtp(String videoOtp) {
		this.videoOtp = videoOtp;
	}

	/**
	 * @return the pdfURL
	 */
	public String getPdfURL() {
		return pdfURL;
	}

	/**
	 * @param pdfURL the pdfURL to set
	 */
	public void setPdfURL(String pdfURL) {
		this.pdfURL = pdfURL;
	}

	/**
	 * @return the techerNoteURL
	 */
	public String getTecherNoteURL() {
		return techerNoteURL;
	}

	/**
	 * @param techerNoteURL the techerNoteURL to set
	 */
	public void setTecherNoteURL(String techerNoteURL) {
		this.techerNoteURL = techerNoteURL;
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
	 * @return the videoSeqNumber
	 */
	public Integer getVideoSeqNumber() {
		return videoSeqNumber;
	}

	/**
	 * @param videoSeqNumber the videoSeqNumber to set
	 */
	public void setVideoSeqNumber(Integer videoSeqNumber) {
		this.videoSeqNumber = videoSeqNumber;
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
	 * @return the videoCoverageDuration
	 */
	public Integer getVideoCoverageDuration() {
		return videoCoverageDuration;
	}

	/**
	 * @param videoCoverageDuration the videoCoverageDuration to set
	 */
	public void setVideoCoverageDuration(Integer videoCoverageDuration) {
		this.videoCoverageDuration = videoCoverageDuration;
	}

	/**
	 * @return the videoDuration
	 */
	public Integer getVideoDuration() {
		return videoDuration;
	}

	/**
	 * @param videoDuration the videoDuration to set
	 */
	public void setVideoDuration(Integer videoDuration) {
		this.videoDuration = videoDuration;
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
	 * @return the isCurrentlyStreaming
	 */
	public Boolean getIsCurrentlyStreaming() {
		return isCurrentlyStreaming;
	}

	/**
	 * @param isCurrentlyStreaming the isCurrentlyStreaming to set
	 */
	public void setIsCurrentlyStreaming(Boolean isCurrentlyStreaming) {
		this.isCurrentlyStreaming = isCurrentlyStreaming;
	}
	
	
}