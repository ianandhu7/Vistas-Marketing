package co.vistafoundation.vlearning.offlinecourse.dto;

public class OfflineVideoCourseResponsewithOTP {
	
	
	/**author Meghana**/
	
	
	private Long idOfflineVideoCourse;

	private Long idProduct;

	private String topic;

	private Long idSubjectChapter;

	private Long idSubject;

	private String videoDescription;
	
	private String heading;
	
	private String question;
	
	private String answer;
	
	private String pdfURL;
	
	private String videoEnLink;
	
	private String videoOtp;
	
	
	

	public String getVideoOtp() {
		return videoOtp;
	}

	public void setVideoOtp(String videoOtp) {
		this.videoOtp = videoOtp;
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
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
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
	 * @return the videoDescription
	 */
	public String getVideoDescription() {
		return videoDescription;
	}

	/**
	 * @param videoDescription the videoDescription to set
	 */
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
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

	public String getVideoEnLink() {
		return videoEnLink;
	}

	public void setVideoEnLink(String videoEnLink) {
		this.videoEnLink = videoEnLink;
	}
	
	

}
