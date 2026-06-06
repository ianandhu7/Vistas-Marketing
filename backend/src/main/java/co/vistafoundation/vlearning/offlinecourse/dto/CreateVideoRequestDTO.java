package co.vistafoundation.vlearning.offlinecourse.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 
 * @author Naveen Kumar
 *
 */

public class CreateVideoRequestDTO {

	
	@NotNull
	private Long idProduct;

	@NotNull
	private String topic;

	@NotNull
	private Long idSubject;

	@NotNull
	private Long idClassStandard;

	@NotNull
	private Long idSubjectChapter;

	private int videoSeqNumber;

	private String videoPosterLocation;

	private int videoDuration;

	private String description;

	@NotNull
	private String videoEnLink;
	
	private Long idSyllabus;
	
	private String pdfUrl;
	
	private Set<VideoLinkDTO> videoLinks = new HashSet<>();
	
	@NotNull
	private String heading;
	
	@NotNull
	private String question;
	
	@NotNull
	private String answer;
    
	private Long idState;
	
	private String folderId;
	
	private String videoUrl;
   
	private String teacherNoteUrl;
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}



	/**
	 * @return the videoSeqNumber
	 */
	public int getVideoSeqNumber() {
		return videoSeqNumber;
	}

	/**
	 * @param videoSeqNumber the videoSeqNumber to set
	 */
	public void setVideoSeqNumber(int videoSeqNumber) {
		this.videoSeqNumber = videoSeqNumber;
	}

	public String getVideoPosterLocation() {
		return videoPosterLocation;
	}

	public void setVideoPosterLocation(String videoPosterLocation) {
		this.videoPosterLocation = videoPosterLocation;
	}

	public int getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}

	/*
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
	 * @return the videoLinks
	 */
	public Set<VideoLinkDTO> getVideoLinks() {
		return videoLinks;
	}

	/**
	 * @param videoLinks the videoLinks to set
	 */
	public void setVideoLinks(Set<VideoLinkDTO> videoLinks) {
		this.videoLinks = videoLinks;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
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
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the pdfUrl
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * @param pdfUrl the pdfUrl to set
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * @return the folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the videoUrl
	 */
	public String getVideoUrl() {
		return videoUrl;
	}

	/**
	 * @param videoUrl the videoUrl to set
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	/**
	 * @return the teacherNoteUrl
	 */
	public String getTeacherNoteUrl() {
		return teacherNoteUrl;
	}

	/**
	 * @param teacherNoteUrl the teacherNoteUrl to set
	 */
	public void setTeacherNoteUrl(String teacherNoteUrl) {
		this.teacherNoteUrl = teacherNoteUrl;
	}


	/**
	 * @return the idProductGroup
	 */
	
	
	
	

}
