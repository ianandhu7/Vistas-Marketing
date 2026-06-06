/**
 * 
 */
package co.vistafoundation.vlearning.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoLikeDislike;
import co.vistafoundation.vlearning.video.repository.SocialVideoRepository;
import co.vistafoundation.vlearning.video.repository.VideoLikeDislikeReposirtory;
import co.vistafoundation.vlearning.video.service.VideoLikeDislikeService;

/**
 * @author Naveen Kumar
 *
 */
@Service
public class VideoLikeDislikeServiceImpl implements VideoLikeDislikeService {

	@Autowired
	SocialVideoRepository socialVideoRepository;

	@Autowired
	VideoLikeDislikeReposirtory videoLikeDislikeReposirtory;

	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public Document<VideoLikeDislike> createUserLikeVideo(Long idSocialVideo, boolean likeFlag, boolean disLikeFlag) {

		Document<VideoLikeDislike> result = new Document<>();

		try {

			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				user = (UserPrincipal) authentication.getPrincipal();

			}

			if (user == null)
				throw new AppException("Invalid User");

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo, true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");

			VideoLikeDislike vldl = videoLikeDislikeReposirtory
					.findBySocialVideo_idSocialVideoAndIdVlUser(idSocialVideo, user.getUserSurId());
			// create fresh record for the video
			if (vldl == null)

			{
				VideoLikeDislike temp = new VideoLikeDislike();

				if (likeFlag) { // when user like the video for first time
					sv.setTotalLikes(sv.getTotalLikes() + 1);
					temp.setLikeFlag(true);
					temp.setDisLikeFlag(false);
					temp.setIdVlUser(user.getUserSurId());
					temp.setSocialVideo(sv);

					VideoLikeDislike response = videoLikeDislikeReposirtory.save(temp);
					result.setData(response);
					result.setMessage("Request successfull");
					result.setStatusCode(HttpStatus.CREATED.value());
				} else if (disLikeFlag) 
				{
					// when user Dislike the video for first time
					sv.setTotalDisLikes(sv.getTotalDisLikes() + 1);
					temp.setLikeFlag(false);
					temp.setDisLikeFlag(true);
					temp.setIdVlUser(user.getUserSurId());
					temp.setSocialVideo(sv);

					VideoLikeDislike response = videoLikeDislikeReposirtory.save(temp);
					result.setData(response);
					result.setMessage("Request successfull");
					result.setStatusCode(HttpStatus.CREATED.value());
				}
				else {
					
					result.setData(new VideoLikeDislike());
					result.setMessage("No DisLike records found.");
					result.setStatusCode(HttpStatus.OK.value());
				}
			} else {
				if (likeFlag != true && disLikeFlag != true) {
					if (vldl.isLikeFlag()) {
						sv.setTotalLikes(sv.getTotalLikes() - 1);
						vldl.setSocialVideo(sv);
						videoLikeDislikeReposirtory.delete(vldl);

						result.setData(new VideoLikeDislike());
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());
					} else {

						sv.setTotalDisLikes(sv.getTotalDisLikes() - 1);
						vldl.setSocialVideo(sv);
						videoLikeDislikeReposirtory.delete(vldl);
						result.setData(new VideoLikeDislike());
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());
					}
				} else if (likeFlag == true && disLikeFlag != true) {
					if (vldl.isDisLikeFlag()) {
						sv.setTotalLikes(sv.getTotalLikes() + 1);
						sv.setTotalDisLikes(sv.getTotalDisLikes() - 1);
						vldl.setDisLikeFlag(false);
						vldl.setLikeFlag(true);
						vldl.setSocialVideo(sv);

						VideoLikeDislike response = videoLikeDislikeReposirtory.save(vldl);
						result.setData(response);
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());

					}

					else {
						vldl.setDisLikeFlag(false);
						vldl.setLikeFlag(true);
						VideoLikeDislike response = videoLikeDislikeReposirtory.save(vldl);
						result.setData(response);
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());

					}
				}

				else if (likeFlag != true && disLikeFlag == true) {
					if (vldl.isLikeFlag()) {
						sv.setTotalLikes(sv.getTotalLikes() - 1);
						sv.setTotalDisLikes(sv.getTotalDisLikes() + 1);
						vldl.setDisLikeFlag(true);
						vldl.setLikeFlag(false);
						vldl.setSocialVideo(sv);

						VideoLikeDislike response = videoLikeDislikeReposirtory.save(vldl);
						result.setData(response);
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());

					}

					else {
						vldl.setDisLikeFlag(true);
						vldl.setLikeFlag(false);
						VideoLikeDislike response = videoLikeDislikeReposirtory.save(vldl);
						result.setData(response);
						result.setMessage("Request successfull");
						result.setStatusCode(HttpStatus.OK.value());

					}
				} else {

					result.setData(vldl);
					result.setMessage("Request successfull");
					result.setStatusCode(HttpStatus.OK.value());

				}

			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<VideoLikeDislike> getUserLikeVideo(Long idSocialVideo) {

		Document<VideoLikeDislike> result = new Document<>();

		try {

			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				user = (UserPrincipal) authentication.getPrincipal();

			}

			if (user == null)
				throw new AppException("Invalid User");

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo, true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");

			VideoLikeDislike temp = videoLikeDislikeReposirtory
					.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), user.getUserSurId());

			if (temp == null) {
				result.setData(temp);
				result.setMessage("No Like or DisLike Found");
				result.setStatusCode(HttpStatus.OK.value());
			} else {
				result.setData(temp);
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

}
