package co.vistafoundation.vlearning.user.dto;

public class LiveUserFilterDTO {

	private String type;
	private Long idClasssStandard;
	private Long idState;
	private Long idSyllabus;
	private Long userSurID;
	private String userName;
	private String schoolName;
	private int size;
	private int page;

	// Default constructor
	public LiveUserFilterDTO() {
	}

	// Parameterized constructor
	public LiveUserFilterDTO(String type, Long idClasssStandard, Long idState, Long idSyllabus, Long userSurID,
			String userName, String schoolName, int size, int page) {
		this.type = type;
		this.idClasssStandard = idClasssStandard;
		this.idState = idState;
		this.idSyllabus = idSyllabus;
		this.userSurID = userSurID;
		this.userName = userName;
		this.schoolName = schoolName;
		this.size = size;
		this.page = page;
	}

	// Getters and Setters
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getIdClasssStandard() {
		return idClasssStandard;
	}

	public void setIdClasssStandard(Long idClasssStandard) {
		this.idClasssStandard = idClasssStandard;
	}

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Long getUserSurID() {
		return userSurID;
	}

	public void setUserSurID(Long userSurID) {
		this.userSurID = userSurID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	// toString method
	@Override
	public String toString() {
		return "LiveUserFilterDTO{" + "type='" + type + '\'' + ", idClasssStandard=" + idClasssStandard + ", idState="
				+ idState + ", idSyllabus=" + idSyllabus + ", userSurID=" + userSurID + ", userName='" + userName + '\''
				+ ", schoolName='" + schoolName + '\'' + ", size=" + size + ", page=" + page + '}';
	}
}
