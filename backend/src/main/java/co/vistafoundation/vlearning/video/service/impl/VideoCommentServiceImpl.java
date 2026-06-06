/**
 * 
 */
package co.vistafoundation.vlearning.video.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.video.dto.CommentFilterDTO;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoComment;
import co.vistafoundation.vlearning.video.repository.SocialVideoRepository;
import co.vistafoundation.vlearning.video.repository.VideoCommentReposirtory;
import co.vistafoundation.vlearning.video.service.VideoCommentService;

/**
 * @author Naveen Kumar
 *
 */
@Service
public class VideoCommentServiceImpl implements VideoCommentService {

	@Autowired
	SocialVideoRepository socialVideoRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VideoCommentReposirtory videoCommentReposirtory;
	
	
	 private static final String COMMENT_REGEX = "^[a-zA-Z0-9 .,?!:;'\"()\\[\\]{}@#$%&*_+-/\\s]*$";
	    private static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REGEX);

	@Override
	@Transactional
	public Document<VideoComment> saveComment(Long idSocialVideo, VideoComment videoComment) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		Document<VideoComment> result = new Document<>();
		
		
		 // Validate the comment content
        if (!isCommentValid(videoComment.getComment())) {
            result.setData(null);
            result.setMessage("Comment contains invalid characters.");
            result.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return result;
        }

		try {

			User user = userRepository.findByUsername(userDetails.getUsername());
			
			if(user ==null)throw new NullPointerException("No user found");

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo,true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");

			sv.setTotalComments(sv.getTotalComments() + 1);
			videoComment.setSocialVideo(sv);
			videoComment.setUser(user);

			VideoComment response = videoCommentReposirtory.save(videoComment);
			result.setData(response);
			result.setMessage("Thanks for your comment! It will appear once approved by the admin.");
			result.setStatusCode(HttpStatus.CREATED.value());

		
		}		
		 catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@Override
	@Transactional
	public Document<VideoComment> updateComment(Long idSocialVideo, VideoComment videoComment) {

		Document<VideoComment> result = new Document<>();

		try {

			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				
			   user = (UserPrincipal) authentication.getPrincipal();
			   
			}
			
			if (user == null) throw new AppException("Invalid User");
			
			VideoComment vc = videoCommentReposirtory
					.findByIdVideoCommentAndUser_userSurId(videoComment.getIdVideoComment(), user.getUserSurId());

			if (vc == null)
				throw new NullPointerException("invalid idVideoComment.");
			vc.setComment(videoComment.getComment());
			vc.setUpdatedAt(Instant.now());
			vc.setUpdatedBy(user.getUserSurId());
			VideoComment response = videoCommentReposirtory.save(vc);
			result.setData(response);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@Override
	@Transactional
	public Document<String> deleteComment(Long idSocialVideo, Long idVideoComment) {

		Document<String> result = new Document<>();

		try {
			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				
			   user = (UserPrincipal) authentication.getPrincipal();
			   
			}
			
			if (user == null) throw new AppException("Invalid User");

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo,true);
			
			

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");
			
			Long count = sv.getTotalComments() == 0 ? 0 : sv.getTotalComments() -1 ;

			

			VideoComment vc = videoCommentReposirtory.findByIdVideoCommentAndUser_userSurId(idVideoComment,
					user.getUserSurId());

			if (vc == null)
				throw new NullPointerException("invalid idVideoComment.");
			
			sv.setTotalComments(count);
			vc.setSocialVideo(sv);
			

			videoCommentReposirtory.delete(vc);
			
			List <VideoComment> temp = videoCommentReposirtory.findBySocialVideoOrderByCreatedAtDesc(sv);
			
			if (temp.isEmpty() && sv.getTotalComments() != 0) sv.setTotalComments(0L);
			
			//check the video comments value zero 
			if(sv.getTotalComments() == 0 )
			{
				List <VideoComment> comments = videoCommentReposirtory.findBySocialVideoOrderByCreatedAtDesc(sv);
				
				if (!comments.isEmpty()) 
				{	
					videoCommentReposirtory.deleteAll(comments);
				}
				
			}
			result.setData("Comment Deleted Sucessfully");
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@Override
	public Document<Page<VideoComment>> getAllCommentsForVideo(Long idSocialVideo,boolean isVisble,int pageNumber) {

		Document<Page<VideoComment>> result = new Document<>();
		try {
			Pageable paging = PageRequest.of(pageNumber, 50);

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo,true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");

			Page<VideoComment> comments = videoCommentReposirtory.findBySocialVideoAndIsVisibleOrderByCreatedAtDesc(sv,isVisble,paging);

			if (comments.getContent().isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More comments for this Videos");

			} else if (comments.getContent().isEmpty()) {

				result.setData(null);
				result.setMessage("No Comments for the video");
				result.setStatusCode(HttpStatus.OK.value());

			} else {
				result.setData(comments);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public Document<Page<VideoComment>> getComments(CommentFilterDTO commentFilter,String sort,
			int pageNumber, int size) {

		Document<Page<VideoComment>> result = new Document<>();
		try {
			
			
			
			Sort.Direction sortDirection = Sort.Direction.fromString(sort);
		  
		
			
			Pageable paging = PageRequest.of(pageNumber, size, Sort.by(sortDirection, "createdAt"));

			
			
			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(commentFilter.getIdSocialVideo(),true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");
			
			Page<VideoComment> comments=null;

			comments = videoCommentReposirtory.getComments(commentFilter.getIdSocialVideo(),
					commentFilter.getIsVisible(), commentFilter.getIdVlUser(), commentFilter.getFrom(),
					commentFilter.getTo(), paging);

			if (comments.getContent().isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More comments for this Videos");

			} else if (comments.getContent().isEmpty()) {

				result.setData(null);
				result.setMessage("No Comments for the video");
				result.setStatusCode(HttpStatus.OK.value());

			} else {
				result.setData(comments);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public Document<VideoComment> updateCommentStatus(Long idVideoComment, Boolean isVisible) {
		

		Document<VideoComment> result = new Document<>();

		try {

			VideoComment vc = videoCommentReposirtory
					.findByIdVideoComment(idVideoComment);

			if (vc == null)
				throw new NullPointerException("invalid idVideoComment.");
			
			vc.setVisible(isVisible);
			VideoComment response = videoCommentReposirtory.save(vc);
			result.setData(response);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}
	
	  private boolean isCommentValid(String comment) {
	        if (comment == null || comment.isEmpty()) {
	            return false;
	        }
	        return COMMENT_PATTERN.matcher(comment).matches();
	    }

}
