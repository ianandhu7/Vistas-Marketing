/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;

/**
 * @author Ahmed
 *
 */
public class TelecallerDemoClassDTO {

	private DemoClassExtraCurricular demoClassExtraCurricular;

	private String teacherName;

	/**
	 * @return the demoClassExtraCurricular
	 */
	public DemoClassExtraCurricular getDemoClassExtraCurricular() {
		return demoClassExtraCurricular;
	}

	/**
	 * @param demoClassExtraCurricular the demoClassExtraCurricular to set
	 */
	public void setDemoClassExtraCurricular(DemoClassExtraCurricular demoClassExtraCurricular) {
		this.demoClassExtraCurricular = demoClassExtraCurricular;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @param demoClassExtraCurricular
	 * @param teacherName
	 */
	public TelecallerDemoClassDTO(DemoClassExtraCurricular demoClassExtraCurricular, String teacherName) {
		super();
		this.demoClassExtraCurricular = demoClassExtraCurricular;
		this.teacherName = teacherName;
	}

	/**
	 * 
	 */
	public TelecallerDemoClassDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
