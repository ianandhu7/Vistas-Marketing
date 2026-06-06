package co.vistafoundation.vlearning.offlinecourse.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import co.vistafoundation.vlearning.audit.model.DateAudit;

/**
 * @author Abdul Elahi
 */
@Entity
public class OfflineVideoCourseRating extends DateAudit{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idoffline_video_course_rating")
    private Long idofflineVideoCourseRating;

    @Column(name = "id_offline_video_course", nullable = false)
    private Long idOfflineVideoCourse;

    @Column(name = "idvl_user", nullable = false)
    private Long userSurId;

    @Column(name = "rating", nullable = false, columnDefinition = "int default 0")
    private int rating;

    @Column(name = "review")
    private String review;
    
    @Column(name = "visible_flag")
    private Boolean visibleFlag;
    
    
    
	/**
	 * @return the visibleFlag
	 */
	public Boolean getVisibleFlag() {
		return visibleFlag;
	}

	/**
	 * @param visibleFlag the visibleFlag to set
	 */
	public void setVisibleFlag(Boolean visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Long getIdofflineVideoCourseRating() {
        return idofflineVideoCourseRating;
    }

    public void setIdofflineVideoCourseRating(Long idofflineVideoCourseRating) {
        this.idofflineVideoCourseRating = idofflineVideoCourseRating;
    }

    public Long getIdOfflineVideoCourse() {
        return idOfflineVideoCourse;
    }

    public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
        this.idOfflineVideoCourse = idOfflineVideoCourse;
    }

    public Long getUserSurId() {
        return userSurId;
    }

    public void setUserSurId(Long userSurId) {
        this.userSurId = userSurId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public OfflineVideoCourseRating() {
    }
    
    public OfflineVideoCourseRating(Long idOfflineVideoCourse, Long userSurId, int rating, String review) {
    	this.idOfflineVideoCourse = idOfflineVideoCourse;
    	this.userSurId = userSurId;
    	this.rating = rating;
    	this.review = review;
    }
}
