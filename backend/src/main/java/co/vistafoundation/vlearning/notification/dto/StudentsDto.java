package co.vistafoundation.vlearning.notification.dto;

public class StudentsDto {
	
	private String StudentName;
	
	private Long idStudent;
	
	private Long idUser;
	
	private Long idParent;
	
	private String ParentName;
	
	private Long idParentUser;
	
	
	
	

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

	public String getParentName() {
		return ParentName;
	}

	public void setParentName(String parentName) {
		ParentName = parentName;
	}

	public Long getIdParentUser() {
		return idParentUser;
	}

	public void setIdParentUser(Long idParentUser) {
		this.idParentUser = idParentUser;
	}

	public String getStudentName() {
		return StudentName;
	}

	public void setStudentName(String studentName) {
		StudentName = studentName;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	

}
