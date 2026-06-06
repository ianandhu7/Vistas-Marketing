/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;
import java.util.List;

/**
 * @author Naveen Kumar
 *
 */
public class SubjectWiseReportCardDTO {

	private Long userId;

	private Instant reportGenerationDate;

	private Float grandTotalMarks;

	private Float grandTotalMarksObtained;

	private String overAllPercentage;

	private String studentName;

	private String classStandard;

	private String state;

	private String syllabus;

	private List<SubjectWiseReportDTO> subjectList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Instant getReportGenerationDate() {
		return reportGenerationDate;
	}

	public void setReportGenerationDate(Instant reportGenerationDate) {
		this.reportGenerationDate = reportGenerationDate;
	}

	public Float getGrandTotalMarks() {
		return grandTotalMarks;
	}

	public void setGrandTotalMarks(Float grandTotalMarks) {
		this.grandTotalMarks = grandTotalMarks;
	}

	public Float getGrandTotalMarksObtained() {
		return grandTotalMarksObtained;
	}

	public void setGrandTotalMarksObtained(Float grandTotalMarksObtained) {
		this.grandTotalMarksObtained = grandTotalMarksObtained;
	}

	public String getOverAllPercentage() {
		return overAllPercentage;
	}

	public void setOverAllPercentage(String overAllPercentage) {
		this.overAllPercentage = overAllPercentage;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public List<SubjectWiseReportDTO> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<SubjectWiseReportDTO> subjectList) {
		this.subjectList = subjectList;
	}

	public SubjectWiseReportCardDTO(Long userId, Instant reportGenerationDate, Float grandTotalMarks,
			Float grandTotalMarksObtained, String overAllPercentage, String studentName, String classStandard,
			String state, String syllabus, List<SubjectWiseReportDTO> subjectList) {
		super();
		this.userId = userId;
		this.reportGenerationDate = reportGenerationDate;
		this.grandTotalMarks = grandTotalMarks;
		this.grandTotalMarksObtained = grandTotalMarksObtained;
		this.overAllPercentage = overAllPercentage;
		this.studentName = studentName;
		this.classStandard = classStandard;
		this.state = state;
		this.syllabus = syllabus;
		this.subjectList = subjectList;
	}

	public SubjectWiseReportCardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
