package co.vistafoundation.vlearning.subscription.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;

import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.NewStreamingSubjectChapterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentChapterListDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.utils.GenerateTime;

@Service
public class StudentSubscriptionServiceImplV4 implements StudentSubscriptionServiceV4{

	

	@Value("#{${listOfMarketingIds}}")
	private List<String> marketingIds;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	GenerateTime generateTime;
	
	@Value("${app.angular.url}")
	private String appAngularUrl;
	
	@Override
	@Cacheable(value = "chapterCache")
	public Document<NewStudentSubscriptionSubjectDTO> getChapterList(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState) {
		

		Document<NewStudentSubscriptionSubjectDTO> result = new Document<>();

		try {


			int count=0;

		

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
							idProductLine, idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found!");

			StudentSubscription ss = null;

			ClassStandard standard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (standard == null)
				throw new NullPointerException("No ClassStandard Found!");

			Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

			if (sub == null)
				throw new NullPointerException("No Subject Found!");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("No Syllabus Found!");


			List<OfflineVideoCourse> ovcFullList = offlineVideoCourseRepository.findByIdProduct(product.getIdProduct());

			Set<Long> uniqueChapterIds = new HashSet<>();

			if (ovcFullList.isEmpty())
				throw new NullPointerException("No Video Data Found for the selected product!");

			List<OfflineVideoCourse> ovcList = new ArrayList<>();

			ovcFullList.stream().forEach(o -> {

				if (!marketingIds.contains(o.getVideoEnLink())) {
					ovcList.add(o);
				}
				uniqueChapterIds.add(o.getIdSubjectChapter());

			});

			count = ovcList.size();


			List<Long> finalChList = new ArrayList<>(uniqueChapterIds);

			List<SubjectChapter> chapters = subjectChapterRepository
					.findByIdSubjectChapterInAndActiveFlagOrderBySortOrder(finalChList,Boolean.TRUE);

			if (chapters.isEmpty())
				throw new NullPointerException("No Chapters Found!");

			List<NewStreamingSubjectChapterDTO> finalStreamingChapters = new ArrayList<>();

			int breakCounter = 0;

			List<SubjectChapter> topTwoChapters = chapters.stream().limit(2).collect(Collectors.toList());

			Map<Long, Long> chaptersVideoCount = ovcList.stream()
					.collect(Collectors.groupingBy((OfflineVideoCourse::getIdSubjectChapter), Collectors.counting()));

			for (SubjectChapter sc : chapters) {
				NewStreamingSubjectChapterDTO sscd = new NewStreamingSubjectChapterDTO();
				
				sscd.setTotalChapterVideos((chaptersVideoCount.get(sc.getIdSubjectChapter())
						== null ? "0" : chaptersVideoCount.get(sc.getIdSubjectChapter()).toString()));
	
				sscd.setChapter(sc);
				int chapterDuration = ovcList.stream()
						.filter(o -> o.getIdSubjectChapter().equals(sc.getIdSubjectChapter()))
						.mapToInt(OfflineVideoCourse::getVideoDuration).sum();

				sscd.setTotalChapterWatchHours(generateTime.generateTimeFromSeconds(chapterDuration));
				sscd.setAccessAllowed(Boolean.FALSE);
				finalStreamingChapters.add(sscd);
			}

			Long totalSubjectCount = chaptersVideoCount.values().stream().reduce(0L, Long::sum);

			NewStudentSubscriptionSubjectDTO sssDTO = new NewStudentSubscriptionSubjectDTO();

			String imageUrl = (appAngularUrl.equals("https://vistaslearning.com") || appAngularUrl.equals("https://student.vistaslearning.com"))
							? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ sub.getIdSubject() + ".webp"
							: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ sub.getIdSubject() + ".webp";
			
			sssDTO.setSubjectThumbnailURL(imageUrl);

			sssDTO.setTotalSubjectwatchHours(generateTime
					.generateTimeFromSeconds(ovcList.stream().mapToInt(OfflineVideoCourse::getVideoDuration).sum()));
			sssDTO.setTotalSubjectVideos(totalSubjectCount.toString());
			sssDTO.setSubjectChapters(finalStreamingChapters);
			sssDTO.setClassStandardName(standard.getClassStandadName());			sssDTO.setSyllabusName(syllabus.getSyllabusName());
		    sssDTO.setSubjectName(sub.getSubjectName());			sssDTO.setIdProduct(product.getIdProduct());
			sssDTO.setPercentageCompletion("");
			
			StudentChapterListDTO stdentChapterListDto= new StudentChapterListDTO();
			stdentChapterListDto.setSubjectChapters(finalStreamingChapters);
			result.setData(sssDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
		

      }
}
