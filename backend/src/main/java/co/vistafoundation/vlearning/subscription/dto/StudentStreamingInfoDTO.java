/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.util.List;

/**
 * @author NAVEEN
 *
 */
public class StudentStreamingInfoDTO {

	List<Object> chapterVideoCourseList;

	private Boolean prevChapterAttemptFlag;

	/**
	 * @return the chapterVideoCourseList
	 */
	public List<Object> getChapterVideoCourseList() {
		return chapterVideoCourseList;
	}

	/**
	 * @param chapterVideoCourseList the chapterVideoCourseList to set
	 */
	public void setChapterVideoCourseList(List<Object> chapterVideoCourseList) {
		this.chapterVideoCourseList = chapterVideoCourseList;
	}

	/**
	 * @return the prevChapterAttemptFlag
	 */
	public Boolean getPrevChapterAttemptFlag() {
		return prevChapterAttemptFlag;
	}

	/**
	 * @param prevChapterAttemptFlag the prevChapterAttemptFlag to set
	 */
	public void setPrevChapterAttemptFlag(Boolean prevChapterAttemptFlag) {
		this.prevChapterAttemptFlag = prevChapterAttemptFlag;
	}

}
