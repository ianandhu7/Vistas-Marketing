package co.vistafoundation.vlearning.video.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;


import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.notification.service.UserNotificationService;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;
import co.vistafoundation.vlearning.video.dto.DeleteSocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.ReviewSocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoFilterDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoResponseDTO;
import co.vistafoundation.vlearning.video.model.Location;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.SocialVideoResolution;
import co.vistafoundation.vlearning.video.model.SocialVideoYoutube;
import co.vistafoundation.vlearning.video.model.VideoCategory;
import co.vistafoundation.vlearning.video.model.VideoLikeDislike;
import co.vistafoundation.vlearning.video.model.VideoView;
import co.vistafoundation.vlearning.video.repository.LocationRepository;
import co.vistafoundation.vlearning.video.repository.SocialVideoRatingReposirtory;
import co.vistafoundation.vlearning.video.repository.SocialVideoRepository;
import co.vistafoundation.vlearning.video.repository.SocialVideoResolutionRepository;
import co.vistafoundation.vlearning.video.repository.SocialVideoYoutubeRepository;
import co.vistafoundation.vlearning.video.repository.TagListRepository;
import co.vistafoundation.vlearning.video.repository.UserVideoViewHistoryReposirtory;
import co.vistafoundation.vlearning.video.repository.VideoCategoryRepository;
import co.vistafoundation.vlearning.video.repository.VideoCommentReposirtory;
import co.vistafoundation.vlearning.video.repository.VideoLikeDislikeReposirtory;
import co.vistafoundation.vlearning.video.repository.VideoTagRepository;
import co.vistafoundation.vlearning.video.repository.VideoViewReposirtory;
import co.vistafoundation.vlearning.video.service.SocialVideoService;
import co.vistafoundation.vlearning.video.utils.VideoResolution;

@Service
public class SocialVideoServiceImpl implements SocialVideoService {

	@Autowired
	private SocialVideoYoutubeRepository socialVideoYoutubeRepository;

	@Autowired
	private VideoCategoryRepository videoCategoryRepository;

	@Autowired
	SocialVideoRepository socialVideoRepository;

	@Autowired
	TagListRepository tagListRepository;

	@Autowired
	VideoTagRepository videoTagRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	SocialVideoRatingReposirtory socialVideoRatingReposirtory;

	@Autowired
	VideoViewReposirtory videoViewReposirtory;

	@Autowired
	UserVideoViewHistoryReposirtory userVideoViewHistoryReposirtory;

	@Autowired
	VideoCommentReposirtory videoCommentReposirtory;

	@Autowired
	VideoLikeDislikeReposirtory videoLikeDislikeReposirtory;

	@Autowired
	ImageFileUploadService imageUploadService;

	@Autowired
	UserNotificationService userNotificationService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	private SocialVideoResolutionRepository socialVideoResolutionRepository; 

	
	@Value("${aws.s3.bucket}")
	private String bucketName;
	
	@Value("${aws.accesskey.id}")
	private String accessId;
	
	@Value("${aws.secretaccess.key}")
	private String secretKey;
	
	
	private static final Logger log = LoggerFactory.getLogger(SocialVideoServiceImpl.class);

	@Override
	public Document<Object> uploadSocialVideoToYoutube(MultipartFile file, String videoDescription) {

		Document<Object> doc = new Document<>();

		try {

			final File tempFile = convertMultiPartFileToFile(file);
			String getUrl = uploadFileToS3Bucket(bucketName + "/vistasocialmedia", tempFile);

			System.err.println("S3 Url==>" + getUrl);

//			byte[] socialVideoFile = file.getBytes();
//
//			Blob blob = new SerialBlob(socialVideoFile);

			SocialVideoYoutube socialVideoYoutube = new SocialVideoYoutube();

			socialVideoYoutube.setVideoDescription(videoDescription);
			socialVideoYoutube.setSocialVideoFile(tempFile);

			SocialVideoYoutube saved = socialVideoYoutubeRepository.save(socialVideoYoutube);

			boolean isDeletedTempFile = tempFile.delete();
			log.info("Temp file deleted from the system : " +isDeletedTempFile );

			doc.setData(saved);
			doc.setMessage("Video Uploaded Successfully");
			doc.setStatusCode(201);

			return doc;

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		}
		return file;
	}

	private String uploadFileToS3Bucket(final String bucketName, final File file) {
		String uniqueFile = file.getName();

		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFile, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName, uniqueFile).toString();
		return s3Url;
	}

	@Override
	public Document<List<VideoCategory>> getAllVideoCategory() {

		Document<List<VideoCategory>> result = new Document<>();

		try {

			List<VideoCategory> list = videoCategoryRepository.findAll();

			if (list.isEmpty())
				throw new NullPointerException("No Video Category list found.");

			result.setData(list);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;

	}

	@Transactional
	@Override
	public Document<SocialVideo> createSocialVideo(String videoTitle, String videoDescription,  Long idVideoCategory, int duration, MultipartFile video,MultipartFile thumbnail) {

		Document<SocialVideo> result = new Document<>();

		try {
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			SocialVideo socialVideo = new SocialVideo();

			VideoCategory videoCategory = videoCategoryRepository
					.findByIdVideoCategory(idVideoCategory);

			if (videoCategory == null)
				throw new NullPointerException("Invalid Video Category");

			
				String city = "Bangalore";
				Location loc = locationRepository.findByCityName(city);

				if (loc == null) {
					Location temp = new Location();
					temp.setCityName(city);
					temp.setGeoLocation("12.97");

					loc = locationRepository.save(temp);
				}
				socialVideo.setIdLocation(loc.getIdLocation());
			
			Document<String> s3Linkresult=uploadSocialVideo(video);
			
			Document<String>  thumbnailUrl =uploadThumbnail(thumbnail);
			
			String outPut=	mediaConvert(s3Linkresult.getData().toString());
				
		     	
			    JSONObject jsonResponse = new JSONObject(outPut);
		        String responseBody = jsonResponse.getString("body");

		        JSONObject bodyJson = new JSONObject(responseBody);
		        String sdOutputUrl = bodyJson.getString("sdOutputUrl");
		        String hdOutputUrl = bodyJson.getString("hdOutputUrl");
			
			
			if(s3Linkresult.getStatusCode()==200 || s3Linkresult.getStatusCode()==201) {

				socialVideo.setVideoTitle(videoTitle);
				socialVideo.setVideoCategory(videoCategory);
				socialVideo.setVideoDescription(videoDescription);
//				socialVideo.setVideoLink(s3Linkresult.getData().toString());
				socialVideo.setThumbnailLink(thumbnailUrl.getData().toString());
				socialVideo.setUser(loggedInUser);
				socialVideo.setAgeRestrictionFlag(false);
				socialVideo.setTotalComments(0L);
				socialVideo.setTotalDisLikes(0L);
				socialVideo.setTotalLikes(0L);
				socialVideo.setTotalViews(0L);
				socialVideo.setVideoDuration(duration);
				socialVideo.setActiveFlag(Boolean.TRUE);
				socialVideo.setPlayingFlag(false);
				socialVideo.setVideoRating(0);
				SocialVideo res = socialVideoRepository.save(socialVideo);
				

				socialVideoResolutionRepository.save(new SocialVideoResolution(s3Linkresult.getData().toString(),
						VideoResolution.HIGH.getValue(), res));
				socialVideoResolutionRepository
				.save(new SocialVideoResolution(hdOutputUrl, VideoResolution.MEDIUM.getValue(), res));
				socialVideoResolutionRepository
				.save(new SocialVideoResolution(sdOutputUrl, VideoResolution.LOW.getValue(), res));

//				if (socialVideoUploadDTO.getVideoTags() != null && !socialVideoUploadDTO.getVideoTags().isEmpty()) {
//					socialVideoUploadDTO.getVideoTags().stream().forEach(tag -> {
//
//						TagList tl = tagListRepository.findByTagName(tag);
//
//						if (tl == null) {
//							TagList tempTag = new TagList();
//							tempTag.setTagName(tag);
//
//							TagList resTag = tagListRepository.save(tempTag);
//
//							VideoTag videoTag = new VideoTag();
//							videoTag.setIdTagList(resTag.getIdTagList());
//							videoTag.setIdSocialVideo(res.getIdSocialVideo());
//							videoTagRepository.save(videoTag);
//
//						} else {
//
//							VideoTag videoTag = new VideoTag();
//							videoTag.setIdTagList(tl.getIdTagList());
//							videoTag.setIdSocialVideo(res.getIdSocialVideo());
//							videoTagRepository.save(videoTag);
//						}
//
//					});
//					System.out.println("Tags assigned to video sucessfully.");
//				}
				result.setData(res);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			}else {

				result.setData(null);
				result.setMessage("Could not upload social video");
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}

		} catch (Exception e) {
            e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}

		return result;

	}

	@Override
	public Document<Page<SocialVideoDTO>> getSocialVideoByCategory(Long idCategory,int pageNumber) {

		Document<Page<SocialVideoDTO>> result = new Document<Page<SocialVideoDTO>>();

		try {
			
			Pageable paging = PageRequest.of(pageNumber, 12);
			
			Page<SocialVideoDTO> page = null;

			Page<SocialVideo> list = socialVideoRepository.findByVideoCategory_idVideoCategoryAndActiveFlagOrderByTotalViewsDescCreatedAtDesc(idCategory,
					true,paging);
			
			UserPrincipal userPrincipal = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (list.isEmpty()) 
			{
				int totalPages = list.getTotalPages();
				
				int newPageNo = pageNumber - totalPages < 0 ? 0 : pageNumber - totalPages;
				
				paging = PageRequest.of(newPageNo, 12);
						
				list = socialVideoRepository.
						findByVideoCategory_idVideoCategoryNotAndActiveFlagOrderByCreatedAtDescVideoCategory_idVideoCategoryAsc(idCategory,true,paging);
			}
			
			if(list.isEmpty() && pageNumber > 0)
				throw new NullPointerException("No More Video found for this Category.");
			
			if(list.isEmpty())
				throw new NullPointerException("No Video found for this Category.");
			
			List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
			
			for(SocialVideo sv: list.toList()) 
			{   
				SocialVideoDTO  svDTO = new SocialVideoDTO();
				
				BeanUtils.copyProperties(sv, svDTO);
				
				if (userPrincipal != null) 
				
				{
				 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
				 if (vldl != null) 
				 {
					 BeanUtils.copyProperties(vldl, svDTO); 
				 }
				}
	
				dtoList.add(svDTO);
			}
			
			page = new PageImpl<>(dtoList, paging,list.getTotalElements());


			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<Page<SocialVideoDTO>> getALLSocialVideo(int pageNumber) {

		Document<Page<SocialVideoDTO>> result = new Document<>();

		try {
			Page<SocialVideoDTO> page = null;
			
            UserPrincipal userPrincipal = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			Pageable paging = PageRequest.of(pageNumber, 12);

			Page<SocialVideo> list = socialVideoRepository.findAllByActiveFlagOrderByCreatedAtDesc(true, paging);
			
			

			if (list.isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos Available Right Now!");

			} else if (list.isEmpty())
				throw new NullPointerException("No Video found.");
			else {
				
				List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
				
				for (SocialVideo sv : list.getContent())
				{
					SocialVideoDTO svd = new SocialVideoDTO();
					BeanUtils.copyProperties(sv, svd);
					
					if (userPrincipal != null) 
						
					{
					 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
					 if (vldl != null) 
					 {
						 BeanUtils.copyProperties(vldl, svd); 
					 }
					}
		
					dtoList.add(svd);
				}
				
				page = new PageImpl<>(dtoList, paging,list.getTotalElements());
				
				result.setData(page);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			}

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<List<SocialVideoDTO>> getAllMySocialVideo() {

		Document<List<SocialVideoDTO>> result = new Document<List<SocialVideoDTO>>();
		try {
			List<SocialVideoDTO> page = null;
			


			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");

			List<SocialVideo> list = socialVideoRepository.findByUser_userSurId(userPrincipal.getUserSurId());

			if (list.isEmpty() ) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos !");
			}

			else {
				
               List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
				
				for (SocialVideo sv : list)
				{
					SocialVideoDTO svd = new SocialVideoDTO();
					BeanUtils.copyProperties(sv, svd);
					
					if (userPrincipal != null) 
						
					{
					 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
					 if (vldl != null) 
					 {
						 BeanUtils.copyProperties(vldl, svd); 
					 }
					}
		
					dtoList.add(svd);
				}
				
	

				result.setData(dtoList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			}

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<SocialVideoDTO> getLatestVideo(Long idUser) {
		Document<SocialVideoDTO> result = new Document<SocialVideoDTO>();
		try {
			SocialVideo sv = null;
			UserPrincipal userPrincipal = null;
			
			if (idUser == -1) {
				 sv = socialVideoRepository.findFirstByActiveFlagOrderByCreatedAtDesc(true);

				if (sv == null)
					throw new NullPointerException("No Video Records found");

				

			} else {
				
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!(authentication instanceof AnonymousAuthenticationToken)) {

					userPrincipal = (UserPrincipal) authentication.getPrincipal();

				}

				if (userPrincipal == null)
					throw new AppException("Invalid User");

				 sv = socialVideoRepository
						.findFirstByUser_userSurIdAndActiveFlagOrderByCreatedAtDesc(userPrincipal.getUserSurId(), true);

				if (sv == null)
					throw new NullPointerException("No Video uploaded by user!");

				
			}
			
			SocialVideoDTO svd = new SocialVideoDTO();
			BeanUtils.copyProperties(sv, svd);
			
			if (userPrincipal != null) 
				
			{
			 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
			 if (vldl != null) 
			 {
				 BeanUtils.copyProperties(vldl, svd); 
			 }
			}
			
			result.setData(svd);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;

	}

	@Override
	public Document<SocialVideoDTO> getVideoById(Long idSocialVideo) {

		Document<SocialVideoDTO> result = new Document<SocialVideoDTO>();

		try {

			SocialVideo video = socialVideoRepository.findByidSocialVideoAndActiveFlag(idSocialVideo, true);

			if (video == null) {
				throw new AppException("Invalid Video Id");
			}
			
			
			SocialVideoDTO svd = new SocialVideoDTO();
			video.setApprovedModeratorUserId(null);
			BeanUtils.copyProperties(video, svd);
			
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal != null)
			{
				VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(video.getIdSocialVideo(), userPrincipal.getUserSurId());
				 if (vldl != null) 
				 {
					 BeanUtils.copyProperties(vldl, svd); 
				 }
			}
				
			
			result.setData(svd);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<Page<SocialVideoDTO>> getALLPopularSocialVideo(String city, int pageNumber) {

		Document<Page<SocialVideoDTO>> result = new Document<>();

		try {
			
			Page<SocialVideoDTO> page = null;

			Pageable paging = PageRequest.of(pageNumber, 12);

			Location location = locationRepository.findByCityName(city);
			
			Page<SocialVideo> list = null;

			if (location != null) {
				
				list = socialVideoRepository
						.findByIdLocationAndActiveFlagOrderByTotalViewsDescCreatedAtDesc(location.getIdLocation(), true,
								paging);

				if (list.isEmpty() && pageNumber > 0) {
					result.setData(null);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("No More Videos Available Right Now!");

				}

				else if (list.isEmpty())
					throw new NullPointerException("No Video found.");

			}

			else {

				 list = socialVideoRepository
						.findAllByActiveFlagOrderByTotalViewsDescCreatedAtDesc(true, paging);

				if (list.isEmpty() && pageNumber > 0) {
					result.setData(null);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("No More Videos Available Right Now!");

				}

				else if (list.isEmpty())
					throw new NullPointerException("No Video found.");

			}
			

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}
			
			 List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
				
				for (SocialVideo sv : list.getContent())
				{
					SocialVideoDTO svd = new SocialVideoDTO();
					BeanUtils.copyProperties(sv, svd);
					
					if (userPrincipal != null) 
						
					{
					 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
					 if (vldl != null) 
					 {
						 BeanUtils.copyProperties(vldl, svd); 
					 }
					}
		
					dtoList.add(svd);
				}
				
				page = new PageImpl<>(dtoList, paging,list.getTotalElements());
			
			
			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<Page<SocialVideo>> getALLSocialVideo(int pageNumber, Boolean activeFlag) {
		Document<Page<SocialVideo>> result = new Document<>();

		try {

			Pageable paging = PageRequest.of(pageNumber, 30);

			Page<SocialVideo> list = socialVideoRepository.findAllByActiveFlagOrderByCreatedAtDesc(activeFlag, paging);

			if (list.isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos Available Right Now!");

			} else if (list.isEmpty())
				throw new NullPointerException("No Video found.");
			else {
				result.setData(list);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			}

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<SocialVideo> setVideoByActiveFlagId(ReviewSocialVideoDTO request) {

		Document<SocialVideo> result = new Document<SocialVideo>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			SocialVideo socialVideo = socialVideoRepository.findByIdSocialVideo(request.getIdSocialVideo());

			if (socialVideo == null)
				throw new NullPointerException("invalid  Video Info ");

			if (request.getActiveFlag() && socialVideo.getApprovedModeratorUserId() == null) {
				String description = "Your video '" + socialVideo.getVideoTitle()
						+ "' is verified successfully and available in V-Entertain now! ";
				userNotificationService.createNotifcation("Video verification successful!", description,
						socialVideo.getUser().getUserSurId());
			} else {
				// in case after approving the ,moderator rejected the video
				if (!request.getActiveFlag() && socialVideo.getApprovedModeratorUserId() != null) {
					userNotificationService.createNotifcation("Video verification failed", request.getMessage(),
							socialVideo.getUser().getUserSurId());
				}

			}

			// updating audit information and moderator flag
			socialVideo.setUpdatedAt(Instant.now());
			socialVideo.setActiveFlag(request.getActiveFlag());
			socialVideo.setApprovedModeratorUserId(loggedInUser.getUserSurId());
			SocialVideo response = socialVideoRepository.save(socialVideo);

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;

	}

	@Override
	public Document<SocialVideo> setVideoPlayFlagById(Long idSocialVideo, Boolean activeFlag) {
		Document<SocialVideo> result = new Document<SocialVideo>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			SocialVideo socialVideo = socialVideoRepository.findByIdSocialVideo(idSocialVideo);

			if (socialVideo == null)
				throw new NullPointerException("invalid  Video Info ");
			// updating playing flag
			socialVideo.setPlayingFlag(activeFlag);

			SocialVideo response = socialVideoRepository.save(socialVideo);

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<SocialVideo> getVideoForModeratorById(Long idSocialVideo) {
		Document<SocialVideo> result = new Document<SocialVideo>();

		try {

			SocialVideo socialVideo = socialVideoRepository.findByIdSocialVideo(idSocialVideo);

			if (socialVideo == null) {
				throw new AppException("Invalid Video Id");
			}

			result.setData(socialVideo);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	@Transactional
	public Document<String> deleteSocialVideoByUser(Long idSocialVideo) {

	    Document<String> result = new Document<>();

	    try {
	        SocialVideo socialVideo = socialVideoRepository.findByIdSocialVideo(idSocialVideo);
	        if (socialVideo == null) {
	            throw new AppException("Invalid Video information.");
	        }

	        System.out.println("Deleting Social Video initializing, id Social video:" + socialVideo.getIdSocialVideo());

	        // Deleting file from S3 bucket
	        List<SocialVideoResolution> socialVideoResolutions = 
	        		socialVideoResolutionRepository.findByIdSocialVideo(idSocialVideo);
	        int count=1;
	        for (SocialVideoResolution socialVideoResolution : socialVideoResolutions) {
				 
			        String filePath[] = socialVideoResolution.getVideoLink().split("/");
			        String fileName = filePath[filePath.length - 1];
			        if(count>=1) {
			        	imageUploadService.deleteFileFromS3("vistasocialmedia/" + fileName);
			        }else {
			        	imageUploadService.deleteSocialVideoFromS3("mediaconvert-output/" + fileName);
			        }
			       
			        System.out.println(socialVideoResolution);
			        count++;
			        continue;
			} 
	        System.out.println("Deleted video data from S3 bucket.");	      
	        
	        // Deleting all related entities
	        deleteSocialVideoEntities(socialVideo);

	        // Deleting the social video itself
	        socialVideoRepository.deleteById(idSocialVideo);
	        System.out.println("Social Video Deleted Successfully!");

	        result.setData("Social Video Deleted Successfully!");
	        result.setStatusCode(HttpStatus.OK.value());
	        result.setMessage("Request Successful");

	    } catch (Exception e) {
	        e.printStackTrace();
	        result.setData(null);
	        result.setMessage(e.getLocalizedMessage());
	        result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	    return result;
	}

	private void deleteSocialVideoEntities(SocialVideo socialVideo) {
	    // Deleting all related entities for the social video
	    videoTagRepository.deleteAllByIdSocialVideo(socialVideo.getIdSocialVideo());
	    System.out.println("Deleted Video Tag information.");

	    socialVideoRatingReposirtory.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted Video rating information.");

	    userVideoViewHistoryReposirtory.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted User Video View History information.");

	    videoCommentReposirtory.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted User Video Comment information.");

	    videoViewReposirtory.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted User Video view information.");

	    videoLikeDislikeReposirtory.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted User Video Likes and Dislike information.");
	    
	    socialVideoResolutionRepository.deleteAllBySocialVideo(socialVideo);
	    System.out.println("Deleted resolution.");
	    
	}


	@Override
	@Transactional
	public Document<String> deleteSocialVideoByAdmin(DeleteSocialVideoDTO request) {
		Document<String> result = new Document<String>();

		try {

			SocialVideo socialVideo = null;

			// getting the user object , if user is admin he have access to delete all video
			socialVideo = socialVideoRepository.findByIdSocialVideo(request.getIdSocialVideo());

			if (socialVideo == null)
				throw new AppException("Invalid Video information.");

			System.out.println(
					"Deleting Social Video intalizing By Admin, id Social video:" + socialVideo.getIdSocialVideo());
			// deleting all tag for the social video
			videoTagRepository.deleteAllByIdSocialVideo(socialVideo.getIdSocialVideo());
			System.out.println("Deleted Video Tag information.");
			// deleting video rating records
			socialVideoRatingReposirtory.deleteAllBySocialVideo(socialVideo);
			System.out.println("Deleted Video rating information.");

			// deleting all video user information history
			userVideoViewHistoryReposirtory.deleteAllBySocialVideo(socialVideo);
			System.out.println("Deleted User Video View History informatio.");

			// deleting all video user comment
			videoCommentReposirtory.deleteAllBySocialVideo(socialVideo);
			System.out.println("Deleted User Video Comment information.");

			// deleting all video view
			videoViewReposirtory.deleteAllBySocialVideo(socialVideo);
			System.out.println("Deleted User Video view information.");

			// deleting all video like and disLikes
			videoLikeDislikeReposirtory.deleteAllBySocialVideo(socialVideo);
			System.out.println("Deleted User Video Likes and Dislike information.");

			// deleting social video information
			socialVideoRepository.deleteById(socialVideo.getIdSocialVideo());

			// creating notification for the user
			userNotificationService.createNotifcation("Your video is removed form V-entertain.", request.getMessage(),
					socialVideo.getUser().getUserSurId());
			System.out.println("Social Video Deleted Sucessfully!");

			// deleting file form s3 bucket

			String filePath[] = socialVideo.getVideoLink().split("/");
			String fileName = filePath[filePath.length - 1];

			imageUploadService.deleteFileFromS3("vistasocialmedia/" + fileName);
			System.out.println("Deleted video data from s3 bucket.");

			result.setData("Social Video Deleted Sucessfully!");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	@Transactional
	public void deleteSocialVideoByScheduler()

	{
		
		List<String> infoList = new ArrayList<String>();

		try {
			Instant startTime, endTime;

			startTime = Instant.now();
			// Threshold of 7 days before from current date.
			Instant date = Instant.now().minus(Period.ofDays(7));

			infoList.add("SocialVideo inactive records clean up scheduler started at: " + startTime + " .");
			infoList.add("Looking for inactive records before the date " + date + " .");

			List<SocialVideo> inActiveVideoList = socialVideoRepository.findAllByActiveFlagAndUpdatedAtLessThan(false,
					date);

			if (inActiveVideoList.isEmpty()) {
				infoList.add("No inactive records found.");
			}

			else {
				infoList.add("Total number of inactive record founded:" + inActiveVideoList.size() + " .");

				int unDeleteCount = 0;

				for (SocialVideo socialVideo : inActiveVideoList) {
					try {

						infoList.add("Deleting Social video id: " + socialVideo.getIdSocialVideo() + " started.");

						// deleting all tag for the social video
						videoTagRepository.deleteAllByIdSocialVideo(socialVideo.getIdSocialVideo());

						// deleting video rating records
						socialVideoRatingReposirtory.deleteAllBySocialVideo(socialVideo);

						// deleting all video user information history
						userVideoViewHistoryReposirtory.deleteAllBySocialVideo(socialVideo);

						// deleting all video user comment
						videoCommentReposirtory.deleteAllBySocialVideo(socialVideo);

						// deleting all video view
						videoViewReposirtory.deleteAllBySocialVideo(socialVideo);

						// deleting all video like and disLikes
						videoLikeDislikeReposirtory.deleteAllBySocialVideo(socialVideo);

						// deleting social video information
						socialVideoRepository.deleteById(socialVideo.getIdSocialVideo());

						// creating notification for the user
						String describ = "Dear User,\n \n " + "Your video '" + socialVideo.getVideoTitle()
								+ "' , uploaded on:  " + new SimpleDateFormat("dd/MMM/yyyy").format(Date.from(socialVideo.getCreatedAt()))
								+ " has crossed maximum allocated date for verification. Therefore this video record will be removed from the system automatically.\n \n";
						userNotificationService.createNotifcation("Your video verification failed.", describ,
								socialVideo.getUser().getUserSurId());

						// deleting file form s3 bucket

						String filePath[] = socialVideo.getVideoLink().split("/");
						String fileName = filePath[filePath.length - 1];

						imageUploadService.deleteFileFromS3("vistasocialmedia/" + fileName);

						infoList.add("Deleting Social video id: " + socialVideo.getIdSocialVideo()
								+ " Completed Successfully!");
					}

					catch (Exception e) {
						infoList.add(
								"Erro occured on while deleting social video id: " + socialVideo.getIdSocialVideo());
						infoList.add(e.getLocalizedMessage());
						infoList.add("------------ skipping the above video due to error -------------");
						unDeleteCount++;
						continue;
					}
				}

				infoList.add(
						"Total number of inactive video deleted: " + (inActiveVideoList.size() - unDeleteCount) + ".");
				infoList.add("Total number of failure occured during deletion: " + unDeleteCount + ".");

			}
			endTime = Instant.now();

			infoList.add("Cleaning up data completed!");
			infoList.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		

		}

		catch (Exception e) {
			infoList.add("scheduler failed abnormally at: " + Instant.now());
			infoList.add(e.getLocalizedMessage());
			
		}

		finally {
	
			String fileName = "Studentsocialvideocleanup_" + Instant.now() + ".txt";
				try(FileWriter writer = new FileWriter(fileName)) {

					for (String str : infoList) {
						writer.write(str + System.lineSeparator());
					}
					writer.close();

					File file = new File(fileName);

					fileUploadService.uploadFileToS3Bucket("/logs/scheduler/social-video-cleanup", fileName, file);

					boolean isDeletedFile = file.delete();
					log.info("file deleted from the system : " +isDeletedFile );

				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage());
				}
			}
	}

	@Override
	public Document<Page<SocialVideoDTO>> getAllMySocialVideoForLanding(int pageNumber) {
		Document<Page<SocialVideoDTO>> result = new Document<Page<SocialVideoDTO>>();
		try {
    
			Page<SocialVideoDTO> page = null;
			
			Pageable paging = PageRequest.of(pageNumber, 12);

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");

			Page<SocialVideo> list = socialVideoRepository.findByUser_userSurIdAndActiveFlag(userPrincipal.getUserSurId(), true, paging);

			if (list.isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos !");
			}

			else if (list.isEmpty())
				throw new NullPointerException("No Video found for user.");

				 List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
					
					for (SocialVideo sv : list.getContent())
					{
						SocialVideoDTO svd = new SocialVideoDTO();
						BeanUtils.copyProperties(sv, svd);
						
						if (userPrincipal != null) 
							
						{
						 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
						 if (vldl != null) 
						 {
							 BeanUtils.copyProperties(vldl, svd); 
						 }
						}
			
						dtoList.add(svd);
					}
					
					page = new PageImpl<>(dtoList, paging,list.getTotalElements());
					

					result.setData(page);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Request Sucessfull");
			

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	
	
	
	
	@Transactional
	public Document<SocialVideo> updateSocialVideo(Long idSocialVideo, String videoTitle, String videoDescription,
			Long idVideoCategory, Integer duration, MultipartFile video, MultipartFile thumbnail) {

		// TODO Auto-generated method stub
		
		Document<SocialVideo> result = new Document<SocialVideo>();
		SocialVideo existingSocialVideo = socialVideoRepository.findByIdSocialVideo(idSocialVideo);

		UserPrincipal userPrincipal = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}

		if (userPrincipal == null)
			throw new AppException("Invalid User");

		User user = new User();
		user.setUserSurId(userPrincipal.getUserSurId());

		 Document<String> newVideoLink=null;
		
		if(video!=null)
		{
		      
			newVideoLink = uploadSocialVideo(video);  
			existingSocialVideo.setVideoDuration(duration);
		}

		if(thumbnail!=null)
		{
		    Document<String> newThumbnailUrl = uploadThumbnail(thumbnail);
		    existingSocialVideo.setThumbnailLink(newThumbnailUrl.getData().toString());
		}
		
		try {
			if (existingSocialVideo != null) {
				VideoCategory videoCateogory = videoCategoryRepository.findByIdVideoCategory(idVideoCategory);
				System.out.println("CATEGORY" + videoCateogory.getCategory());
				SocialVideo newSocialVideo = new SocialVideo(videoTitle, null,existingSocialVideo.getThumbnailLink(),
						videoDescription, user, videoCateogory, existingSocialVideo.getVideoRating(),
						existingSocialVideo.getTotalLikes(), existingSocialVideo.getTotalDisLikes(),
						existingSocialVideo.getTotalComments(), existingSocialVideo.getTotalViews(),
						existingSocialVideo.getVideoDuration(), false, 1L);
				newSocialVideo.setIdSocialVideo(idSocialVideo);
				newSocialVideo.setActiveFlag(true);
				newSocialVideo.setCreatedAt(existingSocialVideo.getCreatedAt());
				newSocialVideo.setCreatedBy(existingSocialVideo.getCreatedBy());

				SocialVideo updatedSocialVideo = socialVideoRepository.save(newSocialVideo);

				if(video!=null) {
					
				socialVideoResolutionRepository.deleteAllBySocialVideo(existingSocialVideo);
					
				String outPut = mediaConvert(newVideoLink.getData().toString());

				JSONObject jsonResponse = new JSONObject(outPut);
				String responseBody = jsonResponse.getString("body");

				JSONObject bodyJson = new JSONObject(responseBody);
				String sdOutputUrl = bodyJson.getString("sdOutputUrl");
				String hdOutputUrl = bodyJson.getString("hdOutputUrl");

				
				socialVideoResolutionRepository.save(new SocialVideoResolution(newVideoLink.getData().toString(),
						VideoResolution.HIGH.getValue(), updatedSocialVideo));
				socialVideoResolutionRepository.save(
						new SocialVideoResolution(hdOutputUrl, VideoResolution.MEDIUM.getValue(), updatedSocialVideo));
				socialVideoResolutionRepository.save(
						new SocialVideoResolution(sdOutputUrl, VideoResolution.LOW.getValue(), updatedSocialVideo));

				}
				result.setData(updatedSocialVideo);
				result.setMessage("Successfully updated");
				result.setStatusCode(200);

			} else {
				throw new AppException("No Social video found");

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<String> uploadSocialVideo(MultipartFile socialVideoFile) {
		// TODO Auto-generated method stub
		Document<String> result = new Document<String>();

		File tempFile =fileUploadService. convertMultiPartFileToFile(socialVideoFile);
		try {

			String random = Long.toString(System.currentTimeMillis());

			String extension = "";

			int i = tempFile.getName().lastIndexOf('.');
			if (i > 0) {
				extension = tempFile.getName().substring(i + 1);
			}

			String getUrl = fileUploadService.uploadFileToS3Bucket( "/vistasocialmedia",
					random + "." + extension,tempFile);

			result.setData(getUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		} finally {
			boolean isDeletedTempFile = tempFile.delete();
			log.info("Temp file deleted from the system : " +isDeletedTempFile );
		}
		return result;
	}

	@Override
	public Document<VideoView> saveSocialVideoLog(Long idSocialVideo,boolean completeFlag) {
		Document<VideoView> result=new Document<VideoView>();
		try {        
		SocialVideo	socialVideo	=socialVideoRepository.findByIdSocialVideo(idSocialVideo);
		
		if(socialVideo==null)
		throw new AppException("Invalid Social Video Id.");
		
		UserPrincipal userPrincipal = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			 userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

		if (userPrincipal == null)
			throw new AppException("Invalid User");
		
		VideoView  videoView =videoViewReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(idSocialVideo,userPrincipal.getUserSurId());
		
		if(videoView==null) {
			VideoView videoViewNew=new VideoView();
			BeanUtils.copyProperties(socialVideo, videoViewNew);
			videoViewNew.setSocialVideo(socialVideo);
			videoViewNew.setIdVlUser(userPrincipal.getUserSurId());
			videoViewNew.setCompleteFlag(Boolean.FALSE);
			if(completeFlag)
				videoViewNew.setCompleteFlag(Boolean.TRUE);

			videoView=	videoViewReposirtory.save(videoViewNew);
		}else {
			if(Boolean.FALSE.equals(videoView.getCompleteFlag()))
					{
				if(completeFlag)
					videoView.setCompleteFlag(Boolean.TRUE);

			}else{
				if(!completeFlag)
					videoView.setCompleteFlag(Boolean.FALSE);
			}		
			videoView=	videoViewReposirtory.save(videoView);
		}
		
		result.setData(videoView);
		result.setStatusCode(HttpStatus.OK.value());
		result.setMessage("Request Sucessfull");
			
			
		
		}catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@Override
	public Document<Page<SocialVideoResponseDTO>> getSocialVideos(int pageNumber, int pageSize) {
	
		Document<Page<SocialVideoResponseDTO>> result = new Document<Page<SocialVideoResponseDTO>>();
		try {
			
			
			Page<SocialVideoResponseDTO> page = null;
			
			Pageable paging = PageRequest.of(pageNumber, pageSize);

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
				
			List<SocialVideoResponseDTO> socialVideos =socialVideoRepository.getSocialVideos(userPrincipal.getUserSurId(),paging,Boolean.TRUE);
			
			socialVideos.forEach(socialVideo -> {
			    List<SocialVideoResolution> socialVideoResolutionList = socialVideoResolutionRepository.findByIdSocialVideo(socialVideo.getIdSocialVideo());
			    socialVideo.setVideoLinks(socialVideoResolutionList);
			    socialVideo.setCompletionFlag(Optional.ofNullable(socialVideo.isCompletionFlag()).orElse(false));
			    socialVideo.setComments(videoCommentReposirtory.findCommentsBySocialVideoId(socialVideo.getIdSocialVideo(),Boolean.TRUE));
			    socialVideo.setLikeFlag(socialVideo.getLikeFlag()==null?Boolean.FALSE:socialVideo.getLikeFlag());
			    socialVideo.setDisLikeFlag(socialVideo.getDisLikeFlag()==null?Boolean.FALSE:socialVideo.getDisLikeFlag());
			});
			
			page = new PageImpl<>(socialVideos, paging, socialVideos.size());

           
				result.setData(page);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			

		} catch (Exception e) {
			e.printStackTrace();
        	result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
		
	}



	public String mediaConvert(String originalVideoUrl) {
		 // Initialize AWS Lambda client

        
        
        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessId, secretKey)))
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        // Prepare input payload for the Lambda function
        String inputUrl = originalVideoUrl;
      
        String outputBucket = "https://vlearning-prod.s3.ap-south-1.amazonaws.com";

        // Create input payload as JSON string
  
        String inputPayload = "{\n" +
                "  \"queryStringParameters\": {\n" +
                "    \"inputUrl\": \"" + inputUrl + "\",\n" +
                "    \"outputBucket\": \"" + outputBucket + "\"\n" +
                "  }\n" +
                "}";;
        
        System.out.println(inputPayload);
        // Create invoke request
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName("arn:aws:lambda:ap-south-1:753548365490:function:Socialvideo-convert")
                .withPayload(ByteBuffer.wrap(inputPayload.getBytes()));
        String functionOutput=null;

        try {
            // Invoke the Lambda function
            InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
                                   
            // Process the invoke result
            functionOutput = new String(invokeResult.getPayload().array());
            System.out.println("Lambda function output: " + functionOutput);
        } catch (Exception e) {
            // Handle exceptions
            System.err.println("Error invoking Lambda function: " + e.getMessage());
        }
        return functionOutput;
		
	}
	
	public Document<String> uploadThumbnail(MultipartFile socialVideoThumbnail) {
		Document<String> result = new Document<String>();
		try {
			String uniqueFile = "social_video" + "_" + Long.toString(System.currentTimeMillis());
			File file = imageUploadService.convertMultiPartFileToFile(socialVideoThumbnail);
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "mediaconvert-output/social-video-thumbnails",
					uniqueFile + "." + extension);
			boolean isDeletedFile = file.delete();
			log.info("Logs file deleted from the system : "+isDeletedFile );
			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Thumbnail Uploaded Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<String> updateSocialVideoStatus(Long idSocialVideo, Boolean status) {
		Document<String> result = new Document<String>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			SocialVideo socialVideo = socialVideoRepository.findByIdSocialVideo(idSocialVideo);

			if (socialVideo == null)
				throw new NullPointerException("invalid  Video Info ");


			// updating audit information and moderator flag
			socialVideo.setUpdatedAt(Instant.now());
			socialVideo.setActiveFlag(status);
			socialVideo.setApprovedModeratorUserId(loggedInUser.getUserSurId());
			socialVideoRepository.save(socialVideo);

			result.setData(status==true?"Enabled Successfully":"Disabled Successfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception e) {
            e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}

	@Override
	public Document<Page<SocialVideoDTO>> socialVideoFilter(SocialVideoFilterDTO socialVideoFilterDTO, String sort,
			int pageNumber, int size) {
		Document<Page<SocialVideoDTO>> result = new Document<>();

		try {
			Page<SocialVideoDTO> page = null;
			
            UserPrincipal userPrincipal = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			
			Sort.Direction sortDirection = Sort.Direction.fromString(sort);
		  
			Pageable paging = PageRequest.of(pageNumber, size, Sort.by(sortDirection, "createdAt"));

			Page<SocialVideo> list = socialVideoRepository.socialVideoFilter(socialVideoFilterDTO.getIdVlUser(), socialVideoFilterDTO.getVideoCategory(), socialVideoFilterDTO.getFrom(), socialVideoFilterDTO.getTo(), socialVideoFilterDTO.getActiveFlag(), paging);
			
			

			if (list.isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos Available Right Now!");

			} else if (list.isEmpty())
				throw new NullPointerException("No Video found.");
			else {
				
				List<SocialVideoDTO> dtoList = new ArrayList<SocialVideoDTO>();
				
				for (SocialVideo sv : list.getContent())
				{
					SocialVideoDTO svd = new SocialVideoDTO();
					BeanUtils.copyProperties(sv, svd);
					
					if (userPrincipal != null) 
						
					{
					 VideoLikeDislike vldl =videoLikeDislikeReposirtory.findBySocialVideo_idSocialVideoAndIdVlUser(sv.getIdSocialVideo(), userPrincipal.getUserSurId());
					 if (vldl != null) 
					 {
						 BeanUtils.copyProperties(vldl, svd); 
					 }
					}
		
					dtoList.add(svd);
				}
				
				page = new PageImpl<>(dtoList, paging,list.getTotalElements());
				
				result.setData(page);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			}

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return result;
		}

		return result;
	}
	


}
