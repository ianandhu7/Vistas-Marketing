/**
 * 
 */
package co.vistafoundation.vlearning.video.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.dto.CommentFilterDTO;
import co.vistafoundation.vlearning.video.model.VideoComment;

/**
 * @author Naveen Kumar
 *
 */

public interface VideoCommentService {

	/**
	 * This method will create VideoComment and update respecitive social video
	 * TotalComment Value.
	 * 
	 * @param IdSocialVideo
	 * @param videoComment
	 * @return
	 */
	public Document<VideoComment> saveComment(Long idSocialVideo, VideoComment videoComment);

	/**
	 * This method will update VideoComment.
	 * 
	 * @param IdSocialVideo
	 * @param videoComment
	 * @return
	 */
	public Document<VideoComment> updateComment(Long idSocialVideo, VideoComment videoComment);

	/**
	 * This method will delete VideoComment and update respecitive social video
	 * TotalComments Value.
	 * 
	 * @param IdSocialVideo
	 * @param videoComment
	 * @return
	 */
	public Document<String> deleteComment(Long idSocialVideo, Long idVideoComment);

	public Document<Page<VideoComment>> getAllCommentsForVideo(Long idSocialVideo,boolean isvisble,int pageNumber);

	public Document<Page<VideoComment>> getComments(CommentFilterDTO commentFilter,
			String sort, int pageNumber, int size);

	public Document<VideoComment> updateCommentStatus(Long idVideoComment, Boolean isVisible);

}
