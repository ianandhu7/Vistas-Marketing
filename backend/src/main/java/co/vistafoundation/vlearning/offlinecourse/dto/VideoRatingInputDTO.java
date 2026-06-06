/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

/**
 *@author Abdul Elahi
 */
public class VideoRatingInputDTO {

	    private Long idOfflineVideoCourse;

	    private int rating;

	    private String review;

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
		 * @return the rating
		 */
		public int getRating() {
			return rating;
		}

		/**
		 * @param rating the rating to set
		 */
		public void setRating(int rating) {
			this.rating = rating;
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
		 * @param idOfflineVideoCourse
		 * @param rating
		 * @param review
		 */
		public VideoRatingInputDTO(Long idOfflineVideoCourse, int rating, String review) {
			super();
			this.idOfflineVideoCourse = idOfflineVideoCourse;
			this.rating = rating;
			this.review = review;
		}

		/**
		 * 
		 */
		public VideoRatingInputDTO() {
			super();
		}

		@Override
		public String toString() {
			return "VideoRatingInputDTO [idOfflineVideoCourse=" + idOfflineVideoCourse + ", rating=" + rating
					+ ", review=" + review + "]";
		}
	    
	    
	
}
