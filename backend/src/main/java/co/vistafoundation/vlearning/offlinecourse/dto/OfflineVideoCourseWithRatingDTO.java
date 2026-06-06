/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;

/**
 * @author Abdul Elahi
 */
public class OfflineVideoCourseWithRatingDTO {

	private OfflineVideoCourse offlineCourse;
	
	private Double rating;
	
	private long totalRating;

	/**
	 * @return the offlineCourse
	 */
	public OfflineVideoCourse getOfflineCourse() {
		return offlineCourse;
	}

	/**
	 * @param offlineCourse the offlineCourse to set
	 */
	public void setOfflineCourse(OfflineVideoCourse offlineCourse) {
		this.offlineCourse = offlineCourse;
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
	 * @return the totalRating
	 */
	public long getTotalRating() {
		return totalRating;
	}

	/**
	 * @param totalRating the totalRating to set
	 */
	public void setTotalRating(long totalRating) {
		this.totalRating = totalRating;
	}

	/**
	 * @param offlineCourse
	 * @param rating
	 */
	public OfflineVideoCourseWithRatingDTO(OfflineVideoCourse offlineCourse, Double rating) {
		super();
		this.offlineCourse = offlineCourse;
		this.rating = rating;
	}

	/**
	 * 
	 */
	public OfflineVideoCourseWithRatingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OfflineVideoCourseWithRatingDTO [offlineCourse=" + offlineCourse + ", rating=" + rating + "]";
	}
	
	
	
	
}
