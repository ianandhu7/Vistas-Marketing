/**
 * 
 */
package co.vistafoundation.vlearning.vct.dto;

import java.util.List;

/**
 * @author naveenkumar
 *
 */
public class VCTSubjectDTO {
	
	private String subjectName;

	private Long idSubject;

	private String color;
	
	private List<String> questionSet;
	
	private String imageURL;
	
	private int totalMarks;
	
	private Long idProduct;
	

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<String> getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(List<String> questionSet) {
		this.questionSet = questionSet;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	

	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}




	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
	

	public VCTSubjectDTO(String subjectName, Long idSubject, String color, List<String> questionSet, String imageURL,
			int totalMarks, Long idProduct) {
		super();
		this.subjectName = subjectName;
		this.idSubject = idSubject;
		this.color = color;
		this.questionSet = questionSet;
		this.imageURL = imageURL;
		this.totalMarks = totalMarks;
		this.idProduct = idProduct;
	}

	public VCTSubjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
