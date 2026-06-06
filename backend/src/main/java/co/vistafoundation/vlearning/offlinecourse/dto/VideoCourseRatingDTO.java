package co.vistafoundation.vlearning.offlinecourse.dto;
public class VideoCourseRatingDTO {
    private String topic;
    private Long idvlUser;
    private String userName;
    private String schoolName;
    private Long idofflineVideoCourse;
    private int rating;
    private String review;
    private Boolean visibleFlag;

    // Constructors, getters, and setters
    public VideoCourseRatingDTO(String topic, Long idvlUser, Long idofflineVideoCourse, int rating, String review) {
        this.topic = topic;
        this.idvlUser = idvlUser;
        this.idofflineVideoCourse = idofflineVideoCourse;
        this.rating = rating;
        this.review = review;
    }
    
    

    /**
	 * @param topic
	 * @param idvlUser
	 * @param userName
	 * @param schoolName
	 * @param idofflineVideoCourse
	 * @param rating
	 * @param review
	 */
	public VideoCourseRatingDTO(String topic, Long idvlUser, String userName, String schoolName,
			Long idofflineVideoCourse, int rating, String review,Boolean visibleFlag) {
		super();
		this.topic = topic;
		this.idvlUser = idvlUser;
		this.userName = userName;
		this.schoolName = schoolName;
		this.idofflineVideoCourse = idofflineVideoCourse;
		this.rating = rating;
		this.review = review;
		this.visibleFlag = visibleFlag;
	}



	/**
	 * 
	 */
	public VideoCourseRatingDTO() {
		// TODO Auto-generated constructor stub
	}



	public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getIdvlUser() {
        return idvlUser;
    }

    public void setIdvlUser(Long idvlUser) {
        this.idvlUser = idvlUser;
    }

    public Long getIdofflineVideoCourse() {
        return idofflineVideoCourse;
    }

    public void setIdofflineVideoCourse(Long idofflineVideoCourse) {
        this.idofflineVideoCourse = idofflineVideoCourse;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}



	/**
	 * @return the visibleFlag
	 */
	public boolean isVisibleFlag() {
		return visibleFlag;
	}



	/**
	 * @param visibleFlag the visibleFlag to set
	 */
	public void setVisibleFlag(boolean visibleFlag) {
		this.visibleFlag = visibleFlag;
	}



	@Override
	public String toString() {
		return "VideoCourseRatingDTO [topic=" + topic + ", idvlUser=" + idvlUser + ", userName=" + userName
				+ ", schoolName=" + schoolName + ", idofflineVideoCourse=" + idofflineVideoCourse + ", rating=" + rating
				+ ", review=" + review + ", visibleFlag=" + visibleFlag + "]";
	}
	
	
    
    
}
