/**
 * 
 */
package co.vistafoundation.vlearning.video.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.UserVideoViewHistory;
import co.vistafoundation.vlearning.video.model.VideoView;
import co.vistafoundation.vlearning.video.repository.SocialVideoRepository;
import co.vistafoundation.vlearning.video.repository.UserVideoViewHistoryReposirtory;
import co.vistafoundation.vlearning.video.repository.VideoViewReposirtory;
import co.vistafoundation.vlearning.video.service.VideoViewService;

/**
 * @author Naveen Kumar
 *
 */
@Service
public class VideoViewServiceImpl implements VideoViewService {

	@Autowired
	SocialVideoRepository socialVideoRepository;

	@Autowired
	VideoViewReposirtory videoViewReposirtory;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserVideoViewHistoryReposirtory userVideoViewHistoryReposirtory;

	@Override
	public Document<VideoView> createVideoView(Long idSocialVideo, String duration) {

		Document<VideoView> result = new Document<>();

		try {
			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				user = (UserPrincipal) authentication.getPrincipal();

			}

			if (user == null)
				throw new AppException("Invalid User");

			SocialVideo sv = socialVideoRepository.findByIdSocialVideoAndActiveFlag(idSocialVideo,true);

			if (sv == null)
				throw new NullPointerException("Invalid Video Id.");

			VideoView vv = videoViewReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(idSocialVideo,
					user.getUserSurId());
			
			UserVideoViewHistory uvvh = userVideoViewHistoryReposirtory
					.findBySocialVideo_idSocialVideoAndIdVlUser(idSocialVideo, user.getUserSurId());

			int currentDuration = Math.round(Float.valueOf(duration));

			int totalDuration = sv.getVideoDuration();

			if (currentDuration > totalDuration)
				throw new AppException("Video Duration Exceed the Limit!");

			if (vv == null && uvvh == null) {
				// creating new VideoViewObject
				sv.setTotalViews(sv.getTotalViews() + 1);
				VideoView temp = new VideoView();
				temp.setIdVlUser(user.getUserSurId());
				temp.setSocialVideo(sv);

				// creating new User UserVideoViewHistory

				UserVideoViewHistory temp1 = new UserVideoViewHistory();
				temp1.setIdVlUser(user.getUserSurId());
				temp1.setSocialVideo(sv);
				temp1.setWatchedDuration(currentDuration);
				double meanValue = (double) currentDuration / totalDuration;
				meanValue *= 100;
				String percent = Double.toString(Math.round(meanValue));
				temp1.setWatchedPercentage(percent);
				VideoView response = videoViewReposirtory.save(temp);
				userVideoViewHistoryReposirtory.save(temp1);

				result.setData(response);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.CREATED.value());
			} else {
				if (uvvh == null)
					uvvh = new UserVideoViewHistory();
				uvvh.setUpdatedAt(Instant.now());
				uvvh.setWatchedDuration(currentDuration);
				double meanValue = (double) currentDuration / totalDuration;
				meanValue *= 100;
				String percent = Double.toString(Math.round(meanValue));
				uvvh.setWatchedPercentage(percent);
				userVideoViewHistoryReposirtory.save(uvvh);
				result.setData(vv);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

}
