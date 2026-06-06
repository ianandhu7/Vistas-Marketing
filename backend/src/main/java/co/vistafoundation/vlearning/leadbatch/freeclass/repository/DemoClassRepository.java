/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassTimeDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClass;

/**
 * @author vk
 *
 */
public interface DemoClassRepository extends JpaRepository<DemoClass, Long> {
   /**
    * Query updated for adding id state.
    * @author Naveen Kumar 
    */
	@Query(value = "select new co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassDTO(dc.idDemoClass, dc.idTeacher, t.teacherName,"
			+ "    dc.idSubject, sb.subjectName, dc.idClassStandard, cs.classStandadName,"
			+ "	 dc.idSyllabus, s.syllabusName, dc.classScheduleDate, dc.classConductedFlag, dc.fromTime, dc.toTime,"
			+ "	 dc.maxStudents, dc.totalStudentEnrolled, dc.idSubjectChapter, sc.chapterName, dc.activeFlag, dc.description,st.idState,st.state) "
			+ "    from DemoClass dc " + "    inner join Teacher t on dc.idTeacher=t.idTeacher"
			+ "    inner join Syllabus s on dc.idSyllabus=s.idSyllabus"
			+ "    inner join Subject sb on dc.idSubject=sb.idSubject"
			+ "    inner join ClassStandard cs on dc.idClassStandard=cs.idClassStandard"
			+ "    inner join SubjectChapter sc on dc.idSubjectChapter=sc.idSubjectChapter"
			+"     inner join State st on dc.idState=st.idState" 
			+ "    where dc.idTeacher=:idTeacher")
	public List<DemoClassDTO> findDemoClassDetails(Long idTeacher);

	/**
	    * Query updated for adding id state.
	    * @author Naveen Kumar 
	    */
	@Query(value = "select new co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassDTO(dc.idDemoClass, dc.idTeacher, t.teacherName," + 
			"    dc.idSubject, sb.subjectName, dc.idClassStandard, cs.classStandadName," + 
			"	 dc.idSyllabus, s.syllabusName, dc.classScheduleDate, dc.classConductedFlag, dc.fromTime, dc.toTime,"+ 
			"	 dc.maxStudents, dc.totalStudentEnrolled, dc.idSubjectChapter, sc.chapterName, dc.activeFlag, dc.description,st.idState,st.state) " + 
			"    from DemoClass dc " + 
			"    inner join Teacher t on dc.idTeacher=t.idTeacher" + 
			"    inner join Syllabus s on dc.idSyllabus=s.idSyllabus" + 
			"    inner join Subject sb on dc.idSubject=sb.idSubject" + 
			"    inner join ClassStandard cs on dc.idClassStandard=cs.idClassStandard" + 
			"    inner join SubjectChapter sc on dc.idSubjectChapter=sc.idSubjectChapter" + 
			"    inner join State st on dc.idState=st.idState" + 
			"    where dc.idTeacher=:idTeacher" + 
			" 	 and dc.idClassStandard=:idClassStandard"+
			" 	 and dc.idSyllabus=:idSyllabus"+
			" 	 and dc.idSubject=:idSubject"+
			" 	 and dc.idSubjectChapter=:idSubjectChapter"+
			" 	 and dc.activeFlag=:activeFlag"+
			" 	 and dc.idState=:idState")
	public List<DemoClassDTO> findDemoClassList(Long idTeacher, Long idClassStandard, Long idSyllabus, Long idSubject, Long idSubjectChapter, Boolean activeFlag,Long idState);

	public DemoClass findByIdDemoClass(Long idDemoClass);

	public List<DemoClass> findByIdSubjectAndIdClassStandardAndIdSyllabusAndIdSubjectChapterAndClassScheduleDate(
			Long idSubject, Long idClassStandard, Long idSyllabus, Long idSubjectChapter, LocalDate classScheduleDate);

	public boolean existsByClassScheduleDateAndFromTimeAndToTime(LocalDate classScheduleDate, LocalTime fromTime,
			LocalTime toTime);

	@Query(value = "select new co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassTimeDTO(concat(fromTime) as fromTime, \r\n"
			+ "concat(toTime) as toTime) from DemoClass where classScheduleDate=:classScheduleDate and idTeacher=:idTeacher", nativeQuery = false)
	public List<DemoClassTimeDTO> customQueryGetTimes(LocalDate classScheduleDate, Long idTeacher);

	List<DemoClass> findByIdSubjectAndIdClassStandardAndIdSyllabusAndIdSubjectChapter(Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idSubjectChapter);

	@Query(value = "Select dc from DemoClass dc where dc.idClassStandard = :idClassStandard and dc.idSyllabus= :idSyllabus and dc.idSubjectChapter=:idSubjectChapter and dc.idSubject=:idSubject and dc.activeFlag = TRUE and dc.totalStudentEnrolled< dc.maxStudents and dc.classScheduleDate = :classScheduleDate")
	List<DemoClass> getAllDemoClasses(Long idSubject, Long idClassStandard, Long idSyllabus, Long idSubjectChapter,
			LocalDate classScheduleDate);

	public DemoClass findFirstByClassConductedFlagAndClassScheduleDateAndActiveFlagAndFromTimeAfter(Boolean false1,
			LocalDate now, Boolean false2, LocalTime now2);

	public DemoClass findFirstByClassScheduleDateAfterAndFromTimeAfterAndClassConductedFlagAndActiveFlag(LocalDate now,
			LocalTime parse, Boolean false1, Boolean false2);

	public List<DemoClass> findByIdTeacherAndClassConductedFlagAndActiveFlag(Long idTeacher, Boolean classConductedFlag, Boolean activeFlag);
	
	
}
