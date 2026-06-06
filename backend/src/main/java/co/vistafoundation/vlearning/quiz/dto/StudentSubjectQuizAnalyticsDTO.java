package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;

public class StudentSubjectQuizAnalyticsDTO {

	
	private Instant date;
	private Long count;

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}


	/**
	 * @param date
	 * @param count
	 */
	public StudentSubjectQuizAnalyticsDTO(Instant date, Long count) {
		super();
		this.date = date;
		this.count = count;
	}

	/**
	 * @return the date
	 */
	public Instant getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Instant date) {
		this.date = date;
	}

	/**
	 * 
	 */
	public StudentSubjectQuizAnalyticsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "StudentSubjectQuizAnalyticsDTO [count=" + count + "]";
	}
	
}
