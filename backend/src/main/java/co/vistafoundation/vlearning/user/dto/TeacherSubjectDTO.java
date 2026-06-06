package co.vistafoundation.vlearning.user.dto;

import java.io.Serializable;

public class TeacherSubjectDTO  implements Serializable{
	
	private static final long serialVersionUID = -1346455314723210126L;
	private String proficiency;
	private Long idSubject;
	private String subjectName;
	private Long idSyllabus;
	private String syllabusName;
	private String subIntroVideo;
	
	
	
	public String getSubIntroVideo() {
		return subIntroVideo;
	}
	public void setSubIntroVideo(String subIntroVideo) {
		this.subIntroVideo = subIntroVideo;
	}
	public String getProficiency() {
		return proficiency;
	}
	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getIdSyllabus() {
		return idSyllabus;
	}
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}
	public String getSyllabusName() {
		return syllabusName;
	}
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	
	
	

}


