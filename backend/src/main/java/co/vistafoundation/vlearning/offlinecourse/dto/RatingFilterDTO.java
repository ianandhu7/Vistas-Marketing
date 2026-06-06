/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

import java.time.Instant;

/**
 * Test
 */
public class RatingFilterDTO {
	
	private Long userSurId;
	
	private String classStandard;
	
	private String userName;
	
	private Integer rating;
	
	private Instant createdAt;
	
	private String review;
	
	private String subject;
	
	private String topic;
	
	

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
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
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
	 * 
	 */
	public RatingFilterDTO() {
		super();
	}

	/**
	 * @param userSurId
	 * @param classStandard
	 * @param userName
	 * @param rating
	 * @param createdAt
	 * @param review
	 * @param subject
	 * @param topic
	 */
	public RatingFilterDTO(Long userSurId, String classStandard, String userName, Integer rating, Instant createdAt,
			String review, String subject, String topic) {
		super();
		this.userSurId = userSurId;
		this.classStandard = classStandard;
		this.userName = userName;
		this.rating = rating;
		this.createdAt = createdAt;
		this.review = review;
		this.subject = subject;
		this.topic = topic;
	}

	/**
	 * @param userSurId
	 * @param classStandard
	 * @param userName
	 * @param rating
	 * @param createdAt
	 * @param review
	 * @param subject
	 */
	public RatingFilterDTO(Long userSurId, String classStandard, String userName, Integer rating, Instant createdAt,
			String review, String subject) {
		super();
		this.userSurId = userSurId;
		this.classStandard = classStandard;
		this.userName = userName;
		this.rating = rating;
		this.createdAt = createdAt;
		this.review = review;
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "RatingFilterDTO [userSurId=" + userSurId + ", classStandard=" + classStandard + ", userName=" + userName
				+ ", rating=" + rating + ", createdAt=" + createdAt + ", review=" + review + ", subject=" + subject
				+ ", topic=" + topic + "]";
	}
	
	
	
}
