/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author NaveenKumar
 *
 */
@JsonIgnoreProperties("streamingSubjectChapters")
public class NewStudentSubscriptionSubjectDTO extends StudentSubscriptionSubjectDTO  implements Serializable{

	private static final long serialVersionUID = -8677789816803062333L;

	private String totalSubjectVideos;

	private String totalSubjectWatchHours;
	
	private String subjectThumbnailURL; 

	private List<NewStreamingSubjectChapterDTO> subjectChapters;

	public NewStudentSubscriptionSubjectDTO() {
	}

	/**
	 * @return the subjectChapters
	 */
	public List<NewStreamingSubjectChapterDTO> getSubjectChapters() {
		return subjectChapters;
	}

	/**
	 * @param subjectChapters the subjectChapters to set
	 */
	public void setSubjectChapters(List<NewStreamingSubjectChapterDTO> subjectChapters) {
		this.subjectChapters = subjectChapters;
	}

	/**
	 * @return the totalSubjectVideos
	 */
	public String getTotalSubjectVideos() {
		return totalSubjectVideos;
	}

	/**
	 * @param totalSubjectVideos the totalSubjectVideos to set
	 */
	public void setTotalSubjectVideos(String totalSubjectVideos) {
		this.totalSubjectVideos = totalSubjectVideos;
	}

	/**
	 * @return the totalSubjectwatchHours
	 */
	public String getTotalSubjectwatchHours() {
		return totalSubjectWatchHours;
	}

	/**
	 * @param totalSubjectwatchHours the totalSubjectwatchHours to set
	 */
	public void setTotalSubjectwatchHours(String totalSubjectwatchHours) {
		this.totalSubjectWatchHours = totalSubjectwatchHours;
	}

	/**
	 * @return the subjectThumbnailURL
	 */
	public String getSubjectThumbnailURL() {
		return subjectThumbnailURL;
	}

	/**
	 * @param subjectThumbnailURL the subjectThumbnailURL to set
	 */
	public void setSubjectThumbnailURL(String subjectThumbnailURL) {
		this.subjectThumbnailURL = subjectThumbnailURL;
	}
	
	

}
