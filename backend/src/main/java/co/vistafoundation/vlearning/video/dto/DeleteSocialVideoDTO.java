/**
 * 
 */
package co.vistafoundation.vlearning.video.dto;

import javax.validation.constraints.NotNull;

/**
 * @author NAVEEN
 *
 */
public class DeleteSocialVideoDTO {
	
	@NotNull
	String message;
	
	@NotNull
	Long idSocialVideo;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the idSocialVideo
	 */
	public Long getIdSocialVideo() {
		return idSocialVideo;
	}

	/**
	 * @param idSocialVideo the idSocialVideo to set
	 */
	public void setIdSocialVideo(Long idSocialVideo) {
		this.idSocialVideo = idSocialVideo;
	}
	
	
	

}
