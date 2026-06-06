package co.vistafoundation.vlearning.user.dto;

public class StudentDTO {

	private Long idStudent;
	private String firstName;
	private String lastName;
	public Long getIdStudent() {
		return idStudent;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public StudentDTO(Long idStudent, String firstName, String lastName) {
		super();
		this.idStudent = idStudent;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public StudentDTO() {
		
	}
}
