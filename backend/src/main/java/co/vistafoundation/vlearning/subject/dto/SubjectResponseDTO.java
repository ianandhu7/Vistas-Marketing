package co.vistafoundation.vlearning.subject.dto;

/**
 * 
 * @author NAVEEN
 *
 */

public class SubjectResponseDTO {

	private Long idSubject;

	private String subjectName;

	private String videoURL;

	private String header;

	private String description;

	private String imageURL;

	private String color;
	
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
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the videoURL
	 */
	public String getVideoURL() {
		return videoURL;
	}

	/**
	 * @param videoURL the videoURL to set
	 */
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param idSubject
	 * @param subjectName
	 * @param videoURL
	 * @param header
	 * @param description
	 * @param imageURL
	 * @param color
	 */
	public SubjectResponseDTO(Long idSubject, String subjectName, String videoURL, String header, String description,
			String imageURL, String color) {
		super();
		this.idSubject = idSubject;
		this.subjectName = subjectName;
		this.videoURL = videoURL;
		this.header = header;
		this.description = description;
		this.imageURL = imageURL;
		this.color = color;
	}

	/**
	 * 
	 */
	public SubjectResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
