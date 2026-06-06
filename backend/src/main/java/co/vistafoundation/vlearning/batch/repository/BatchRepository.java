package co.vistafoundation.vlearning.batch.repository;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;

/**
 * @author NaveenKumar
 * 
 **/
public interface BatchRepository extends JpaRepository<Batch, Long> {

	List<Batch> findByIdProduct(Long productId);

	List<Batch> findByIdProductAndIdTeacher(Long idProduct, Long idTeacher);

	List<Batch> findByIdProductAndIdTeacherAndBatchFromTime(Long idProduct, Long idTeacher, LocalTime fromTime);

	List<Batch> findByIdProductAndIdTeacherAndBatchToTime(Long idProduct, Long idTeacher, LocalTime toTime);

	List<Batch> findByIdProductAndIdTeacherAndBatchFromTimeAndBatchToTime(Long idProduct, Long idTeacher,
			LocalTime fromTime, LocalTime toTime);

	List<Batch> findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(Long idProduct, Long idTeacher,
			Long IdDayOfWeekCode);

	List<Batch> findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(Long idProduct,
			Long idTeacher, Long IdDayOfWeekCode, LocalTime fromTime);

	List<Batch> findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndAndBatchToTime(Long idProduct,
			Long idTeacher, Long IdDayOfWeekCode, LocalTime toTime);

	List<Batch> findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(
			Long idProduct, Long idTeacher, Long IdDayOfWeekCode, LocalTime fromTime, LocalTime toTime);

	List<Batch> findByIdProductAndDayOfWeekCode_idDayofWeekCode(Long productId, Long IdDayOfWeekCode);

	List<Batch> findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(Long productId, Long IdDayOfWeekCode,
			LocalTime fromTime);

	List<Batch> findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchToTime(Long productId, Long IdDayOfWeekCode,
			LocalTime toTime);

	List<Batch> findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(Long productId,
			Long IdDayOfWeekCode, LocalTime fromTime, LocalTime toTime);

	List<Batch> findByIdProductAndBatchFromTime(Long productId, LocalTime fromTime);

	List<Batch> findByIdProductAndBatchToTime(Long productId, LocalTime toTime);

	List<Batch> findByIdProductAndBatchFromTimeAndBatchToTime(Long productId, LocalTime fromTime, LocalTime toTime);

	List<Batch> findByIdTeacherAndDayOfWeekCode_idDayofWeekCode(Long idTeacher, Long IdDayOfWeekCode);

	List<Batch> findByIdTeacher(Long idTeacher);
	
	List<Batch> findByIdTeacherAndActiveFlagAndCurrentVacancyGreaterThan(Long idTeacher,Boolean activeFlag , int currentVacancy);
	
	@Query("Select b from Batch b JOIN Product p ON (b.idProduct=p.idProduct) where b.idTeacher=:idTeacher and b.activeFlag=:activeFlag and b.currentVacancy>:currentVacancy and p.idClassStandard=:idClassStandard")
	List<Batch> findByTeacherByClassStandard(Long idTeacher,Boolean activeFlag , int currentVacancy, Long idClassStandard);

	Batch findByIdBatch(Long idBatch);

	@Query(value = "select b from Batch b where b.idBatch=:idBatch")
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "120000")})
	Batch findBatchAvailabilityAndUpdate(Long idBatch);

	List<Batch> findByIdProductAndBatchFromTimeAndBatchToTimeAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(
			Long idProduct, LocalTime fromTime, LocalTime toTime, Long idTeacher, Long idDayofWeekCode);

	List<Batch> findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndIdTeacher(Long productId,Long idDayofWeekCode, Long idTeacher);

	/**
	 * @author Sajini
	 * @param idTeacher
	 * @return
	 */

	List<Batch> findAllBatchesByIdTeacher(Long idTeacher);

	/**
	 * @author Naveen Kumar 
	 * @param idProduct
	 * @param idTeacher
	 * @param batchFromTime
	 * @param batchToTime
	 * @return
	 */
	@Query("select b from Batch b "
			+ "where (b.idProduct = :idProduct)"
			+ "and (:idTeacher is null or b.idTeacher = :idTeacher)"
			+ "and (:dayOfWeekCode is null or b.dayOfWeekCode = :dayOfWeekCode)"
			+ "and (:batchFromTime is null or b.batchFromTime = :batchFromTime)"
			+ "and (:batchToTime is null or b.batchToTime = :batchToTime)"
			)
	List<Batch> findPersonalAcademicBatch(Long idProduct, Long idTeacher,DayOfWeekCode dayOfWeekCode, LocalTime batchFromTime, LocalTime  batchToTime);

	List<Batch> findByIdTeacherAndActiveFlag(Long idTeacher, Boolean flag);
	
	List<Batch> findByIdProductAndIdTeacherAndActiveFlagAndCurrentVacancyGreaterThan(Long idProduct, Long idTeacher,Boolean activeFlag , int CurrentVacancy);
	
	List<Batch> findByIdProductAndActiveFlagAndCurrentVacancyGreaterThan(Long idProduct,Boolean activeFlag , int CurrentVacancy);
	
	List<Batch> findAllByOrderByCreatedAtDesc();

	List<Batch> findByActiveFlagOrderByCreatedAtDesc(Boolean flag);

	Batch findByIdBatchAndActiveFlag(Long idBatch, Boolean flag );
	
	@Query("select b from Batch b, UserCart uc\r\n" + 
			"where b.idBatch = uc.idBatch\r\n" + 
			" and b.paymentStatus = :paymentStatus\r\n" + 
			" and uc.updatedBy = :userSurId\r\n" + 
			" and b.updatedBy = :userSurId" +
			" and b.idBatch= :idBatch" +
			" and uc.idBatch= :idBatch")
	Batch findByUserIdcheckLock(String paymentStatus, Long userSurId, Long idBatch);

	List<Batch> findByIdBatchGroupAndActiveFlag(Long idBatchGroup, boolean activeFlag);
	
	List<Batch> findByIdBatchGroup(Long idBatchGroup);

}
