package co.vistafoundation.vlearning.ticket.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


@Entity
@Table(name = "ticket")
public class Ticket  implements Serializable {

	private static final long serialVersionUID = 5274754025175257343L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ticket")
	private Integer idTicket;

	@Column(name = "idsubject_chapter", nullable = false)
	private Integer idSubjectChapter;
	
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

	@Column(name = "video_en_link", length = 50)
	private String videoEnLink;

	@Column(name = "idissue_category", nullable = false)
	private Integer idIssueCategory;

	@Column(name = "comment", length = 1500)
	private String comment;

	@Column(name = "status", length = 20)
	private String status = "OPEN"; // Default value

	@Column(name = "ID_VL_USER")
	private Long idVlUser;

	// Constructors
	public Ticket() {
	}



	public Ticket(Integer idTicket, Integer idSubjectChapter, LocalDateTime createdAt, LocalDateTime updatedAt,
			String videoEnLink, Integer idIssueCategory, String comment, String status, Long idVlUser) {
		super();
		this.idTicket = idTicket;
		this.idSubjectChapter = idSubjectChapter;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.videoEnLink = videoEnLink;
		this.idIssueCategory = idIssueCategory;
		this.comment = comment;
		this.status = status;
		this.idVlUser = idVlUser;
	}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


	// Getters and Setters
	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
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

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
}
