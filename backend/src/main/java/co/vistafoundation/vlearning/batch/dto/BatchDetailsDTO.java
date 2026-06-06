package co.vistafoundation.vlearning.batch.dto;

import java.util.List;

public class BatchDetailsDTO {
	
	private String teacherName;
	
	private String teacherEmailId;
	
	private String teacherMobileNumber;

	private String teacherExpLevel;

	private int teacherRating;
	
	private String teacherImage;
	
	private List<BatchDTO> batchList;
	
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherEmailId() {
		return teacherEmailId;
	}

	public void setTeacherEmailId(String teacherEmailId) {
		this.teacherEmailId = teacherEmailId;
	}

	public String getTeacherMobileNumber() {
		return teacherMobileNumber;
	}

	public void setTeacherMobileNumber(String teacherMobileNumber) {
		this.teacherMobileNumber = teacherMobileNumber;
	}

	public String getTeacherExpLevel() {
		return teacherExpLevel;
	}

	public void setTeacherExpLevel(String teacherExpLevel) {
		this.teacherExpLevel = teacherExpLevel;
	}

	public int getTeacherRating() {
		return teacherRating;
	}

	public void setTeacherRating(int teacherRating) {
		this.teacherRating = teacherRating;
	}

	public String getTeacherImage() {
		return teacherImage;
	}

	public void setTeacherImage(String teacherImage) {
		this.teacherImage = teacherImage;
	}

	public List<BatchDTO> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<BatchDTO> batchList) {
		this.batchList = batchList;
	}
}
