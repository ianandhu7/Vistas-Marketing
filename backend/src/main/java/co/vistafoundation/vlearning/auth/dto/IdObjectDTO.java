package co.vistafoundation.vlearning.auth.dto;

public class IdObjectDTO {
	
	private Long idStudent;
	private Long idParent;	
	private Long idTeacher;
	public Long getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public Long getIdParent() {
		return idParent;
	}
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
	public Long getIdTeacher() {
		return idTeacher;
	}
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}
	public IdObjectDTO(Long idStudent, Long idParent, Long idTeacher) {
		super();
		this.idStudent = idStudent;
		this.idParent = idParent;
		this.idTeacher = idTeacher;
	}
	
	public IdObjectDTO() {
		
	}

}
