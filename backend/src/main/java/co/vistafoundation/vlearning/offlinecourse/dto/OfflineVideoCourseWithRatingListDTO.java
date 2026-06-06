/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

import java.util.List;

public class OfflineVideoCourseWithRatingListDTO {
	
	List<OfflineVideoCourseWithRatingDTO> offlineVideoCourseWithRating;
	
	long totalRatings;

	/**
	 * @return the offlineVideoCourseWithRatingDTOs
	 */
	public List<OfflineVideoCourseWithRatingDTO> getOfflineVideoCourseWithRating() {
		return offlineVideoCourseWithRating;
	}

	/**
	 * @param offlineVideoCourseWithRatingDTOs the offlineVideoCourseWithRatingDTOs to set
	 */
	public void setOfflineVideoCourseWithRating(
			List<OfflineVideoCourseWithRatingDTO> offlineVideoCourseWithRatingDTOs) {
		this.offlineVideoCourseWithRating = offlineVideoCourseWithRatingDTOs;
	}

	/**
	 * @return the totalRatings
	 */
	public long getTotalRatings() {
		return totalRatings;
	}

	/**
	 * @param totalRatings the totalRatings to set
	 */
	public void setTotalRatings(long totalRatings) {
		this.totalRatings = totalRatings;
	}

	/**
	 * 
	 */
	public OfflineVideoCourseWithRatingListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param offlineVideoCourseWithRatingDTOs
	 * @param totalRatings
	 */
	public OfflineVideoCourseWithRatingListDTO(List<OfflineVideoCourseWithRatingDTO> offlineVideoCourseWithRatingDTOs,
			long totalRatings) {
		super();
		this.offlineVideoCourseWithRating = offlineVideoCourseWithRatingDTOs;
		this.totalRatings = totalRatings;
	}
	
}
