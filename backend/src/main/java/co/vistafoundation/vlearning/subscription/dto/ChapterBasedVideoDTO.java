package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;
import java.util.List;

public class ChapterBasedVideoDTO implements Serializable {

	private static final long serialVersionUID = -2143818855372359646L;

	private Long idStudentSubscription;

	private Long idSubjectChapter;

	private Long idSubject;

	private String subjectName;

	private String totalDuration;

	private Long totalContent;

	private String chapterName;

	private Long nextIdSubjectChapter;

	private Boolean isNextChapterAvailable;

	private List<StudentVideoStreamingDTO> studentVideoStreamingDTO;

	private Boolean isAccessAllowed;

	public ChapterBasedVideoDTO() {

	}

	public ChapterBasedVideoDTO(Long idStudentSubscription, Long idSubjectChapter, Long idSubject, String subjectName,
			String totalDuration, Long totalContent, String chapterName, Long nextIdSubjectChapter,
			Boolean isNextChapterAvailable, List<StudentVideoStreamingDTO> studentVideoStreamingDTO,
			Boolean isAccessAllowed) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idSubjectChapter = idSubjectChapter;
		this.idSubject = idSubject;
		this.subjectName = subjectName;
		this.totalDuration = totalDuration;
		this.totalContent = totalContent;
		this.chapterName = chapterName;
		this.nextIdSubjectChapter = nextIdSubjectChapter;
		this.isNextChapterAvailable = isNextChapterAvailable;
		this.studentVideoStreamingDTO = studentVideoStreamingDTO;
		this.isAccessAllowed = isAccessAllowed;
	}

	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Long getTotalContent() {
		return totalContent;
	}

	public void setTotalContent(Long totalContent) {
		this.totalContent = totalContent;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Long getNextIdSubjectChapter() {
		return nextIdSubjectChapter;
	}

	public void setNextIdSubjectChapter(Long nextIdSubjectChapter) {
		this.nextIdSubjectChapter = nextIdSubjectChapter;
	}

	public Boolean getIsNextChapterAvailable() {
		return isNextChapterAvailable;
	}

	public void setIsNextChapterAvailable(Boolean isNextChapterAvailable) {
		this.isNextChapterAvailable = isNextChapterAvailable;
	}

	public List<StudentVideoStreamingDTO> getStudentVideoStreamingDTO() {
		return studentVideoStreamingDTO;
	}

	public void setStudentVideoStreamingDTO(List<StudentVideoStreamingDTO> studentVideoStreamingDTO) {
		this.studentVideoStreamingDTO = studentVideoStreamingDTO;
	}

	/**
	 * @return the isAccessAllowed
	 */
	public Boolean getIsAccessAllowed() {
		return isAccessAllowed;
	}

	/**
	 * @param isAccessAllowed the isAccessAllowed to set
	 */
	public void setIsAccessAllowed(Boolean isAccessAllowed) {
		this.isAccessAllowed = isAccessAllowed;
	}

}
