/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

/**
 * @author NaveenKumar
 *
 */
public class StudentVCTMarksDTO {

	private Float totalMarks;
	
	private Float totalMarkeScored;
	
	private Float percentageFloat;
	
	private Long userSurId;
	
	private Boolean qualiefiedForGlobalRanking;

	public Float getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Float totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Float getTotalMarkeScored() {
		return totalMarkeScored;
	}

	public void setTotalMarkeScored(Float totalMarkeScored) {
		this.totalMarkeScored = totalMarkeScored;
	}

	public Float getPercentageFloat() {
		return percentageFloat;
	}

	public void setPercentageFloat(Float percentageFloat) {
		this.percentageFloat = percentageFloat;
	}

	
	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	public StudentVCTMarksDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Boolean getQualiefiedForGlobalRanking() {
		return qualiefiedForGlobalRanking;
	}

	public void setQualiefiedForGlobalRanking(Boolean qualiefiedForGlobalRanking) {
		this.qualiefiedForGlobalRanking = qualiefiedForGlobalRanking;
	}

	public StudentVCTMarksDTO(Float totalMarks, Float totalMarkeScored, Float percentageFloat, Long userSurId) {
		super();
		this.totalMarks = totalMarks;
		this.totalMarkeScored = totalMarkeScored;
		this.percentageFloat = percentageFloat;
		this.userSurId = userSurId;
	}

	@Override
	public String toString() {
		return "StudentVCTMarksDTO [totalMarks=" + totalMarks + ", totalMarkeScored=" + totalMarkeScored
				+ ", percentageFloat=" + percentageFloat + ", userSurId=" + userSurId + "]";
	}

	
	
	
	
	

}
