/**
 * 
 */
package co.vistafoundation.vlearning.share.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoCategoryRepository;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoRepository;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.share.dto.ShareVideoDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoLinkDTO;
import co.vistafoundation.vlearning.share.dto.ShareVideoMetaInfoDTO;
import co.vistafoundation.vlearning.share.model.ShareVideo;
import co.vistafoundation.vlearning.share.repository.ShareVideoRepository;
import co.vistafoundation.vlearning.share.service.ShareVideoService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.util.UserContentAccessValidator;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

/**
 * @author NaveenKumar
 *
 */
@Service
public class ShareVideoServiceImpl implements ShareVideoService {

	@Value("${app.share.video.viewcount:5}")
	private int viewCount;

	@Value("${app.share.video.requestcount:5}")
	private int requestCount;

	@Value("${app.share.video.expiry.minutes:3600}")
	private long exipryMinutes;

	@Autowired
	ShareVideoRepository shareVideoRepository;

	@Autowired
	LiveClassRepository liveClassRepository;

	@Autowired
	DiscoverVideoRepository discoverVideoRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VideoCipherConfiguration videoCipherConfiguration;

	@Autowired
	UserContentAccessValidator userContentAccessValidator;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	DiscoverVideoCategoryRepository discoverVideoCategoryRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Value("${app.share.url}")
	String angularUrl;

	@Override
	public Document<ShareVideoDTO> getSharedVideoIdInfo(String encryptedVidesoId) {

		Document<ShareVideoDTO> result = new Document<>();

		try {

			byte[] decodedBytes = Base64.getDecoder().decode(encryptedVidesoId.getBytes());

			String decodedString = new String(decodedBytes);

			// id will be stored after decode the value
			Long shareId = Long.valueOf(decodedString);

			// check whether the particular share video id is valid and active
			ShareVideo sv = shareVideoRepository.findByIdShareVideoAndActiveFlag(shareId, Boolean.TRUE);

			if (sv == null)
				throw new NullPointerException("This URL might be invalid.");

			User user = userRepository.findByUserSurId(sv.getIdRequestedUser());

			if (user == null)
				throw new AppException("Invalid user identity information found in this video.");

			if (!user.getActiveFlag())
				throw new AppException(
						"Sorry, user account has been blocked for the particular shared URL. Please contact the administrator.");

			// check shared video content is premium
			Boolean premiumFlag = sv.getContentType().equalsIgnoreCase("premium") ? true : false;

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			Boolean isLinkExpired = this.isLinkExpired(sv);
			// user not logged in
			if (userPrincipal == null) {

				if (isLinkExpired) {
					result.setData(null);
					result.setMessage("Sorry, the current URL has expired. Please log in to stream content.");
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

				} else {
					result.setData(this.createShareVideoBy(sv, false, encryptedVidesoId, premiumFlag, true));
					result.setMessage("Request Successfull");
					result.setStatusCode(HttpStatus.OK.value());
				}
				
			} else {
				// during the session user logged in
				if (userContentAccessValidator.isAllowedToAccessTheContent(sv.getIdClassStandard(), sv.getIdSyllabus(),
						sv.getIdState())) {

					if (premiumFlag) {
						if (isLinkExpired) {
							result.setData(this.createShareVideoBy(sv, true, encryptedVidesoId, premiumFlag,
									userPrincipal.getIsSubscribed()));
							result.setMessage("Request Successfull");
							result.setStatusCode(HttpStatus.OK.value());
						} else {
							result.setData(this.createShareVideoBy(sv, false, encryptedVidesoId, premiumFlag,
									userPrincipal.getIsSubscribed()));
							result.setMessage("Request Successfull");
							result.setStatusCode(HttpStatus.OK.value());
						}
					} else {

						if (isLinkExpired) {
							result.setData(this.createShareVideoBy(sv, true, encryptedVidesoId, false, true));
							result.setMessage("Request Successfull");
							result.setStatusCode(HttpStatus.OK.value());
						} else {
							result.setData(this.createShareVideoBy(sv, false, encryptedVidesoId, false, true));
							result.setMessage("Request Successfull");
							result.setStatusCode(HttpStatus.OK.value());
						}
					}

				} else {
					result.setData(null);
					result.setMessage("Sorry, you are not authorized to stream this content.");
					result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				}
			}

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		return result;
	}

	public ShareVideoDTO createShareVideoBy(ShareVideo sv, Boolean expiredLink, String encryptedVidesoId,
			Boolean premium, Boolean accessAllowed) throws Exception {

		ShareVideoDTO svDto = new ShareVideoDTO();

		svDto.setIdShareVideo(encryptedVidesoId);
		svDto.setVideoType(sv.getVideoType());
		svDto.setTitle(sv.getVideoTitle());
		svDto.setSubTitle(sv.getVideoSubTitle());
		svDto.setDescription(sv.getVideoDescription());
		svDto.setThumbnailURL(sv.getThumbnailURL());

		switch (sv.getVideoType().toLowerCase()) {
		case "liveclass": {

			LiveClass lc = liveClassRepository.findByIdLiveClassAndActiveFlag(sv.getIdVideo(), Boolean.TRUE);

			if (lc == null)
				throw new AppException("Invalid LiveClass video information.");

			if (lc.getClassType().equalsIgnoreCase("premium") && lc.isLiveCompletionFlag()) {
				premium = true;
			}

			svDto.setLiveClassURL(lc.getLiveClassURL());

			break;
		}

		case "discover":

		{
			Boolean availablity = discoverVideoRepository.existsById(sv.getIdVideo());

			if (!availablity)
				throw new AppException("Invalid discover video information.");

			svDto.setVcpVideoId(sv.getVcpVideoId());

			if (expiredLink) {
				String body = "{\"ttl\":3600}"; // set video expiration for an hour
				VideoCipherOTP vco = videoCipherConfiguration.getOTP(sv.getVcpVideoId(), body);
				svDto.setVcpVideoOtp(vco.getOtp());

			} else {
				svDto.setVcpVideoOtp(sv.getVcpVideoOtp());
			}

			break;

		}

		case "animated":

		{
			Boolean availablity = offlineVideoCourseRepository.existsById(sv.getIdVideo());

			if (!availablity)
				throw new AppException("Invalid animted video information.");

			svDto.setVcpVideoId(sv.getVcpVideoId());

			if (expiredLink) {
				String body = "{\"ttl\":3600}"; // set video expiration for an hour
				VideoCipherOTP vco = videoCipherConfiguration.getOTP(sv.getVcpVideoId(), body);
				svDto.setVcpVideoOtp(vco.getOtp());

			} else {
				svDto.setVcpVideoOtp(sv.getVcpVideoOtp());
			}

			break;

		}

		default: {

			svDto.setS3BucketURL(sv.getS3BucketURL());

		}

		}

		if (premium && accessAllowed) {
			svDto.setPremiumFlag(true);
		} else if (premium && !accessAllowed) {

			svDto.setIdShareVideo(encryptedVidesoId);
			svDto.setVideoType(sv.getVideoType());
			svDto.setTitle(sv.getVideoTitle());
			svDto.setSubTitle(sv.getVideoSubTitle());
			svDto.setDescription(sv.getVideoDescription());
			svDto.setPremiumFlag(true);
			svDto.setVcpVideoId(null);
			svDto.setVcpVideoOtp(null);
			svDto.setLiveClassURL(null);
			svDto.setS3BucketURL(null);

		} else {
			svDto.setPremiumFlag(false);
		}

		return svDto;
	}

	private Boolean isLinkExpired(ShareVideo sv) throws Exception {

		return (sv.getViewCount() < viewCount
				&& ChronoUnit.MINUTES.between(sv.getCreatedAt(), Instant.now()) < exipryMinutes) ? false : true;

	}

	/*
	 * @author AbdulElahi Generating the link to share
	 */

	@Override
	public Document<String> getShareVideoLink(ShareVideoLinkDTO svLinkDTO) {

		Document<String> doc = new Document<>();

		try {
			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid user information.");

			Long userSurId = userPrincipal.getUserSurId();

			String url = null;

			Long idClassStandard = 4L;
			Long idSyllabus = 4L;
			Long idState = 6L;

			String title = null;
			String subTitle = null;
			String description = null;

			String thumbnailURL = null;

			LocalDate date = LocalDate.now();

			switch (svLinkDTO.getVideoType().toLowerCase()) {

			case "liveclass": {
				LiveClass lc = liveClassRepository.findByIdLiveClassAndActiveFlag(svLinkDTO.getIdVideo(), Boolean.TRUE);

				if (lc == null)
					throw new AppException("Invalid LiveClass video information.");

				idClassStandard = lc.getIdClassStandard();
				idSyllabus = lc.getIdSyllabus();
				idState = lc.getIdState();

				Boolean validator = userContentAccessValidator.isAllowedToAccessTheContent(idClassStandard, idSyllabus,
						idState);

				if (!validator)
					throw new AppException("unable to share this video");

				Subject sub = subjectRepository.findByIdSubject(lc.getIdSubject());

				if (sub == null)
					throw new NullPointerException("Invalid State found in live class");

				title = lc.getLiveClassHeading();
				description = lc.getLiveClassDesc();
				thumbnailURL = lc.getThumbnailURL();

				if (lc.getIdLiveClassCategory().equals(1L)) {
					ClassStandard cs = classRepository.findByIdClassStandard(lc.getIdClassStandard());

					if (cs == null)
						throw new NullPointerException("Invalid Class Standard found in live class");

					Syllabus sy = syllabusRepository.findByIdSyllabus(lc.getIdSyllabus());

					if (sy == null)
						throw new NullPointerException("Invalid Syllabus found in live class");

					State st = stateRepository.findByIdState(lc.getIdState());

					if (st == null)
						throw new NullPointerException("Invalid State found in live class");

					subTitle = sy.getSyllabusName() + " " + st.getState() + " " + " Class " + cs.getClassStandadName()
							+ " - " + sub.getSubjectName().toUpperCase();

				} else {
					subTitle = "Building legends" + " - " + sub.getSubjectName().toUpperCase();
				}

				break;
			}
			case "discover": {
				DiscoverVideo dv = discoverVideoRepository.findByIdDiscoverVideo(svLinkDTO.getIdVideo());
				if (dv == null)
					throw new AppException("Invalid Discover video information.");

				url = dv.getVideoLink();

				title = dv.getTopic();
				description = dv.getVideoDescription();

				DiscoverVideoCategory dvc = discoverVideoCategoryRepository
						.findByIdDiscoverVideoCategory(dv.getIdDiscoverVideoCategory());

				if (dvc == null)
					throw new AppException("Invalid Discover Video Category information.");

				subTitle = "Discover" + " - " + dvc.getCategory().toUpperCase();
				thumbnailURL = dv.getPostarLoc();

				break;
			}
			case "animated": {

				OfflineVideoCourse ofc = offlineVideoCourseRepository.getByIdOfflineVideoCourse(svLinkDTO.getIdVideo());

				if (ofc == null)
					throw new AppException("Invalid Offline video information.");

				Product product = productRepository.findByIdProductAndActiveFlag(
						ofc.getIdProduct(),Boolean.TRUE);

				if (product == null)
					throw new AppException("Invalid prodcut information found in offline video course.");

				Boolean validator = userContentAccessValidator.isAllowedToAccessTheContent(product.getIdClassStandard(),
						product.getIdSyllabus(), product.getIdState());

				if (!validator)
					throw new AppException("Unable to share this video.");

				title = ofc.getQuestion();
				String[] lines = ofc.getAnswer().split(System.lineSeparator());
				description = lines[0];

				Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

				if (sub == null)
					throw new NullPointerException("Invalid Subject found in Animated Video");

				if (product.getIdProductLine().equals(5L)) {
					ClassStandard cs = classRepository.findByIdClassStandard(product.getIdClassStandard());

					if (cs == null)
						throw new NullPointerException("Invalid Class Standard found in Animated Video");

					Syllabus sy = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

					if (sy == null)
						throw new NullPointerException("Invalid Syllabus found in Animated Video");

					State st = stateRepository.findByIdState(product.getIdState());

					if (st == null)
						throw new NullPointerException("Invalid State found in Animated Video");
					
					idClassStandard = cs.getIdClassStandard();
					idState = st.getIdState();
					idSyllabus = sy.getIdSyllabus();
					
					
					subTitle = sy.getSyllabusName() + " " + st.getState() + " " + "Class " + cs.getClassStandadName()
							+ " - " + sub.getSubjectName().toUpperCase();
					
				} else if (product.getIdProductLine().equals(6L)) {
					SubjectChapter subChap = subjectChapterRepository.findByIdSubjectChapter(ofc.getIdSubjectChapter());

					if (subChap == null)
						throw new NullPointerException("Invalid Subject Chapter in Animated Video.");

					subTitle = "Building legends" + " - " + sub.getSubjectName().toUpperCase() + " - "
							+ subChap.getChapterName().toUpperCase();
				} else {
					SubjectChapter subChap = subjectChapterRepository.findByIdSubjectChapter(ofc.getIdSubjectChapter());

					if (subChap == null)
						throw new NullPointerException("Invalid Subject Chapter in Animated Video.");

					subTitle = sub.getSubjectName().toUpperCase() + " - " + subChap.getChapterName().toUpperCase();
				}

				url = ofc.getVideoEnLink();
				thumbnailURL = ofc.getVideoPoster1Location();

				break;

			}
			default: {
				throw new AppException("Invalid video type found.");
			}

			}

			ShareVideo sv = shareVideoRepository.findByIdRequestedUserAndRequestedDateAndVideoTypeAndIdVideo(userSurId,
					date, svLinkDTO.getVideoType().toLowerCase(), svLinkDTO.getIdVideo());

			if (sv == null) {

				int count = shareVideoRepository
						.getViewCountByIdRequestedUserAndRequestedDate(userSurId, LocalDate.now()).size();

				if (count < requestCount) {

					ShareVideo newSv = new ShareVideo();

					newSv.setContentType(svLinkDTO.getContentType().toLowerCase());
					newSv.setIdClassStandard(idClassStandard);
					newSv.setIdState(idState);
					newSv.setIdSyllabus(idSyllabus);
					newSv.setIdRequestedUser(userSurId);
					newSv.setIdVideo(svLinkDTO.getIdVideo());
					newSv.setRequestedDate(LocalDate.now());
					newSv.setViewCount(0);
					newSv.setVideoType(svLinkDTO.getVideoType().toLowerCase());
					newSv.setActiveFlag(Boolean.TRUE);
					newSv.setVideoTitle(title);
					newSv.setVideoSubTitle(subTitle);
					newSv.setVideoDescription(description);
					newSv.setThumbnailURL(thumbnailURL);

					// generating video cipher otp
					if (svLinkDTO.getVideoType().equalsIgnoreCase("animated")
							|| svLinkDTO.getVideoType().equalsIgnoreCase("discover")) {
						newSv.setVcpVideoId(url);

						String body = "{\"ttl\":" + exipryMinutes + "}"; // set video expiration based on environmental
																			// values
						VideoCipherOTP vco = videoCipherConfiguration.getOTP(url, body);
						newSv.setVcpVideoOtp(vco.getOtp());

					}

					sv = shareVideoRepository.save(newSv);

				} else {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
					doc.setMessage(
							"Sorry, you have exceeded the daily limit for sharing videos. Please try again tomorrow!");
					return doc;
				}
			}

			String encodedUrl = Base64.getUrlEncoder().encodeToString(sv.getIdShareVideo().toString().getBytes());

			String sharableUrl = angularUrl + "/share/" + encodedUrl;

			doc.setData(sharableUrl);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Video URL generated successfully.");

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@Async
	@Override
	public Document<String> updatedVideoViewCount(String encryptedVidesoId) {

		Document<String> result = new Document<>();

		try {

			byte[] decodedBytes = Base64.getDecoder().decode(encryptedVidesoId.getBytes());

			String decodedString = new String(decodedBytes);

			// id will be stored after decode the value
			Long shareId = Long.valueOf(decodedString);

			// check whether the particular share video id is valid and active
			ShareVideo sv = shareVideoRepository.findByIdShareVideoAndActiveFlag(shareId, Boolean.TRUE);

			if (sv == null)
				throw new NullPointerException("This URL might be invalid.");

			int viewCount = sv.getViewCount();

			sv.setViewCount(viewCount + 1);

			shareVideoRepository.save(sv);

			result.setData("View Count updated!");
			result.setMessage("Request Successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		return result;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document<ShareVideoMetaInfoDTO> getMetaInfo(String encryptedVideoId) {
		Document doc = new Document();
		try {
			
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedVideoId.getBytes());

			String decodedString = new String(decodedBytes);
			
			Long shareId = Long.valueOf(decodedString);

			ShareVideo shareVideoInfo = shareVideoRepository.findByIdShareVideoAndActiveFlag(shareId,Boolean.TRUE);

			if (shareVideoInfo == null)
				throw new AppException("Invalid Share id");

			ShareVideoMetaInfoDTO shareVideoMetaInfoDTO = new ShareVideoMetaInfoDTO();
			shareVideoMetaInfoDTO.setTitle(shareVideoInfo.getVideoTitle());
			shareVideoMetaInfoDTO.setSubTitlle(shareVideoInfo.getVideoSubTitle());
			shareVideoMetaInfoDTO.setThumbnail(shareVideoInfo.getThumbnailURL());
			shareVideoMetaInfoDTO.setDesc(shareVideoInfo.getVideoDescription());

			doc.setData(shareVideoMetaInfoDTO);
			doc.setMessage("Successfully generated Meta Info");
			doc.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {

			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}

		return doc;
	}

}