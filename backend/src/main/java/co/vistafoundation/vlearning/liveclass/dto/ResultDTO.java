package co.vistafoundation.vlearning.liveclass.dto;




public class ResultDTO{

	private String classConductedDate;
	private String totalLiveClasses;
	private String studentAttendedCount;
	
	public ResultDTO() {
	}

	public String getClassConductedDate() {
		return classConductedDate;
	}

	public void setClassConductedDate(String classConductedDate) {
		this.classConductedDate = classConductedDate;
	}

	public String getTotalLiveClasses() {
		return totalLiveClasses;
	}

	public void setTotalLiveClasses(String totalLiveClasses) {
		this.totalLiveClasses = totalLiveClasses;
	}

	public String getStudentAttendedCount() {
		return studentAttendedCount;
	}

	public void setStudentAttendedCount(String studentAttendedCount) {
		this.studentAttendedCount = studentAttendedCount;
	}
	
	/*public ResultDTO(Date classConductedDate, Long totalLiveClasses, Long studentAttendedCount) {
		this.classConductedDate = classConductedDate;
		this.totalLiveClasses = totalLiveClasses;
		this.studentAttendedCount = studentAttendedCount;
	}*/
	
	
	
	
}
