package co.vistafoundation.vlearning.liveclass.dto;

public class LiveClassQuestionAnswerRequestDTO {

	private Long idLiveClass;
	private Long userSurId;
	private String question;
	private String answer;
	
	public Long getIdLiveClass() {
		return idLiveClass;
	}
	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
	}
	public Long getUserSurId() {
		return userSurId;
	}
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
