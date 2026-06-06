package co.vistafoundation.vlearning.batch.dto;

import java.util.List;

import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author NaveenKumar
 * 
 **/

public class BatchMetaDataDTO {

	private List<ClassStandard> classList;

	private List<Subject> subjectList;

	private List<ProductLine> batchList;

	private List<Teacher> TeacherList;

	private List<DayOfWeekCode> dayofweek;

	public List<DayOfWeekCode> getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(List<DayOfWeekCode> dayofweek) {
		this.dayofweek = dayofweek;
	}

	public List<Teacher> getTeacherList() {
		return TeacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		TeacherList = teacherList;
	}

	public List<ClassStandard> getClassList() {
		return classList;
	}

	public void setClassList(List<ClassStandard> classList) {
		this.classList = classList;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public List<ProductLine> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<ProductLine> batchList) {
		this.batchList = batchList;
	}

}
