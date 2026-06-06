/**
 * 
 */
package co.vistafoundation.vlearning.faq.dto;

/**
 * @author NAVEEN
 *
 */
public class FaqObjectAnswer {

	private String answer;

	private String imageLink;

	private String videoLink;

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
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}

	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	/**
	 * @return the videoLink
	 */
	public String getVideoLink() {
		return videoLink;
	}

	/**
	 * @param videoLink the videoLink to set
	 */
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	/**
	 * @param answer
	 * @param imageLink
	 * @param videoLink
	 */
	public FaqObjectAnswer(String answer, String imageLink, String videoLink) {
		super();
		this.answer = answer;
		this.imageLink = imageLink;
		this.videoLink = videoLink;
	}

	/**
	 * 
	 */
	public FaqObjectAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
}
