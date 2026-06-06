/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class PersonalCoachingBatchListResponseDTO {

	private Teacher teacher;
	private Product product;
	private Batch batch;

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	/**
	 * @param teacher
	 * @param product
	 * @param batch
	 */
	public PersonalCoachingBatchListResponseDTO(Teacher teacher, Product product, Batch batch) {
		super();
		this.teacher = teacher;
		this.product = product;
		this.batch = batch;
	}

	/**
	 * 
	 */
	public PersonalCoachingBatchListResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
