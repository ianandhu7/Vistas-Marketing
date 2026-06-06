/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

/**
 * @author NaveenKumar
 *
 */
public class VCTQuizOverAllReviewDTO {
	
	private String questionType;
	
	private String questionTitle;
	
	private int  noOfQuestions;
	
	private int totalMarks;
	
	private int correctAnswer;
	
	private float totalMarksScored;
	
	private int mark;
	
	

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	
	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public float getTotalMarksScored() {
		return totalMarksScored;
	}

	public void setTotalMarksScored(float totalMarksScored) {
		this.totalMarksScored = totalMarksScored;
	}

	
	
	public int getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	

	public VCTQuizOverAllReviewDTO(String questionType, String questionTitle, int noOfQuestions, int totalMarks,
			int correctAnswer, float totalMarksScored, int mark) {
		super();
		this.questionType = questionType;
		this.questionTitle = questionTitle;
		this.noOfQuestions = noOfQuestions;
		this.totalMarks = totalMarks;
		this.correctAnswer = correctAnswer;
		this.totalMarksScored = totalMarksScored;
		this.mark = mark;
	}

	public VCTQuizOverAllReviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
 
}
