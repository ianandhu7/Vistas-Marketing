package co.vistafoundation.vlearning.discover.videos.Imp;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoCategoryRepository;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoRepository;
import co.vistafoundation.vlearning.discover.videos.services.DiscoverVideoService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;


/**
 * author  Meghana,Sajini
 * 
 * **/

@Service
public class DiscoverVideosImpl implements DiscoverVideoService {
	
	@Autowired
	DiscoverVideoRepository discoverVideoRepository;
	
	@Autowired
	DiscoverVideoCategoryRepository discoverVideoCategoryRepository;
	
	@Autowired
	VideoCipherConfiguration videoCipherConfiguration;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.discover_video_category}")
	private String bucketFolder;
	
	private static final Logger log = LoggerFactory.getLogger(DiscoverVideosImpl.class);
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllfeaturedVideos() {

		Document result = new Document();
		try {
			List<DiscoverVideo> discoverVideolist = discoverVideoRepository.findByFeaturedFlag(true);	
			
			if(discoverVideolist.isEmpty())throw new NullPointerException("No Featured Videos Found!");
			
			result.setData(discoverVideolist);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}
	
	
	@Override
	public Document<List<DiscoverVideo>> getTop10LatestVideos() {

		Document<List<DiscoverVideo>> result = new Document<>();
		try {
			List<DiscoverVideo> latestdiscoverVideolist = discoverVideoRepository.findTOP10ByOrderByUploadedDateDesc();
			
			if(latestdiscoverVideolist.isEmpty())throw new NullPointerException("No Latest videos Found!");
			
			result.setData(latestdiscoverVideolist);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}
	
		
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getDiscoverVideosByCategory(Long idDiscoverVideoCategory) {

		Document result = new Document();
		
		
		try {
			List<DiscoverVideo> latestdiscoverVideolist = discoverVideoRepository.findByIdDiscoverVideoCategory(idDiscoverVideoCategory);
			if(latestdiscoverVideolist.isEmpty()) throw new NullPointerException("No Videos Found!");
			
			result.setData(latestdiscoverVideolist);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}
	
	
	//@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getDiscoverVideosByCategoryandLanguage(Long idDiscoverVideoCategory, String langauge) {

		Document result = new Document();		
		
		try {
			List<DiscoverVideo> latestdiscoverVideolist = discoverVideoRepository.findByIdDiscoverVideoCategoryAndLanguage(idDiscoverVideoCategory, langauge);
			
			if(latestdiscoverVideolist.isEmpty()) throw new NullPointerException("No Videos Found for this Language");
			result.setData(latestdiscoverVideolist);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}
	
	
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Document getAllDiscoverCategories() {

			Document result = new Document();	
			
			try {
				List<DiscoverVideoCategory> category = discoverVideoCategoryRepository.findAll();
				
				if(category.isEmpty()) throw new NullPointerException("No Videos Found");
				result.setData(category);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			}
			catch (Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}

		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Document saveDiscoverVideos(DiscoverVideo discoverVideo) {
			Document result = new Document();
			try {
				if(discoverVideo==null)
					throw new AppException("Video Details Not found!!!");
				
				String body = "{\"ttl\":21600}"; // set video otp expiration for 6 hours
				VideoCipherOTP vco = videoCipherConfiguration.getOTP(discoverVideo.getVideoLink(), body);

//				VideoCipherMeta vcm = videoCipherConfiguration.getVideoMetaData(discoverVideo.getVideoLink());
				
				HashMap<String, Object> vcm = videoCipherConfiguration.getVideoMetaData(discoverVideo.getVideoLink());
				System.out.println(vcm);

				if (vcm == null || vco == null)
					throw new AppException("Invalid VideoId Provided.");
				
				Date uploadedDate = new Date();
				discoverVideo.setUploadedDate(uploadedDate);
				
				discoverVideo.setVideoOtp(vco.getOtp());
				discoverVideo.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
				
//				for (VideoPoster vp :vcm.getPosters())
//				{
//					if (vp.getHeight() == 480)
//					{
//						discoverVideo.setPostarLoc(vp.getUrl());
//					}
//				}
				
				
//				discoverVideo.setPostarLoc(vcm.getPosters().get(0).getUrl());
//				discoverVideo.setVideoDuration(vcm.getDuration());
				discoverVideo.setVideoDuration((int)vcm.get("duration"));
				discoverVideo.setPostarLoc(((HashMap<String, Object>)((List)vcm.get("posters")).get(0)).get("url").toString());
				DiscoverVideo res=discoverVideoRepository.save(discoverVideo);
				result.setData(res);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Video Details Saved Sucessfully");
				return result;
			}
			catch (Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}
		}


		@Override
		@SuppressWarnings({ "rawtypes","unchecked"})
		public Document getAllLanguages() {

			Document result = new Document();		
			try {
				List<String> list = discoverVideoRepository.getLanguages();	
				if(list.isEmpty()) throw new NullPointerException("No Language found");
				result.setData(list);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;

			}
			catch (Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}
		
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Document uploadVideoCategoryImageToS3(MultipartFile file, String categoryName) {
			Document doc = new Document<>();
			try {

				final File tempFile = convertMultiPartFileToFile(file);
				String imageLink = uploadFileToS3Bucket(bucketFolder, tempFile);

				System.err.println("S3 Url==>" + imageLink);
				
				DiscoverVideoCategory discoverVideoCategory = new DiscoverVideoCategory();
				discoverVideoCategory.setCategory(categoryName);
				discoverVideoCategory.setImageLink(imageLink);
				
				DiscoverVideoCategory res = discoverVideoCategoryRepository.save(discoverVideoCategory);

				boolean isDeletedFile = tempFile.delete();
				 log.info("Logs file and batch file deleted from the system : "+isDeletedFile );

				doc.setData(res);
				doc.setMessage("Image Uploaded Successfully");
				doc.setStatusCode(201);
				return doc;
			}
			catch (Exception e) {
				doc.setData(e.getLocalizedMessage());
				doc.setMessage(e.getLocalizedMessage());
				doc.setStatusCode(500);
				return doc;
			}
		}
	
		
		
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Document getAllLatestVideosOnLanguage(String language) {

			Document result = new Document();
			try {
				List<DiscoverVideo> latestdiscoverVideolist = discoverVideoRepository.findTOP10ByLanguageOrderByUploadedDateDesc(language);
				
				if(latestdiscoverVideolist.isEmpty())throw new NullPointerException("No Videos For this language"); 
				result.setData(latestdiscoverVideolist);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;

			}

			catch (Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
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

		private String uploadFileToS3Bucket(final String bucketFolder, final File file) {
			String uniqueFile = file.getName();

			// Object is created in PublicRead mode
			final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketFolder, uniqueFile, file)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			amazonS3.putObject(putObjectRequest);
			String s3Url = amazonS3.getUrl(bucketFolder, uniqueFile).toString();
			return s3Url;
		}


		/**
		 * updated by Sajini
		 * added proper pagination
		 */
		@Override
		public Document<Page<DiscoverVideo>> searchDiscoverVideo(String topic,int pageNumber) {
			Document<Page<DiscoverVideo>> result = new Document<>();
			
			
			try {
				Pageable paging = PageRequest.of(pageNumber, 12);
				
				Page<DiscoverVideo> searchedDiscoverVideolist = discoverVideoRepository.findByTopicContainingIgnoreCase(topic,paging);
				
				if(searchedDiscoverVideolist.isEmpty())
					throw new AppException("Not Result found!!!");
				
				if (pageNumber>0)
					throw new AppException("No  Datas Available.");
				
				result.setData(searchedDiscoverVideolist);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;

			}

			catch (Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}
		}
		
		Specification<DiscoverVideo> forWords(Collection<String> words) {
		    if(words == null || words.isEmpty())
		        throw new RuntimeException("List of words cannot be empty.");

		    return (root, query, builder) -> words.stream()
		            .map(String::toLowerCase)
		            .map(word -> "%" + word + "%")
		            .map(word -> builder.like(builder.lower(root.get("text")), word))
		            .reduce(builder::or)
		            .get();

		}
		
		
//		Latest discover category videos

		@Override
		public Document<List<DiscoverVideo>> getTop10CategoryVideosByLanguage(Long idDiscoverVideoCategory, String language) {
			
			Document<List<DiscoverVideo>> result = new Document<>();
			try {
				List<DiscoverVideo> top10categoryVideoList = discoverVideoRepository.findTOP10ByIdDiscoverVideoCategoryAndLanguageOrderByUploadedDateDesc(idDiscoverVideoCategory, language);
				if(top10categoryVideoList.isEmpty()) throw new NullPointerException("No Latest videos found");
					
				result.setData(top10categoryVideoList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
			
		}

//		with default language Language 

		@Override
		public Document<List<DiscoverVideo>> getTop10LatestCategoryVideos(Long idDiscoverVideoCategory) {
			Document<List<DiscoverVideo>> result = new Document<>();
			try {
				List<DiscoverVideo> top10categoryVideos = discoverVideoRepository.findTOP10ByIdDiscoverVideoCategoryOrderByUploadedDateDesc(idDiscoverVideoCategory);
				if(top10categoryVideos.isEmpty()) throw new NullPointerException("No Latest videos found");
					
				result.setData(top10categoryVideos);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
			
			
		}

//		  See All Latest Videos
		
		@Override
		public Document<List<DiscoverVideo>> getAllLatestCategoryVideos(Long idDiscoverVideoCategory) {
			Document<List<DiscoverVideo>> result = new Document<>();
			try {
			List<DiscoverVideo> allLatestVideos = discoverVideoRepository.findAllByIdDiscoverVideoCategoryOrderByUploadedDateDesc(idDiscoverVideoCategory);

			if(allLatestVideos.isEmpty()) throw new NullPointerException("No Videos Found");
			
			result.setData(allLatestVideos);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
		}

		//All category Videos-10nos
		@Override
		public Document<List<DiscoverVideo>> getTop10CategoryVideos(Long idDiscoverVideoCategory) {
			
			Document<List<DiscoverVideo>> result = new Document<>();
			try {
				List<DiscoverVideo> allCategoryVideos10 = discoverVideoRepository.findTop10ByIdDiscoverVideoCategory(idDiscoverVideoCategory);
				if(allCategoryVideos10.isEmpty())throw new NullPointerException("No Videos Found");
				
				result.setData(allCategoryVideos10);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
			
		}

// See All Videos
		@Override
		public Document<Page<DiscoverVideo>> getAllCategoryVideos(Long idDiscoverVideoCategory,int pageNumber, String language) {
			Document<Page<DiscoverVideo>> result = new Document<>();
			try {
				Pageable paging = PageRequest.of(pageNumber, 12);
				Page<DiscoverVideo> allCategoryVideos =null;
				
				if(language.equalsIgnoreCase("all")) {
					allCategoryVideos = discoverVideoRepository.findAllByIdDiscoverVideoCategory(idDiscoverVideoCategory,paging);
				}
				else {
					allCategoryVideos = discoverVideoRepository.findAllByIdDiscoverVideoCategoryAndLanguage(idDiscoverVideoCategory,language, paging);
				}
						
				if(allCategoryVideos ==null) {
					throw new NullPointerException("No Videos Found");
				}
				
				if (pageNumber>0 && allCategoryVideos.isEmpty())
					throw new AppException("No More Videos Available for this category.");
					
				if(allCategoryVideos.isEmpty())throw new NullPointerException("No Videos Found");
				
				result.setData(allCategoryVideos);
				
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
			
			
		}


		@Override
		public Document<Page<DiscoverVideo>> getSimilarVideo(Long idDiscoverVideoCategory,int pageNumber) {
			
			Document<Page<DiscoverVideo>> result = new Document<>();
			try {
				Pageable paging = PageRequest.of(pageNumber, 12);
				Page<DiscoverVideo> allCategoryBasedVideos = discoverVideoRepository.findAllByIdDiscoverVideoCategory(idDiscoverVideoCategory,paging);
				
				if (pageNumber>0 && allCategoryBasedVideos.isEmpty())
					throw new AppException("No More Videos Available for this category.");
					
				if(allCategoryBasedVideos.isEmpty())throw new NullPointerException("No Videos Found");
				
				
				result.setData(allCategoryBasedVideos);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;				
			}
		}


		@Override
		public Document<DiscoverVideoCategory> getDiscoverCategoryById(Long idDiscoverVideoCategory) {
			Document<DiscoverVideoCategory> result = new Document<>();
			try {
			DiscoverVideoCategory dvc = discoverVideoCategoryRepository.findByIdDiscoverVideoCategory(idDiscoverVideoCategory);
			if(dvc == null) throw new NullPointerException("No category id found");
			
			
			result.setData(dvc);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
			} 
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;	
				
			}
			
		}

		@Override
		public Document<DiscoverVideo> getDiscoverVideoById(Long idDiscoverVideo) {
			
			Document<DiscoverVideo> result = new Document<>();
			try {
				DiscoverVideo discoverVideo = discoverVideoRepository.findByIdDiscoverVideo(idDiscoverVideo);
				if(discoverVideo==null)
					throw new NullPointerException("No Video Found");
				discoverVideo.setVideoOtp(createCourseVideoOtp(discoverVideo.getVideoLink()));
				
				result.setData(discoverVideo);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
				
			}
			catch(Exception exp) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
				
			}
		}
		

		public String createCourseVideoOtp(String videoId) {

			VideoCipherOTP result = new VideoCipherOTP();
			VideoCipherOTP vco = new VideoCipherOTP();
			
			try {
				
				UserPrincipal userPrincipal = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					 userPrincipal = (UserPrincipal) authentication.getPrincipal();
					}
				   Gson gson = new Gson();
					
					  // Constructing the JSON string with the necessary fields
					 Map<String, Object> annotation = new HashMap<>();
				        annotation.put("type", "text");
				        annotation.put("text", "vistaslearning.com");
				        annotation.put("x", "5");
				        annotation.put("y", "15");
				        annotation.put("alpha", "0.25");
				        annotation.put("color", "#ffffff");
				        annotation.put("size", "15");


				        Map<String, Object> otpParameters = new HashMap<>();
				        otpParameters.put("ttl", 21600);  // set video otp expiration for 6 hours
				        otpParameters.put("userId", userPrincipal.getUserSurId());
				        otpParameters.put("annotate", gson.toJson(new Map[]{annotation}));

				        String jsonBody = gson.toJson(otpParameters);
				
				vco = videoCipherConfiguration.getOTP(videoId, jsonBody);

				
			}

			catch (Exception exp) {
	           	exp.printStackTrace();
			}

			return vco.getOtp();
		}

		
		
}
