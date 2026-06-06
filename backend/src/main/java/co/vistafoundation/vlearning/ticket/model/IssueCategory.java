package co.vistafoundation.vlearning.ticket.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issue_category")
public class IssueCategory  implements Serializable{

    private static final long serialVersionUID = 358456142157422836L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idissue_category")
    private Integer idIssueCategory;

    @Column(name = "issue_category", length = 50, nullable = false)
    private String issueCategory;

    @Column(name = "status", nullable = false)
    private Boolean status = true; // Default value

    // Constructors
    public IssueCategory() {
    }

    public IssueCategory(String issueCategory, Boolean status) {
        this.issueCategory = issueCategory;
        this.status = status;
    }

    // Getters and Setters
    public Integer getIdIssueCategory() {
        return idIssueCategory;
    }

    public void setIdIssueCategory(Integer idIssueCategory) {
        this.idIssueCategory = idIssueCategory;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}