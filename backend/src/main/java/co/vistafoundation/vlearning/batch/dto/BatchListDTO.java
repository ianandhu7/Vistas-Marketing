/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class BatchListDTO {

	private Teacher teacher;
	private Product product;
	private Batch batch;
	private ClassStandard classStandard;
	private Subject subject; 

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public ClassStandard getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(ClassStandard classStandard) {
		this.classStandard = classStandard;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public BatchListDTO(Teacher teacher, Product product, Batch batch, ClassStandard classStandard, Subject subject) {
		super();
		this.teacher = teacher;
		this.product = product;
		this.batch = batch;
		this.classStandard = classStandard;
		this.subject = subject;
	}

	public BatchListDTO() {

	}

}
