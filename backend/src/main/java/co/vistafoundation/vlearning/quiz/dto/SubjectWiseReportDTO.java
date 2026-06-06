/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

/**
 * @author Naveen Kumar
 *
 */
public class SubjectWiseReportDTO {

	private Long idQuiz;

	private Long idStudentSubscr;

	private Long idStudentSubjectQuiz;

	private String subjectName;

	private int totalMarks;

	private Float securedMarks;

	private String securedPercentage;

	private String color;

	private String imageUrl;

	private Long idProduct;
	
	private String remarks;

	public Long getIdQuiz() {
		return idQuiz;
	}

	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}

	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}

	public Long getIdStudentSubjectQuiz() {
		return idStudentSubjectQuiz;
	}

	public void setIdStudentSubjectQuiz(Long idStudentSubjectQuiz) {
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Float getSecuredMarks() {
		return securedMarks;
	}

	public void setSecuredMarks(Float securedMarks) {
		this.securedMarks = securedMarks;
	}

	public String getSecuredPercentage() {
		return securedPercentage;
	}

	public void setSecuredPercentage(String securedPercentage) {
		this.securedPercentage = securedPercentage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public SubjectWiseReportDTO(Long idQuiz, Long idStudentSubscr, Long idStudentSubjectQuiz, String subjectName,
			int totalMarks, Float securedMarks, String securedPercentage, String color, String imageUrl,
			Long idProduct) {
		super();
		this.idQuiz = idQuiz;
		this.idStudentSubscr = idStudentSubscr;
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
		this.subjectName = subjectName;
		this.totalMarks = totalMarks;
		this.securedMarks = securedMarks;
		this.securedPercentage = securedPercentage;
		this.color = color;
		this.imageUrl = imageUrl;
		this.idProduct = idProduct;
	}

	public SubjectWiseReportDTO() {
		super();

	}

}
