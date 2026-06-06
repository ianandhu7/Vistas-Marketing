package co.vistafoundation.vlearning.ticket.dto;

public class TicketDTO {

	private Integer idSubjectChapter;
	private String videoEnLink;
	private Integer idIssueCategory;
	private String comment;
	private String status = "OPEN"; // Default value

	public TicketDTO() {

	}

	public TicketDTO(Integer idSubjectChapter, String videoEnLink, Integer idIssueCategory, String comment, String status) {
		super();
		this.idSubjectChapter = idSubjectChapter;
		this.videoEnLink = videoEnLink;
		this.idIssueCategory = idIssueCategory;
		this.comment = comment;
		this.status = status;
	}

	public Integer getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Integer idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	public String getVideoEnLink() {
		return videoEnLink;
	}

	public void setVideoEnLink(String videoEnLink) {
		this.videoEnLink = videoEnLink;
	}

	public Integer getIdIssueCategory() {
		return idIssueCategory;
	}

	public void setIdIssueCategory(Integer idIssueCategory) {
		this.idIssueCategory = idIssueCategory;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
