package co.vistafoundation.vlearning.batch.dto;

import java.util.List;

import co.vistafoundation.vlearning.batch.model.BatchCalender;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.subject.model.Subject;

public class TeacherBatchDetailsDTO {

	private Long idBatch;
	private String batchName;
	private ClassStandard classStandard;
	private Subject subject;
	private List<BatchCalender> batchCalender;	
	
	
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public Long getIdBatch() {
		return idBatch;
	}
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
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
	public List<BatchCalender> getBatchCalender() {
		return batchCalender;
	}
	public void setBatchCalender(List<BatchCalender> batchCalender) {
		this.batchCalender = batchCalender;
	}
	
	
}
