package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;

public class QuizScoreResponseDTO {

	 private Long userSurID;
	    private String name;
	    private String classStandard;
	    private String state;
	    private String syllabus;
	    private String schoolName;
	    private String subject;
	    private String chapter;
	    private Instant quizTaken;
	    private Float quizScore;
	    private Long idClassStandard;
	    private Long idSyllabus;
	    private Long idState;

	    // Getters and Setters
	    public Long getUserSurID() {
	        return userSurID;
	    }

	    public void setUserSurID(Long userSurID) {
	        this.userSurID = userSurID;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getClassStandard() {
	        return classStandard;
	    }

	    public void setClassStandard(String classStandard) {
	        this.classStandard = classStandard;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public String getSyllabus() {
	        return syllabus;
	    }

	    public void setSyllabus(String syllabus) {
	        this.syllabus = syllabus;
	    }

	    public String getSchoolName() {
	        return schoolName;
	    }

	    public void setSchoolName(String schoolName) {
	        this.schoolName = schoolName;
	    }

	    public String getSubject() {
	        return subject;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public String getChapter() {
	        return chapter;
	    }

	    public void setChapter(String chapter) {
	        this.chapter = chapter;
	    }

	    public Instant getQuizTaken() {
	        return quizTaken;
	    }

	    public void setQuizTaken(Instant quizTaken) {
	        this.quizTaken = quizTaken;
	    }

	    public Float getQuizScore() {
	        return quizScore;
	    }

	    public void setQuizScore(Float quizScore) {
	        this.quizScore = quizScore;
	    }
	    

		/**
		 * @return the idClassStandard
		 */
		public Long getIdClassStandard() {
			return idClassStandard;
		}

		/**
		 * @param idClassStandard the idClassStandard to set
		 */
		public void setIdClassStandard(Long idClassStandard) {
			this.idClassStandard = idClassStandard;
		}

		/**
		 * @return the idSyllabus
		 */
		public Long getIdSyllabus() {
			return idSyllabus;
		}

		/**
		 * @param idSyllabus the idSyllabus to set
		 */
		public void setIdSyllabus(Long idSyllabus) {
			this.idSyllabus = idSyllabus;
		}

		/**
		 * @return the idState
		 */
		public Long getIdState() {
			return idState;
		}

		/**
		 * @param idState the idState to set
		 */
		public void setIdState(Long idState) {
			this.idState = idState;
		}

		/**
		 * @param userSurID
		 * @param name
		 * @param classStandard
		 * @param state
		 * @param syllabus
		 * @param schoolName
		 * @param subject
		 * @param chapter
		 * @param quizTaken
		 * @param quizScore
		 */
		public QuizScoreResponseDTO(Long userSurID, String name, String classStandard, String state, String syllabus,
				String schoolName, String subject, String chapter, Instant quizTaken, Float quizScore) {
			super();
			this.userSurID = userSurID;
			this.name = name;
			this.classStandard = classStandard;
			this.state = state;
			this.syllabus = syllabus;
			this.schoolName = schoolName;
			this.subject = subject;
			this.chapter = chapter;
			this.quizTaken = quizTaken;
			this.quizScore = quizScore;
		}
		

		/**
		 * @param userSurID
		 * @param name
		 * @param classStandard
		 * @param state
		 * @param syllabus
		 * @param schoolName
		 * @param subject
		 * @param quizTaken
		 * @param quizScore
		 
		(u.userSurId, u.firstName,"
				+ "c.classStandadName,st.state, "
				+ "sy.syllabusName,s.schoolName,su.subjectName, ssq.createdAt,ssq.quizScore )"
		*/public QuizScoreResponseDTO(Long userSurID, String name, String classStandard, String state, String syllabus,
				String schoolName, String subject, Instant quizTaken, Float quizScore) {
			super();
			this.userSurID = userSurID;
			this.name = name;
			this.classStandard = classStandard;
			this.state = state;
			this.syllabus = syllabus;
			this.schoolName = schoolName;
			this.subject = subject;
			this.quizTaken = quizTaken;
			this.quizScore = quizScore;
		}
		
		
		

		/**
		 * @param userSurID
		 * @param name
		 * @param classStandard
		 * @param state
		 * @param syllabus
		 * @param schoolName
		 * @param subject
		 * @param chapter
		 * @param quizTaken
		 * @param quizScore
		 * @param idClassStandard
		 * @param idSyllabus
		 * @param idState
		 */
		public QuizScoreResponseDTO(Long userSurID, String name, String classStandard, String state, String syllabus,
				String schoolName, String subject, Instant quizTaken, Float quizScore, Long idClassStandard,
				Long idSyllabus, Long idState) {
			super();
			this.userSurID = userSurID;
			this.name = name;
			this.classStandard = classStandard;
			this.state = state;
			this.syllabus = syllabus;
			this.schoolName = schoolName;
			this.subject = subject;
			this.quizTaken = quizTaken;
			this.quizScore = quizScore;
			this.idClassStandard = idClassStandard;
			this.idSyllabus = idSyllabus;
			this.idState = idState;
		}

		public QuizScoreResponseDTO() {
			super();
		}

		@Override
		public String toString() {
			return "QuizScoreResponseDTO [userSurID=" + userSurID + ", name=" + name + ", classStandard=" + classStandard
					+ ", state=" + state + ", syllabus=" + syllabus + ", schoolName=" + schoolName + ", subject=" + subject
					+ ", chapter=" + chapter + ", quizTaken=" + quizTaken + ", quizScore=" + quizScore + "]";
		}
}
