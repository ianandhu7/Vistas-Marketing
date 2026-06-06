/**
 * 
 */
package co.vistafoundation.vlearning.subscription.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subscription.model.VideoStreamingLog;
import co.vistafoundation.vlearning.subscription.repository.VideoStreamingLogRepository;

/**
 * @author NaveenKumar
 *
 */

@Component
public class VideoStreamingLogUtils {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	VideoStreamingLogRepository videoStreamingLogRepository;
	
	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository; 
	
	@Autowired
	SubjectChapterRepository subjectChapterRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(VideoStreamingLogUtils.class);
	
	
	 @Async
		public void videoStreamingLog(VideoStreamingLog videoStreamingLog) {
	    	VideoStreamingLog vsl=null;		
	    	 try {
	    	      
	    		 vsl= videoStreamingLogRepository.getVideoStreamingLog(videoStreamingLog.getIdVlUser(),videoStreamingLog.getIdSubject());
	    	      
	    	   
	    	    	  Product product= productRepository.findByIdProductAndIdProductLineAndActiveFlag(videoStreamingLog.getIdProduct(),5L,Boolean.TRUE);
	        	      String category = (product != null ) ? "academic" : "eca";
	        	      videoStreamingLog.setCategory(category);

	    	      
	    	      if (vsl == null) {
	    				String uniqueString=videoStreamingLog.getIdVlUser()+"#"+videoStreamingLog.getIdSubject();
	    				videoStreamingLog.setIdVideoStreamingLog(uniqueString);
	    				videoStreamingLogRepository.save(videoStreamingLog);
	    				
	    			} else {
	    				videoStreamingLog.setIdVideoStreamingLog(vsl.getIdVideoStreamingLog());
	    				videoStreamingLogRepository.save(videoStreamingLog);
	    			}
	    	      
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}     	
					
		}

	 public VideoStreamingLog nextVideoStreamingLog(VideoStreamingLog videoStreamingLog) {
		 List<OfflineVideoCourse> ovcList=null;	
		 OfflineVideoCourse currentOfflineVideoCourse=null;
		 OfflineVideoCourse nextOfflineVideoCourse=null;
	    	 try {
	    	       
	    		     if(videoStreamingLog.getCategory().equals("academic")) {
	    		    	 
	    	    	   
					ovcList = offlineVideoCourseRepository.findByIdSubjectAndIdSubjectChapterOrderByVideoSeqNumberAsc(
							videoStreamingLog.getIdSubject(), videoStreamingLog.getIdSubjectChapter());

					currentOfflineVideoCourse = offlineVideoCourseRepository
							.findByIdOfflineVideoCourse(videoStreamingLog.getIdOfflineVideoCourse());

					int index = ovcList.indexOf(currentOfflineVideoCourse);
					
					if((index + 1) > (ovcList.size()-1)) {
						
						videoStreamingLog.setIdSubjectChapter(videoStreamingLog.getIdSubjectChapter()+1);
						ovcList = offlineVideoCourseRepository.findByIdSubjectAndIdSubjectChapterOrderByVideoSeqNumberAsc(
								videoStreamingLog.getIdSubject(), videoStreamingLog.getIdSubjectChapter());
						if(!ovcList.isEmpty()) {
						nextOfflineVideoCourse=ovcList.get(0);
						}
					
					}else {
						
						nextOfflineVideoCourse = ovcList.get(index + 1);
					}
					videoStreamingLog.setIdOfflineVideoCourse(nextOfflineVideoCourse.getIdOfflineVideoCourse());
					videoStreamingLog.setVideoCoverageDuration(0);
					videoStreamingLog.setVideoDuration(nextOfflineVideoCourse.getVideoDuration());
					videoStreamingLog.setPctComplete("0");
					videoStreamingLog.setTopic(nextOfflineVideoCourse.getTopic());
					SubjectChapter subjectChapter =subjectChapterRepository.findByIdSubjectChapterAndActiveFlag(nextOfflineVideoCourse.getIdSubjectChapter(),Boolean.TRUE);
					if(subjectChapter==null)
					    throw new  AppException("No Chapter data found for this idSubjectChapter:"+nextOfflineVideoCourse.getIdSubjectChapter());
					videoStreamingLog.setChapterName(subjectChapter.getChapterName());
					videoStreamingLog.setVideoSeqNumber(nextOfflineVideoCourse.getVideoSeqNumber());
	    	 }
	    	       
	    	    
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}     	
	    	 return videoStreamingLog;		
		}

}
