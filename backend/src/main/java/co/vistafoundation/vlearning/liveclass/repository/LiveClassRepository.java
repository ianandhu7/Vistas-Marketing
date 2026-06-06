package co.vistafoundation.vlearning.liveclass.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.liveclass.model.LiveClass; 

/**
 * 
 * @author Sajini
 *
 */

public interface LiveClassRepository extends JpaRepository<LiveClass, Long> {

	public List<LiveClass> findAllByLiveCompletionFlagAndIdLiveClassCategoryAndIdClassStandardAndActiveFlag(Boolean completionFlag,
			Long idLiveClassCategory, Long IdClassStandard,Boolean activeFlag);

	public List<LiveClass> findAllByLiveCompletionFlagAndIdLiveClassCategoryAndActiveFlag(Boolean completionFlag,
			Long idLiveClassCategory,Boolean activeFlag);

//	it will fetch the first live-class record for today's date after current time;
	public LiveClass findFirstByLiveCompletionFlagAndClassDateAndActiveFlagAndFromTimeAfter(Boolean completionFlag,
			LocalDate classDate,Boolean activeFlag, LocalTime fromTime);

//	it will fetch the first live-class record for upcoming date;
	public LiveClass findFirstByClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(LocalDate classDate,
			LocalTime fromTime, Boolean completionFlag,Boolean activeFlag);

	@Query(value="select * from live_class l where l.idlive_class =:idLiveClass and l.active_flag=:activeFlag",nativeQuery = true)
	public LiveClass findByIdLiveClassAndActiveFlag(Long idLiveClass,Boolean activeFlag);

	public List<LiveClass> findByIdTeacherAndActiveFlag(Long idTeacher, Boolean activeFlag);

	@Query(value = "SELECT * from live_class where idteacher=:idTeacher and class_date=:date  and active_flag=:activeFlag", nativeQuery=true)
	public List<LiveClass> findByIdTeacherAndClassDateAndActiveFlag(Long idTeacher, LocalDate date,Boolean activeFlag);

	public List<LiveClass> findByClassDateAndActiveFlag(LocalDate date, Boolean activeFlag);

	public List<LiveClass> findByIdTeacherAndLiveCompletionFlagAndActiveFlag(Long idTeacher, Boolean completionFlag, Boolean activeFlag);

	// it will fetch the first live class record for today's date
	public LiveClass findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
			Long idClassStandard, Boolean completionFlag, Boolean currentRunningFlag, LocalDate classDate,Boolean activeFlag);

	// it will fetch All Academic live class record for today's date
	public List<LiveClass> findByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
			Long idClassStandard, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag,
			LocalDate classDate, Boolean activeFlag);

	// it will fetch All Academic live-class record for today's date after current
	// time;
	public List<LiveClass> findByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndClassDateAndCurrentRunningFlagAndActiveFlag(
			Long idClassStandard, Long idLiveClassCategory, Boolean completionFlag, LocalDate classDate,
			Boolean currentRunningFlag,Boolean activeFlag);

	// it will fetch All Academic live-class record for upcoming date;
	public List<LiveClass> findByIdClassStandardAndIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(
			Long idClassStandard, Long idLiveClassCategory, LocalDate classDate, LocalTime fromTime,
			Boolean completionFlag,Boolean activeFlag);

	// it will fetch All ExtraCurricular live class record for today's date
	public List<LiveClass> findByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
			Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag, LocalDate classDate, Boolean activeFlag);

	// it will fetch All ExtraCurricular live-class record for today's date after
	// current time;
	public List<LiveClass> findByIdLiveClassCategoryAndLiveCompletionFlagAndClassDateAndActiveFlagAndFromTimeAfter(
			Long idLiveClassCategory, Boolean completionFlag, LocalDate classDate,Boolean activeFlag, LocalTime fromTime);

	// it will fetch All ExtraCurricular live-class record for upcoming date;
	public List<LiveClass> findByIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(
			Long idLiveClassCategory, LocalDate classDate, LocalTime fromTime, Boolean completionFlag,Boolean activeFlag);

	public List<LiveClass> findByIdClassStandardAndActiveFlag(Long idClassStandard,Boolean activeFlag);

	public List<LiveClass> findByIdLiveClassCategoryAndActiveFlag(Long idLiveClassCategory,Boolean activeFlag);

	// it will fetch all broadcasted video
	public List<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInOrderByClassDateAsc(Boolean completionFlag, Long idLanguage,Boolean activeFlag,
			List<Long> idClassStandards);

	public List<LiveClass> findTOP10ByClassDateGreaterThanEqualAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(
			LocalDate classDate, Boolean completionFlag,Boolean activeFlag);

	public List<LiveClass> findTOP10ByClassDateGreaterThanEqualAndIdClassStandardInAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(
			LocalDate classDate, List<Long> idClassStandards, Boolean completionFlag,Boolean activeFlag);

	public LiveClass findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
			Long idClassStandard, Boolean completionFlag, Boolean currentRunningFlag,Boolean activeFlag, LocalDate classDate);

	public LiveClass findFirstByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateAndIdLanguageIn(Long idLiveClassCategory,
			Boolean currentRunningFlag,Boolean activeFlag,LocalDate classDate, List<Long> idLanguage);

	public LiveClass findFirstByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndIdLanguageInAndClassDateLessThanEqualOrderByClassDateDesc(
			Long idLiveClassCategory, Boolean liveCompletionFlag, Boolean currentRunningFlag, Boolean activeFlag,List<Long> idLanguage, LocalDate classDate);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageAndActiveFlag(
			Long idClassStandard, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag,
			LocalDate classDate, Long idLanguage,Boolean activeFlag);
	
	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag
	(Long idClassStandard,Long idLiveClassCategory,Boolean liveCompletionFlag, Boolean currentRunningFlag, LocalDate classDate,Boolean activeFlag);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
			Long idClassStandard, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag,Long idLanguage,
			LocalDate classDate,Boolean activeFlag);
	
	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
			Long idClassStandard, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag,
			LocalDate classDate,Boolean activeFlag);

	public List<LiveClass> findByCurrentRunningFlagAndLiveCompletionFlagAndActiveFlag(boolean currentRunningFlag, boolean liveCompletionFlag,Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(
			LocalDate classDate, Long idClassStandard, Long idLiveClassCategory, Boolean liveCompletionFlag, Boolean activeFlag);

	public LiveClass findFirstByLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagOrderByIdClassStandard(
			Boolean completionFlag, Boolean currentRunningFlag, LocalDate classDate,Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscIdClassStandardAscCurrentRunningFlagDesc(
			LocalDate classDate, Long idLiveClassCategory, Boolean liveCompletionFlag,Boolean activeFlag);

	public LiveClass findFirstByLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDescIdClassStandardAsc(
			Boolean completionFlag, Boolean currentRunningFlag, Boolean activeFlag, LocalDate classDate);

	public List<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateAsc(Boolean liveCompletionFlag, Long idLanguage,Boolean activeFlag);

	public List<LiveClass> findByClassDateAndLiveCompletionFlagAndActiveFlagAndFromTimeNotNull(LocalDate date, Boolean falg,Boolean activeFlag);
	
	//Query for getting All Academic Live Classes
	
	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlag(LocalDate classDate, Long idClassStandard, Long idLiveClassCategory, Boolean currentRunningFlag,Long idLanguage,Boolean activeFlag);
	
	//Query for getting All Academic upcoming Classes
	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateAsc
	(LocalDate classDate, Long idClassStandard, Long idLiveClassCategory, Boolean CurrentRunningFlag, Boolean LiveCompletionFlag,Long idLanguage,Boolean activeFlag);
	
	//Before Logging in Academic Live
	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagOrderByIdClassStandardAsc(Long idLiveClassCategory, Boolean CurrentRunningFlag, Long idLanguage,Boolean activeFlag);
	
	//Before Logging in Academic Upcoming
	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByIdClassStandardAsc(LocalDate classDate, Long idLiveClassCategory, Boolean CurrentRunningFlag, Boolean liveCompletionFlag,Long idLanguage,Boolean activeFlag);

	//After Login Academic Live
	public List<LiveClass> findByIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlag(Long idClassStandard,Long idLiveClassCategory, Boolean CurrentRunningFlag,Boolean activeFlag);
	
//	AFter Login Academic Upcoming
//	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagOrderByClassDateAsc(LocalDate classDate, Long idClassStandard, Long idLiveClassCategory, Boolean CurrentRunningFlag, Boolean liveCompletionFlag);
	
//	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagOrderByIdClassStandardAsc(LocalDate classDate,Long idLiveClassCategory,Boolean currentRunningFlag, Boolean LiveCompletionFlag);

	public List<LiveClass> findByIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlag(Long idClassStandard,Long idLiveClassCategory, Boolean CurrentRunningFlag, Long idLanguage,Boolean activeFlag);

	//Before login in by clssStd
	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndIdClassStandardEquals(Long idLiveClassCategory, Boolean CurrentRunningFlag, Long idLanguage,Boolean activeFlag, Long idClassStandard);

	//Before Login Upcoming by class 
	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndActiveFlag(LocalDate classDate, Long idLiveClassCategory, Boolean CurrentRunningFlag, Boolean liveCompletionFlag,Long idLanguage, Long idClassStandard,Boolean activeFlag);

	public List<LiveClass> findTOP10ByClassDateGreaterThanEqualAndIdClassStandardInAndLiveCompletionFlagAndActiveFlagAndIdLanguageInOrderByClassDateAscCurrentRunningFlagDesc(
			LocalDate now, List<Long> idClassStandards, Boolean false1,Boolean activeFlag, List<Long> lanLists);

	/*	--------------------------------------------------Subject based Live class------------------------------------------------------ */
	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageAndIdSubjectAndActiveFlag(
			Long classStdId, Long idLiveClassCategory, Boolean liveCompletionFlag, Boolean currentRunningFlag, LocalDate now, Long idLanguage,Long idSubject,Boolean activeFlag);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean liveCompletionFlag, Boolean currentRunningFlag, Long idLanguage, Long idSubject,Boolean activeFlag,
			LocalDate now);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlag(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean currentRunningFlag, Long idLanguage ,Long idSubject,Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean currentRunningFlag, Boolean liveCompletionFlag, Long idLanguage,Long idSubject,Boolean activeFlag);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardInOrderByClassDateAsc(
			Boolean liveCompletionFlag, Long idLanguage, Long idSubject,Boolean activeFlag, List<Long> idClassStandards,Pageable paging);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardEquals(
			Long idLiveClassCategory, Boolean currentRunningFlag, Long idLanguage, Long idSubject,Boolean activeFlag, Long idClassStandard);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndIdSubjectAndActiveFlag(
			LocalDate now, Long idLiveClassCategory, Boolean CurrentRunningFlag, Boolean liveCompletionFlag, Long idLanguage,
			Long idClassStandard, Long idSubject,Boolean activeFlag);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByClassDateDesc(Boolean liveCompletion,
			Long idLanguage, Long idSubject,Boolean activeFlag,Pageable paging);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean currentRunningFlag, Long idLanguage, Long idSubject,Boolean activeFlag);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdClassStandardAndActiveFlag(
			Long idLiveClassCategory, Boolean currentRunningFlag, Long idLanguage, Long idClassStandard,Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean currentRunningFlag, Boolean liveCompletionFla, Long idLanguage, Long idSubject, Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean liveCompletionFlag, Long idLanguage, Boolean activeFlag);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean liveCompletionFlag, Long idLanguage, Long idSubject,
			Boolean activeFlag);


	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageOrderByClassDateAsc(Boolean completionFlag,
			Boolean activeFlag, Long idLanguage,Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInOrderByClassDateAsc(
			Boolean liveCompletionFlag, Long idLanguage, Boolean activeFlag, List<Long> idClassStandards,Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageOrderByClassDateDesc(Boolean true1,
			Boolean liveCompletionFlag, Long idLanguage, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardOrderByClassDateDesc(
			Boolean true1, Boolean true2, Long idLanguage, Long idClassStandard, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardAndIdSubjectOrderByClassDateDesc(
			Boolean true1, Boolean true2, Long idLanguage, Long idClassStandard, Long idSubject, Pageable paging);
	

	public List<LiveClass> findAllByIdTeacherAndActiveFlag(Long idTeacher,Boolean activeFlag);

//	public List<LiveClass> findByClassDateAndActiveFlag(LocalDate date,Boolean activeFlag);

	public List<LiveClass> findByIdTeacherAndClassDate(Long idTeacher, LocalDate date);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, List<Long> idClassStandards, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardInOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Long idSubject, Boolean true2, List<Long> idClassStandards,
			Pageable paging);

	@Query(value="select * from live_class l where l.class_date =:date and l.active_flag=:activeFlag order by l.class_date desc ",nativeQuery = true)
	public List<LiveClass> findByClassDateAndActiveFlagOrderByClassDateDesc(LocalDate date,Boolean activeFlag);

	@Query(value="select * from live_class l where l.idteacher =:idTeacher and l.active_flag=:activeFlag order by l.class_date desc ",nativeQuery = true)
	public List<LiveClass> findAllByIdTeacherAndActiveFlagOrderByClassDateDesc(Long idTeacher,Boolean activeFlag);

	public List<LiveClass> findByIdTeacherAndClassDateAndActiveFlagOrderByClassDate(Long idTeacher, LocalDate date,Boolean activeFlag);


	public List<LiveClass> findAllByActiveFlag(Boolean flag);

	@Query(value="select * from live_class l where l.class_date =:date and l.active_flag=:activeFlag and l.idteacher=:idTeacher  order by l.class_date desc ",nativeQuery = true)
	public List<LiveClass> findByIdTeacherAndClassDateAndActiveFlagOrderByClassDateDesc(Long idTeacher, LocalDate date,Boolean activeFlag);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, Long idLanguage, Boolean true2,
			LocalDate now);

	public List<LiveClass> findAllByActiveFlagAndLiveClassURLIsNotNull(Boolean true1);

	@Query(value = "(SELECT * from live_class  where current_running_flag=1 and active_flag=1 and class_type= 'premium' and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and idlive_category=1 and class_date = CURDATE() and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) union (select * from live_class where current_running_flag=0 and live_completion_flag=0 and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and active_flag=1 and class_type= 'premium' and idlive_category=1 and class_date >= CURDATE() and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) union (SELECT * from live_class where current_running_flag=0 and live_completion_flag=1 and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and idlive_category=1 and active_flag =1 and class_type= 'premium' and idsyllabus = :idSyllabus and idstate = :idState order by class_date DESC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> get10ClassesByIdClassStandard(List<Long> idClassStandards,List<Long> lanLists,Long idSyllabus,Long idState);
	
	
	@Query(value = "(SELECT * from live_class where current_running_flag=1 and active_flag=1 and class_type= 'premium' and idlive_category=1 and class_date =CURDATE() order by class_date ASC,idclass_standard ASC limit 10) union (select * from live_class where current_running_flag=0 and live_completion_flag=0 and active_flag=1 and class_type= 'premium' and idlive_category=1 and class_date >= CURDATE() order by class_date ASC,idclass_standard ASC limit 10) union (SELECT * from live_class  where current_running_flag=0 and live_completion_flag=1 and idlive_category=1 and active_flag=1 and class_type= 'premium' order by class_date DESC,idclass_standard ASC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> get10Classes();
	
	@Query(value = "(SELECT * from live_class where current_running_flag=1 and"
			+ " active_flag=1 and class_type='premium' and idlive_category=1 and"
			+ " idclass_standard =:idClassStandard and idlanguage =:lanLists and "
			+ "class_date =CURDATE() order by class_date ASC,idclass_standard ASC limit 10)"
			+ " union (select * from live_class where current_running_flag=0 and live_completion_flag=0 "
			+ "and active_flag=1 and class_type='premium' and idlive_category=1 and idclass_standard =:idClassStandard and"
			+ " idSubject = :idSubject and idlanguage =:lanLists and class_date >= CURDATE() "
			+ "order by class_date ASC,idclass_standard ASC limit 10) union "
			+ "(SELECT * from live_class  where current_running_flag=0 and live_completion_flag=1 "
			+ "and idlive_category=1 and idclass_standard =:idClassStandard and idSubject = :idSubject"
			+ " and active_flag=1 and class_type='premium' and idlanguage =:lanLists order by "
			+ "class_date DESC,idclass_standard ASC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> getAcademicLiveByClassWithoutSubject(Long idClassStandard,Long lanLists);
	
	@Query(value = "(SELECT * from live_class where current_running_flag=1 and active_flag=1 and class_type= 'premium' "
			+ "and idlive_category=1 and idclass_standard =:idClassStandard and idSubject = :idSubject "
			+ "and idlanguage =:lanLists and idsyllabus = :idSyllabus and idstate = :idState and  "
			+ "class_date =CURDATE() order by class_date ASC,idclass_standard ASC limit 10) union "
			+ "(select * from live_class where current_running_flag=0 and live_completion_flag=0 and "
			+ "active_flag=1 and class_type='premium' and idlive_category=1 and idclass_standard =:idClassStandard and "
			+ "idSubject = :idSubject and idlanguage =:lanLists and idsyllabus = :idSyllabus "
			+ "and idstate = :idState and class_date >= CURDATE() order by class_date ASC,idclass_standard ASC limit 10) "
			+ "union (SELECT * from live_class  where current_running_flag=0 and live_completion_flag=1 and "
			+ "idlive_category=1 and active_flag=1 and class_type='premium' and idclass_standard =:idClassStandard and idSubject = :idSubject "
			+ "and idlanguage =:lanLists and idsyllabus = :idSyllabus and idstate = :idState  "
			+ "order by class_date DESC,idclass_standard ASC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> getAcademicLiveByClass(Long idClassStandard ,Long idSubject,Long lanLists, Long idSyllabus, Long idState);
	
	@Query(value = "select all_live_class.class_date class_date,all_live_class.totalLiveClasses totalLiveClasses, "
			+ "case when student_live_class.studentAttendedCount is NULL THEN 0 else student_live_class.studentAttendedCount "
			+ "end studentAttendedCount FROM ( select DATE(l.class_date) class_date, count(*) totalLiveClasses from live_class l "
			+ "where l.live_completion_flag = 1 and l.idclass_standard in (:idClassStandard,4) "
			+ "group by l.class_date)all_live_class LEFT JOIN (select DATE(ula.created_at) "
			+ "class_date,count(1) studentAttendedCount from user_live_class_attended ula "
			+ "where idvl_user = :idVlUser group by DATE(ula.created_at)) student_live_class "
			+ "ON DATE(all_live_class.class_date) =  DATE(student_live_class.class_date) order by class_date desc limit 10;" , nativeQuery=true)
	public List<Object[]> getStudentLiveClassAttendedCountinfo(Long idClassStandard , Long idVlUser);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Boolean activeFlag, LocalDate todayDate);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndClassDate(
			Long idLiveClassCategory, Boolean runningFlag, Long idLanguage, Long idClassStandard, Boolean activeFlag,
			LocalDate todayDate);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean runningFlag, Long idLanguage, Long idSubject, Boolean activeFlag, LocalDate todayDate);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDate(
			Long idLiveClassCategory, Boolean runningFlag, Long idLanguage, Long idSubject, Boolean activeFlag,
			Long idClassStandard, LocalDate todayDate);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageOrderByClassDateDescIdClassStandardAsc(
			Boolean true1, Boolean true2, Long idLanguage, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByClassDateDescIdClassStandardAsc(
			Boolean true1, Long idLanguage, Long idSubject, Boolean true2, Pageable paging);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Boolean true2, LocalDate now);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdClassStandardAndActiveFlagAndClassDate(
			Long idLiveClassCategory, Boolean true1, Long idClassStandard, Boolean true2, LocalDate now);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndClassDateOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Long idSubject, Boolean true2, LocalDate now);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDate(
			Long idLiveClassCategory, Boolean true1, Long idSubject, Boolean true2, Long idClassStandard,
			LocalDate now);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndActiveFlagOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Boolean true1);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndActiveFlag(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idClassStandard,
			Boolean true1);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdSubjectAndActiveFlagOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idSubject, Boolean true1);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndIdSubjectAndActiveFlag(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idClassStandard,
			Long idSubject, Boolean true1);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlag(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists,
			Long idSubject, Boolean true2);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlag(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists,
			Boolean true2);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndActiveFlagOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Boolean true1);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndIdSubjectAndActiveFlagOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Long idSubject, Boolean true1);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Boolean true2, LocalDate now, Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndClassDateAndIdSyllabus(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idClassStandard, Boolean true2,
			LocalDate now, Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idSubject, Boolean true2, LocalDate now,
			Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDateAndIdSyllabus(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idSubject, Boolean true2,
			Long idClassStandard, LocalDate now, Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage, Boolean true1,
			Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndIdSyllabus(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Long idClassStandard, Boolean true1, Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage, Long idSubject,
			Boolean true1, Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndIdSubjectAndActiveFlagAndIdSyllabus(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Long idClassStandard, Long idSubject, Boolean true1, Long idSyllabus);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdSyllabusOrderByClassDateDescIdClassStandardAsc(
			Boolean true1, Boolean true2, Long idLanguage, Long idSyllabus, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Boolean true2, Long idLanguage, Long idClassStandard, Long idSyllabus, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusOrderByClassDateDescIdClassStandardAsc(
			Boolean true1, Long idLanguage, Long idSubject, Boolean true2, Long idSyllabus, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardAndIdSubjectAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Boolean true2, Long idLanguage, Long idClassStandard, Long idSubject, Long idSyllabus,
			Pageable paging);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Boolean true1, Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndActiveFlagAndIdSyllabus(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idClassStandard,
			Boolean true1, Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdSubjectAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idSubject, Boolean true1,
			Long idSyllabus);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndIdSubjectAndActiveFlagAndIdSyllabus(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idClassStandard,
			Long idSubject, Boolean true1, Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Boolean true2, LocalDate now, Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdClassStandardAndActiveFlagAndClassDateAndIdSyllabus(
			Long idLiveClassCategory, Boolean true1, Long idClassStandard, Boolean true2, LocalDate now,
			Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(
			Long idLiveClassCategory, Boolean true1, Long idSubject, Boolean true2, LocalDate now, Long idSyllabus);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDateAndIdSyllabus(
			Long idLiveClassCategory, Boolean true1, Long idSubject, Boolean true2, Long idClassStandard, LocalDate now,
			Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabus(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Long idLanguage,
			Long idSubject, Boolean true2, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, Long idLanguage, Long idSubject,
			Boolean true2, LocalDate now, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageAndActiveFlagAndIdSyllabus(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Long idLanguage,
			Boolean true2, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, Long idLanguage, Boolean true2,
			LocalDate now, Long idSyllabus);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndIdSyllabus(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, Long idLanguage, Boolean true2,
			Long syllabusId);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabus(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idSubject,
			Boolean true2, Long syllabusId);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Boolean true1, Long syllabusId);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Long idSubject, Boolean true1, Long syllabusId);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, List<Long> idClassStandards, Long idSyllabus,
			Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardInAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Long idSubject, Boolean true2, List<Long> idClassStandards, Long idSyllabus,
			Pageable paging);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndIdSyllabus(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists, Boolean true2,
			Long syllabusId);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabus(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists,
			Long idSubject, Boolean true2, Long syllabusId);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndActiveFlagAndIdSyllabusOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Boolean true1, Long syllabusId);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabusOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Long idSubject, Boolean true1, Long syllabusId);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateDesc(Boolean true1,
			Long idLanguage, Boolean true2, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idSubject, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idSyllabus, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdSyllabusOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idSubject, Long idSyllabus, Pageable paging);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandard(
			LocalDate now, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage, Long idSubject,
			Boolean true1, Long idClassStandard);

	public List<LiveClass> findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateAndIdClassStandard(
			Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idSubject, Boolean true2, LocalDate now,
			Long idClassStandard);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdClassStandardInOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idSubject, List<Long> idClassStandards,
			Pageable paging);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabus(
			Long classStdId, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag, LocalDate now,
			ArrayList<Long> idLanguages, Long idSubject, Boolean activeFlag, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag, ArrayList<Long> idLanguages,
			Long idSubject, Boolean activeFlag, LocalDate now, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdLanguageInAndActiveFlagAndIdSyllabus(
			Long classStdId, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag, LocalDate now,
			ArrayList<Long> idLanguages, Boolean activeFlag, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDesc(
			Long classStdId, Long idLiveClassCategory, Boolean completionFlag, Boolean currentRunningFlag, ArrayList<Long> idLanguages,
			Boolean activeFlag, LocalDate now, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdSubjectAndActiveFlagAndIdSyllabusAndIdLanguageInOrderByIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Long idSubject,
			Boolean true2, Long idSyllabus, ArrayList<Long> idLanguages);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusAndIdLanguageInOrderByIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Boolean true2,
			Long idSyllabus, ArrayList<Long> idLanguages);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDescIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, ArrayList<Long> idLanguages,
			Long idSubject, Boolean true2, LocalDate now, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusOrderByClassDateDescIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, ArrayList<Long> idLanguages,
			Boolean true2, LocalDate now, Long idSyllabus);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusAndIdLanguageIn(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Boolean true2,
			Long idSyllabus, ArrayList<Long> idLanguages);

	public List<LiveClass> findByIdTeacherAndClassDateAndActiveFlagAndIdLiveClassNot(Long idTeacher,
			LocalDate classDate, Boolean true1, Long idLiveClass);
	
	
	@Query("select l from LiveClass l "
			+ "where (l.idLiveClassCategory = :idLiveClassCategory) "
			+ "and (:idLanguage is null or l.idLanguage = :idLanguage) "
			+ "and (l.currentRunningFlag = :currentRunningFlag) "
			+ "and (l.liveCompletionFlag = :liveCompletionFlag)"
			+ "and (:idClassStandard is null or l.idClassStandard = :idClassStandard)"
			+ "and (:idSubject is null or l.idSubject = :idSubject)"
			+ "and (:idSyllabus is null or l.idSyllabus = :idSyllabus)"
			+ "and (:idState is null or l.idState = :idState)"
			+ "and (l.activeFlag = :activeFlag) "
			+ "and (l.classDate = :classDate) order by l.idClassStandard ASC"
			)
   public List<LiveClass> getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateOrderbyClassStandardAsc
   (Long idLiveClassCategory, Long idLanguage ,Boolean currentRunningFlag, Boolean liveCompletionFlag, Long idClassStandard, Long idSubject,Long idSyllabus,Long idState,Boolean activeFlag,LocalDate classDate);

	@Query("select l from LiveClass l "
			+ "where (l.idLiveClassCategory = :idLiveClassCategory) "
			+ "and (:idLanguage is null or l.idLanguage = :idLanguage) "
			+ "and (l.currentRunningFlag = :currentRunningFlag) "
			+ "and (l.liveCompletionFlag = :liveCompletionFlag)"
			+ "and (:idClassStandard is null or l.idClassStandard = :idClassStandard)"
			+ "and (:idSubject is null or l.idSubject = :idSubject)"
			+ "and (:idSyllabus is null or l.idSyllabus = :idSyllabus)"
			+ "and (:idState is null or l.idState = :idState)"
			+ "and (l.activeFlag = :activeFlag) "
			+ "and (l.classDate >= :classDate) order by l.classDate ASC , l.idClassStandard ASC"
			)
   public List<LiveClass> getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateGreaterThanorEqualOrderbyClassDateAsc
   (Long idLiveClassCategory, Long idLanguage ,Boolean currentRunningFlag, Boolean liveCompletionFlag, Long idClassStandard, Long idSubject,Long idSyllabus,Long idState,Boolean activeFlag,LocalDate classDate);
	

	@Query("select l from LiveClass l "
			+ "where (l.idLiveClassCategory = :idLiveClassCategory) "
			+ "and (l.idLanguage = :idLanguage) "
			+ "and (l.currentRunningFlag = :currentRunningFlag) "
			+ "and (l.liveCompletionFlag = :liveCompletionFlag)"
			+ "and (:idClassStandard is null or l.idClassStandard = :idClassStandard)"
			+ "and (:idSubject is null or l.idSubject = :idSubject)"
			+ "and (:idSyllabus is null or l.idSyllabus = :idSyllabus)"
			+ "and (:idState is null or l.idState = :idState)"
			+ "and (l.activeFlag = :activeFlag) "
			+ "order by l.classDate Desc , l.idClassStandard ASC"
			)
   public Page<LiveClass> getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagWithPaging
   (Long idLiveClassCategory, Long idLanguage ,Boolean currentRunningFlag, Boolean liveCompletionFlag, Long idClassStandard, Long idSubject,Long idSyllabus,Long idState,Boolean activeFlag,Pageable paging);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdSubjectAndActiveFlagAndIdSyllabusAndIdLanguageInAndIdStateOrderByIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Long idSubject,
			Boolean true2, Long idSyllabus, ArrayList<Long> idLanguages, Long idState);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, ArrayList<Long> idLanguages,
			Long idSubject, Boolean true2, LocalDate now, Long idSyllabus, Long idState);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusAndIdLanguageInAndIdStateOrderByIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean true1, LocalDate now, Boolean true2,
			Long idSyllabus, ArrayList<Long> idLanguages, Long idState);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
			Long classStdId, Long idLiveClassCategory, Boolean true1, Boolean false1, ArrayList<Long> idLanguages,
			Boolean true2, LocalDate now, Long idSyllabus, Long idState);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndIdSyllabusAndIdState(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, Long idLanguage, Boolean true2,
			Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusAndIdState(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, Long idLanguage, Long idSubject,
			Boolean true2, Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Boolean true1, Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2, Long idLanguage,
			Long idSubject, Boolean true1, Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndIdSyllabusAndIdState(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists, Boolean true2,
			Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabusAndIdState(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean true1, List<Long> lanLists,
			Long idSubject, Boolean true2, Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Boolean true1, Long syllabusId, Long idState);

	public List<LiveClass> findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(
			LocalDate now, Long classStdId, Long idLiveClassCategory, Boolean false1, Boolean false2,
			List<Long> lanLists, Long idSubject, Boolean true1, Long syllabusId, Long idState);

	public LiveClass findFirstByLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdLiveClassCategoryInOrderByIdClassStandard(
			Boolean false1, Boolean true1, LocalDate now, Boolean true2, List<Long> idLiveClassCategoryList);


	public LiveClass findFirstByLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualAndIdLiveClassCategoryInOrderByClassDateDescIdClassStandardAsc(
			Boolean true1, Boolean false1, Boolean true2, LocalDate now, List<Long> idLiveClassCategoryList);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdSubjectAndActiveFlagAndIdSyllabusAndIdLanguageInAndIdStateOrderByIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Long idSubject, Boolean true2, Long idSyllabus, ArrayList<Long> idLanguages, Long idState);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean true1, Boolean false1,
			ArrayList<Long> idLanguages, Long idSubject, Boolean true2, LocalDate now, Long idSyllabus, Long idState);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusAndIdLanguageInAndIdStateOrderByIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Boolean true2, Long idSyllabus, ArrayList<Long> idLanguages, Long idState);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean true1, Boolean false1,
			ArrayList<Long> idLanguages, Boolean true2, LocalDate now, Long idSyllabus, Long idState);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdSubjectAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Long idSubject, Boolean true2, ArrayList<Long> idSyllabusList, ArrayList<Long> idLanguages,
			ArrayList<Long> idStates);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusInAndIdStateInOrderByClassDateDescIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean true1, Boolean false1,
			ArrayList<Long> idLanguages, Long idSubject, Boolean true2, LocalDate now, ArrayList<Long> idSyllabusList,
			ArrayList<Long> idStates);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Boolean true2, ArrayList<Long> idSyllabusList, ArrayList<Long> idLanguages,
			ArrayList<Long> idStates);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusInAndIdStateInOrderByClassDateDescIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean true1, Boolean false1,
			ArrayList<Long> idLanguages, Boolean true2, LocalDate now, ArrayList<Long> idSyllabusList,
			ArrayList<Long> idStates);

	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLanguageDescIdLiveClassCategoryAsc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Boolean true2, ArrayList<Long> idSyllabusList, ArrayList<Long> idLanguages,
			ArrayList<Long> idStates);


	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdLiveClassCategoryOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idSubject, Long idLiveClassCategory, Pageable paging);

	public Page<LiveClass> findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdLiveClassCategoryOrderByClassDateDesc(
			Boolean true1, Long idLanguage, Boolean true2, Long idLiveClassCategory, Pageable paging);
	
	@Query(value = "(SELECT * from live_class where current_running_flag=1 and active_flag=1 and class_type='premium' "
			+ "and idlive_category=2 and idclass_standard =:idClassStandard and idSubject = :idSubject "
			+ "and idlanguage =:lanLists and idsyllabus = :idSyllabus and class_date =CURDATE() order by "
			+ "class_date ASC,idclass_standard ASC limit 10) union (select * from live_class "
			+ "where current_running_flag=0 and live_completion_flag=0 and active_flag=1 and  and class_type='premium' "
			+ "idlive_category=2 and idclass_standard =:idClassStandard and idSubject = :idSubject "
			+ "and idlanguage =:lanLists and idsyllabus = :idSyllabus and class_date >= CURDATE() "
			+ "order by class_date ASC,idclass_standard ASC limit 10) union "
			+ "(SELECT * from live_class  where current_running_flag=0 and live_completion_flag=1 and "
			+ "active_flag=1 and class_type='premium' and idlive_category=2 and idclass_standard =:idClassStandard and "
			+ "idSubject = :idSubject and idlanguage =:lanLists and idsyllabus = :idSyllabus "
			+ "order by class_date DESC,idclass_standard ASC limit 10)limit 10 ", nativeQuery = true)
	public List<LiveClass> getExtraCurLiveByClass(Long idClassStandard, Long idSubject, Long lanLists, Long idSyllabus);
	
//	@Query(value = "(SELECT * from live_class where current_running_flag=1 and active_flag=1 and idlive_category=2 and class_date =CURDATE() order by class_date ASC,idclass_standard ASC limit 10) union (select * from live_class where current_running_flag=0 and live_completion_flag=0 and active_flag=1 and idlive_category=2 and class_date >= CURDATE() order by class_date ASC,idclass_standard ASC limit 10) union (SELECT * from live_class  where current_running_flag=0 and live_completion_flag=1 and idlive_category=2 and active_flag=1 order by class_date DESC,idclass_standard ASC limit 10)limit 10 ", nativeQuery = true)
	
//	@Query(value = "(SELECT * from live_class  where current_running_flag=1 and active_flag=1 and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and idlive_category=1 and class_date = CURDATE() and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) union (select * from live_class where current_running_flag=0 and live_completion_flag=0 and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and active_flag=1 and idlive_category=1 and class_date >= CURDATE() and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) union (SELECT * from live_class where current_running_flag=0 and live_completion_flag=1 and idclass_standard IN :idClassStandards and idlanguage IN :lanLists and idlive_category=1 and active_flag =1 and idsyllabus = :idSyllabus and idstate = :idState order by class_date DESC limit 10)limit 10 ",nativeQuery = true))
//	public List<LiveClass> get10ExtraCurricularClasses(List<Long> idClassStandards,List<Long> lanLists,Long idSyllabus,Long idState);
	
	@Query(value = "(SELECT * from live_class  where current_running_flag=1 and active_flag=1 and class_type='premium' "
			+ "and idlanguage IN :lanLists and idlive_category=2 and class_date = CURDATE() "
			+ "and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) "
			+ "union (select * from live_class where current_running_flag=0 and live_completion_flag=0 "
			+ "and idlanguage IN :lanLists and active_flag=1 and class_type='premium' and idlive_category=2 and class_date >= CURDATE() "
			+ "and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) "
			+ "union (SELECT * from live_class where current_running_flag=0 and live_completion_flag=1 "
			+ "and idlanguage IN :lanLists and idlive_category=2 and active_flag =1 and class_type='premium' and idsyllabus = :idSyllabus "
			+ "and idstate = :idState order by class_date DESC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> get10ExtraCurricularClasses(List<Long> lanLists,Long idSyllabus,Long idState);



	public LiveClass findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLiveClassCategoryAscIdLanguageDesc(
			ArrayList<Long> idLiveClassStandards, ArrayList<Long> idLiveClassCategorys, Boolean false1, Boolean true1,
			LocalDate now, Boolean true2, ArrayList<Long> idSyllabusList, ArrayList<Long> idLanguages,
			ArrayList<Long> idStates);

	public LiveClass findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
			Long classStdId, long l, Boolean true1, Boolean false1, Long idLanguage, Boolean true2, LocalDate now,
			Long idSyllabus, Long idState);

	public LiveClass findFirstByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateOrderByIdLanguageDesc(
			Long idLiveClassCategory, Boolean true1, Boolean true2, LocalDate now);

	public LiveClass findFirstByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDescIdLanguageDesc(
			Long idLiveClassCategory, Boolean true1, Boolean false1, Boolean true2, LocalDate now);
	
	@Query(value = "(SELECT * from live_class  where current_running_flag=1 and active_flag=1 and class_type='premium' "
			+ "and idclass_standard = :idClassStandard and idlive_category=2 and class_date = CURDATE() "
			+ "and idsyllabus = :idSyllabus and idstate = :idState order by class_date ASC limit 10) "
			+ "union (select * from live_class where current_running_flag=0 and live_completion_flag=0 and "
			+ "idclass_standard  =:idClassStandard and active_flag=1 and class_type='premium' and idlive_category=2 and "
			+ "class_date >= CURDATE() and idsyllabus = :idSyllabus and idstate = :idState order by "
			+ "class_date ASC limit 10) union (SELECT * from live_class where current_running_flag=0 and "
			+ "live_completion_flag=1 and idclass_standard = :idClassStandard and idlive_category=2 and "
			+ "active_flag =1 and class_type='premium' and idsyllabus = :idSyllabus and idstate = :idState order by class_date DESC limit 10)limit 10 ",nativeQuery = true)
	public List<LiveClass> get10ExtraCurricularClassesWithoutLang(Long idSyllabus,Long idState,Long idClassStandard);

	public LiveClass findFirstByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateAndIdLanguageInOrderByIdLanguageDesc(
			Long idLiveClassCategory, Boolean true1, Boolean true2, LocalDate now, List<Long> lanLists);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndIdSyllabusOrderByIdLanguageDesc(
			LocalDate now, Long l, Long idLiveClassCategory, Boolean true1, List<Long> lanLists, Boolean true2, Long m);

	public List<LiveClass> findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndIdSyllabusOrderByIdLanguageDesc(
			LocalDate now, Long l, Long idLiveClassCategory, Boolean true1, Boolean true2, Long m);
	
	@Query(value ="SELECT thumbnail_url from live_class where idlive_class = :idLive", nativeQuery=true)
	public String getThumnailUrlLiveClass(Long idLive);
	
	@Query(value="select * from live_class l where "
			+ "(:idLiveClassCategory is null or l.idlive_category = :idLiveClassCategory) "
			+ "and ( l.idlanguage in :idLanguage) "
			+ "and (:idClassStandard is null or l.idclass_standard = :idClassStandard) "
			+ "and (:idSubject is null or l.idsubject = :idSubject) "
			+ "and (:idSyllabus is null or l.idsyllabus = :idSyllabus) "
			+ "and (:idState is null or l.idstate = :idState) "
			+ "and (:runningFlag is null or l.current_running_flag = :runningFlag) "
			+ "and (:completionFlag is null or l.live_completion_flag = :completionFlag) "
			+ "and l.class_type= 'free' and l.active_flag=1 "
			+ "and l.class_date > :someDate "
			+ " order by l.current_running_flag desc,"
			+ " l.live_completion_flag  asc,"
			+ " l.class_date DESC  ",nativeQuery=true)
	public Page<LiveClass> getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageDesc(Long idClassStandard, Long idLiveClassCategory,
			Long [] idLanguage, Long idSubject, Long idSyllabus, Long idState ,Boolean runningFlag, Boolean completionFlag,LocalDate someDate, Pageable paging  );
	
	
	@Query(value="select * from live_class l where "
			+ "(:idLiveClassCategory is null or l.idlive_category = :idLiveClassCategory) "
			+ "and ( l.idlanguage in :idLanguage) "
			+ "and (:idClassStandard is null or l.idclass_standard = :idClassStandard) "
			+ "and (:idSubject is null or l.idsubject = :idSubject) "
			+ "and (:idSyllabus is null or l.idsyllabus = :idSyllabus) "
			+ "and (:idState is null or l.idstate = :idState) "
			+ "and (:runningFlag is null or l.current_running_flag = :runningFlag) "
			+ "and (:completionFlag is null or l.live_completion_flag = :completionFlag) "
			+ "and l.class_type= 'free' and l.active_flag=1 "
			+ "and l.class_date > :someDate "
			+ " order by "
			+ " l.class_date asc , l.from_time asc ",nativeQuery=true)
	public Page<LiveClass> getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageAsc(Long idClassStandard, Long idLiveClassCategory,
			Long [] idLanguage, Long idSubject, Long idSyllabus, Long idState ,Boolean runningFlag, Boolean completionFlag,LocalDate someDate,  Pageable paging);
	
	
	@Query("SELECT l FROM LiveClass l "
            + "WHERE (:idLiveClassCategory IS NULL OR l.idLiveClassCategory = :idLiveClassCategory) "
            + "    AND (l.idLanguage IN :idLanguage) "
            + "    AND (:idClassStandard IS NULL OR l.idClassStandard = :idClassStandard) "
            + "    AND (:idSubject IS NULL OR l.idSubject = :idSubject) "
            + "    AND (:idSyllabus IS NULL OR l.idSyllabus = :idSyllabus) "
            + "    AND (:idState IS NULL OR l.idState = :idState) "
            + "    AND (:runningFlag IS NULL OR l.currentRunningFlag = :runningFlag) "
            + "    AND (:completionFlag IS NULL OR l.liveCompletionFlag = :completionFlag) "
            + "    AND l.classType = 'free' "
            + "    AND l.activeFlag = 1 "
            + "    AND l.classDate > :someDate "
            + "ORDER BY l.classDate DESC, l.fromTime DESC")
	public Page<LiveClass> getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageByDateTimeDesc(Long idClassStandard, 
		Long idLiveClassCategory, Long[] idLanguage, Long idSubject, Long idSyllabus, Long idState, Boolean runningFlag, Boolean completionFlag, LocalDate someDate, Pageable paging);


	public LiveClass findByIdLiveClass(Long idVideo);

	public List<LiveClass> findByIdSubject(Long idSubject);
	
}

