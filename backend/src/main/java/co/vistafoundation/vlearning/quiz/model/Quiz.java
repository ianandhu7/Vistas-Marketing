package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/* Author Meghana*/

@Entity

@Table(name = "QUIZ" ,uniqueConstraints= @UniqueConstraint(columnNames={"idPRODUCT", "idSUBJECT_CHAPTER","idSUBJECT","idOFFLINE_VIDEO_COURSE"}))

public class Quiz extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idQUIZ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idQuiz;

	@Column(name = "idSUBJECT_CHAPTER", nullable = false)
	private Long idSubjectChapter;

	@Column(name = "idSUBJECT" , nullable = false)
	private Long idSubject;
	
	@Column(name ="idPRODUCT", nullable = false)
	private Long idProduct;
	
	@Column(name ="QUIZ_NAME", nullable = false)
	private String quizName;
	
	@Column(name="idOFFLINE_VIDEO_COURSE")
	private Long idOfflineVideoCourse;


	public Long getIdQuiz() {
		return idQuiz;
	}

	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}

	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	public Quiz(Long idSubjectChapter, Long idSubject, Long idProduct, String quizName, 
			Long idOfflineVideoCourse) {
		super();
		this.idSubjectChapter = idSubjectChapter;
		this.idSubject = idSubject;
		this.idProduct = idProduct;
		this.quizName = quizName;
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	public Quiz() {
		super();
		
	}
	

}