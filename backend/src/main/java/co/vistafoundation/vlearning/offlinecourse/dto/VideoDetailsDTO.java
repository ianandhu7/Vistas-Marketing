/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

/**
 * @author Abdul Elahi
 */
public class VideoDetailsDTO {
	
	private Long idOfflineVideoCourse;
	
	private String classStandard;
	
	private Double rating;
	
	private String subject;
	
	private String topic;
	
	private String thumbnail;

	
	public VideoDetailsDTO() {
		super();
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
	 * @return the classStandard
	 */
	public String getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @param idOfflineVideoCourse
	 * @param classStandard
	 * @param rating
	 * @param subject
	 * @param topic
	 * @param thumbnail
	 */
	public VideoDetailsDTO(Long idOfflineVideoCourse, String classStandard, Double rating, String subject,
			String topic, String thumbnail) {
		super();
		this.idOfflineVideoCourse = idOfflineVideoCourse;
		this.classStandard = classStandard;
		this.rating = rating;
		this.subject = subject;
		this.topic = topic;
		this.thumbnail = thumbnail;
	}
	
	
	
}
