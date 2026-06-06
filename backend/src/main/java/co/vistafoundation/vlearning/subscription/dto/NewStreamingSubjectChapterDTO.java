/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;

import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTO;

/**
 * @author NaveenKumar
 *
 */
public class NewStreamingSubjectChapterDTO extends StreamingSubjectChapterDTO implements Serializable{

     private static final long serialVersionUID = 944286471101098804L;

	private String totalChapterVideos;
	
	private String totalChapterWatchHours;
	
	
	public NewStreamingSubjectChapterDTO() {}


	/**
	 * @return the totalChapterVideos
	 */
	public String getTotalChapterVideos() {
		return totalChapterVideos;
	}


	/**
	 * @param totalChapterVideos the totalChapterVideos to set
	 */
	public void setTotalChapterVideos(String totalChapterVideos) {
		this.totalChapterVideos = totalChapterVideos;
	}


	/**
	 * @return the totalChapterWatchHours
	 */
	public String getTotalChapterWatchHours() {
		return totalChapterWatchHours;
	}


	/**
	 * @param totalChapterWatchHours the totalChapterWatchHours to set
	 */
	public void setTotalChapterWatchHours(String totalChapterWatchHours) {
		this.totalChapterWatchHours = totalChapterWatchHours;
	}
	
	

}
