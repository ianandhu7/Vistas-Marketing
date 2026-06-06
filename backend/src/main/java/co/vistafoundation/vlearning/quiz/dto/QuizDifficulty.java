/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

/**
 * @author NAVEEN
 *
 */
public class QuizDifficulty {

	
	Long idProductGroup;
	
	Long idProduct;
	
	String category;
	
	String product_name;
	
	Long idStudentSubscription;
	
	Boolean attempFlag;
	
	
	
	/**
	 * @return the idProductGroup
	 */
	public Long getIdProductGroup() {
		return idProductGroup;
	}



	/**
	 * @param idProductGroup the idProductGroup to set
	 */
	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}



	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}



	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}



	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}



	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}



	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}



	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}



	/**
	 * @return the idStudentSubscription
	 */
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}



	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}



	/**
	 * @return the attempFlag
	 */
	public Boolean getAttempFlag() {
		return attempFlag;
	}



	/**
	 * @param attempFlag the attempFlag to set
	 */
	public void setAttempFlag(Boolean attempFlag) {
		this.attempFlag = attempFlag;
	}


	

	/**
	 * @param idProductGroup
	 * @param idProduct
	 * @param category
	 * @param product_name
	 * @param idStudentSubscription
	 * @param attempFlag
	 */
	public QuizDifficulty(Long idProductGroup, Long idProduct, String category, String product_name,
			Long idStudentSubscription, Boolean attempFlag) {
		super();
		this.idProductGroup = idProductGroup;
		this.idProduct = idProduct;
		this.category = category;
		this.product_name = product_name;
		this.idStudentSubscription = idStudentSubscription;
		this.attempFlag = attempFlag;
	}



	public QuizDifficulty() {
		super();
		// TODO Auto-generated constructor stub
	}

}
