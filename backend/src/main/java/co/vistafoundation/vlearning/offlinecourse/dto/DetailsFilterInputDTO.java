/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.dto;

/**
 * Test
 */
public class DetailsFilterInputDTO {

	private Long idClassStandard;
	private Long idSubject;
	private Long idState;
	private Long idSyllabus;
	

	private Integer rating;

	private int page;

	private int size;

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

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
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
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
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @param idClassStandard
	 * @param idSubject
	 * @param page
	 * @param size
	 */
	public DetailsFilterInputDTO(Long idClassStandard, Long idSubject, int page, int size) {
		super();
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.page = page;
		this.size = size;
	}

	/**
	 * @param idClassStandard
	 * @param idSubject
	 * @param rating
	 * @param page
	 * @param size
	 */
	public DetailsFilterInputDTO(Long idClassStandard, Long idSubject, Integer rating, int page, int size) {
		super();
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.rating = rating;
		this.page = page;
		this.size = size;
	}

	/**
	 * 
	 */
	public DetailsFilterInputDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
