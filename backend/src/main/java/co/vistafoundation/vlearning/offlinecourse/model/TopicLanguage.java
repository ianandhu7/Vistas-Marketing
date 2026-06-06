/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "TOPIC_LANGUAGE")
public class TopicLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idTOPIC_LANGUAGE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTopicLanguage;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idOFFLINE_VIDEO_COURSE", referencedColumnName = "idOFFLINE_VIDEO_COURSE", nullable = false)
	private OfflineVideoCourse offlineVideoCourse;

	@Column(name = "idLANGUAGE", nullable = false)
	private Long idLanguage;

	@Column(name = "HEADING", length = 100)
	private String heading;

	@Column(name = "QUESTION", length = 500)
	private String question;

	@Column(name = "ANSWER", length = 1500)
	private String answer;
	
	@Column(name = "PDF_URL", length = 500)
	private String pdfURL;

	/**
	 * @return the idTopicLanguage
	 */
	public Long getIdTopicLanguage() {
		return idTopicLanguage;
	}

	/**
	 * @param idTopicLanguage the idTopicLanguage to set
	 */
	public void setIdTopicLanguage(Long idTopicLanguage) {
		this.idTopicLanguage = idTopicLanguage;
	}

	/**
	 * @return the offlineVideoCourse
	 */
	public OfflineVideoCourse getOfflineVideoCourse() {
		return offlineVideoCourse;
	}

	/**
	 * @param offlineVideoCourse the offlineVideoCourse to set
	 */
	public void setOfflineVideoCourse(OfflineVideoCourse offlineVideoCourse) {
		this.offlineVideoCourse = offlineVideoCourse;
	}

	/**
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	/**
	 * 
	 */
	public TopicLanguage() {
		super();
	
	}

	/**
	 * @return the pdfURL
	 */
	public String getPdfURL() {
		return pdfURL;
	}

	/**
	 * @param pdfURL the pdfURL to set
	 */
	public void setPdfURL(String pdfURL) {
		this.pdfURL = pdfURL;
	}

	/**
	 * @param offlineVideoCourse
	 * @param idLanguage
	 * @param heading
	 * @param question
	 * @param answer
	 * @param pdfURL
	 */
	public TopicLanguage(OfflineVideoCourse offlineVideoCourse, Long idLanguage, String heading, String question,
			String answer, String pdfURL) {
		super();
		this.offlineVideoCourse = offlineVideoCourse;
		this.idLanguage = idLanguage;
		this.heading = heading;
		this.question = question;
		this.answer = answer;
		this.pdfURL = pdfURL;
	}

	/**
	 * @return the pdfUrl
	 */

	
	
	

}
