/**
 * 
 */
package co.vistafoundation.vlearning.webex.model;

import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class WebExAssignedTeacherDTO {

	private Teacher teacher;
	private WebExPool pool;

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
	 * @return the pool
	 */
	public WebExPool getPool() {
		return pool;
	}

	/**
	 * @param pool the pool to set
	 */
	public void setPool(WebExPool pool) {
		this.pool = pool;
	}

	/**
	 * @param teacher
	 * @param pool
	 */
	public WebExAssignedTeacherDTO(Teacher teacher, WebExPool pool) {
		super();
		this.teacher = teacher;
		this.pool = pool;
	}

	/**
	 * 
	 */
	public WebExAssignedTeacherDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
