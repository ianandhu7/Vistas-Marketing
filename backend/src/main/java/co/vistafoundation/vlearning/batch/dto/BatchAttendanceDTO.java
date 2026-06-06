package co.vistafoundation.vlearning.batch.dto;

public class BatchAttendanceDTO {
	
	
	private String title;
	private String start;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	
	
	
	
	public BatchAttendanceDTO(String title, String start) {
		super();
		this.title = title;
		this.start = start;
	}
	public BatchAttendanceDTO() {		
		// TODO Auto-generated constructor stub
	}
	
	
	

}
