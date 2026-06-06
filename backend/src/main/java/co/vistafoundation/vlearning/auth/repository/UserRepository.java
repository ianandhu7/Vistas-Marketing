/**
 * 
 */
package co.vistafoundation.vlearning.auth.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.user.dto.UserProfileDTO;

/**
 * @author vk
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);
	
	@Query("select u from User u where u.username = :emailOrPhone OR u.mobileNumber = :emailOrPhone OR u.email = :emailOrPhone ")
	User findByEmailOrMobileNumber(@Param("emailOrPhone") String emailOrPhone);

	List<User> findByUserSurIdIn(List<Long> userIds);

	User findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	User  findByUserSurId(Long userSurId);

	Boolean existsByMobileNumber(String mobile);
	
	List<User> findByClassStandard(Long idClassStandard);
	
	List<User> findByRegisteredAsIn(List<String> role);
	
	User findByMobileNumber(String mobileNumber);
	
	User findByEmailAndMobileNumber(String email,String mobile);
	
	User getByEmail(String email);

	@Query("select new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,u.secondaryLanguage,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END,s.isProfileEdited) "
			+ "from User u "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 and ss.activeFlag= TRUE "
			+ "where u.registeredAs=:registeredAs and (:firstName is null or u.firstName = :firstName) and (:email is null or u.email = :email) and (:mobileNumber is null or u.mobileNumber = :mobileNumber) "
			+ "	ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllSpecificRole(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName,
			@Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber,
			Pageable page);
	
	
	@Query("select new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,u.secondaryLanguage,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END) "
			+ "from User u "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "where u.registeredAs=:registeredAs and (:firstName is null or u.firstName = :firstName) and (:email is null or u.email = :email) and (:mobileNumber is null or u.mobileNumber = :mobileNumber) ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRoleOther(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName,
			@Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber,
			Pageable page);
	
	@Query("select new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,u.secondaryLanguage,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END) "
			+ "from User u "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "where u.registeredAs=:registeredAs and (DATE(u.createdAt)>=:from and DATE(u.createdAt)<=:to) ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRoleOtherWithDate(@Param(value = "registeredAs") String registeredAs, @Param(value="from") Date from, @Param(value="to") Date to, Pageable page);
	
	@Query("select new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,u.secondaryLanguage,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END,s.isProfileEdited) "
			+ "from User u "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "where u.registeredAs=:registeredAs and (DATE(u.createdAt)>=:from and DATE(u.createdAt)<=:to) ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllSpecificRoleWithDate(@Param(value = "registeredAs") String registeredAs, @Param(value="from") Date from, @Param(value="to") Date to, Pageable page);
	
	@Query("select new co.vistafoundation.vlearning.user.dto.UserProfileDTO(u.firstName,u.email,s.idClassStandard,s.idSyllabus,s.idState,s.idStudentMedium,s.idLangauage) "
			+ "from User u "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "where s.user.userSurId=:userSurId")
	public UserProfileDTO findCustomStudentProfile(@Param(value = "userSurId") Long userSurId);
	
	public Long countByRegisteredAs(String registeredAsage);
	
	public Boolean existsByUsernameOrEmail(String username, String email);
	
	@Query("SELECT count(*) FROM User where registeredAs='Student' and active_flag=TRUE")
	public Long getActiveStudentCount();
	
	
	@Query(value="SELECT distinct u.user_sur_id,u.email,ud.device_id,u.first_name FROM  vl_user u "
			+ "left join user_device ud on  ud.idvluser = u.user_sur_id and ud.device_type=\"MOBILE\" \n"
			+ "where "+" u.user_sur_id in (:userIds);",nativeQuery=true)
	public List<Object[]> getUserNotificationData(List<Long> userIds);
	
	
	@Query(value="SELECT  distinct u.user_sur_id,u.email,ud.device_id,u.first_name FROM  vl_user u "
			+ "left  join user_device ud on u.user_sur_id = ud.idvluser and ud.device_type=\"MOBILE\" "
			+ "where u.registered_as= :registeredAs ",nativeQuery=true)
	public List<Object[]> findAllUserByResgisteredAs(String registeredAs);
	
	
	public User findByUsernameOrEmailOrMobileNumber(String username, String email,String mobileNumber);
	
	/**
	 * Created new methods for enhancement of the filters for user list
	 * this methods returns the users list having roles other than student based on filters
	 * 
	 * @author Abdul Elahi.
	 */
	@Query("select distinct new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,l.language,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END, s.isProfileEdited,s.idSyllabus) "
			+ "from User u "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "left join Language l on l.idLanguage = s.idLangauage "
			+ "where u.registeredAs=:registeredAs "
			+ "and (:firstName is null or u.firstName LIKE %:firstName%) "
			+ "and (:email is null or u.email LIKE %:email%) "
			+ "and (:mobileNumber is null or u.mobileNumber LIKE %:mobileNumber%) "
			+ "ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRolesExceptStudent(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName,
			@Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber,
			Pageable page);
	
	/**
	 * Created new methods for enhancement of the filters for user list
	 * this methods returns the users list having roles other than student based on the Date Range and Filters
	 * 
	 * @author Abdul Elahi.
	 */
	@Query("select distinct new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId,u.firstName,u.lastName,u.username,u.email,u.registeredAs,u.mobileNumber,l.language,u.userProfilePic,cs.idClassStandard,cs.classStandadName,u.activeFlag,u.createdAt,CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END, s.isProfileEdited,s.idSyllabus) "
			+ "from User u "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left  join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "left join Language l on l.idLanguage = s.idLangauage "
			+ "where u.registeredAs=:registeredAs "
			+ "and (:firstName is null or u.firstName LIKE %:firstName%) "
			+ "and (:email is null or u.email LIKE %:email%) "
			+ "and (:mobileNumber is null or u.mobileNumber LIKE %:mobileNumber%) "
			+ "and (DATE(u.createdAt)>=:from and DATE(u.createdAt)<=:to)"
			+ "ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRolesExceptStudentWithDates(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName,
			@Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber,
			@Param(value="from") Date from, @Param(value="to") Date to,
			Pageable page);
	
	/**
	 * Created new methods for enhancement of the filters for user list
	 * this methods returns the users list having role "Student" based on filters
	 * 
	 * @author Abdul Elahi.
	 */
	@Query("select distinct new co.vistafoundation.vlearning.auth.dto.UserListDTO(u.userSurId, u.firstName, u.lastName, u.username, u.email, u.registeredAs, u.mobileNumber, l.language, st.state, u.userProfilePic, cs.idClassStandard, cs.classStandadName, u.activeFlag, u.createdAt, CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END, s.isProfileEdited,s.idSyllabus) "
			+ "from User u "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left join Language l on l.idLanguage = s.idLangauage "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "left join State st on s.idState = st.idState "
			+ "where u.registeredAs = :registeredAs "
			+ "and (:firstName is null or u.firstName LIKE :firstName%) "
			+ "and (:email is null or u.email LIKE :email%) "
			+ "and (:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) "
			+ "and (:idClassStandard is null or s.idClassStandard = :idClassStandard) "
			+ "and (:idSyllabus is null or s.idSyllabus = :idSyllabus) "
			+ "and (:idMedium is null or s.idStudentMedium = :idMedium) "
			+ "and (:idState is null or s.idState = :idState) "
			+ "ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRoleStudent(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName,
			@Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber,
			@Param(value = "idClassStandard") Long idClassStandard,
			@Param(value = "idSyllabus") Long idSyllabus,
			@Param(value = "idMedium") Long idMedium,
			@Param(value = "idState") Long idState,
			Pageable page);
	
	/**
	 * Created new methods for enhancement of the filters for user list
	 * this methods returns the users list having role "Student" based on the Date Range and Filters
	 * 
	 * @author Abdul Elahi.
	 */
	@Query("select distinct new co.vistafoundation.vlearning.auth.dto.UserListDTO("
			+ "u.userSurId, u.firstName, u.lastName, u.username, u.email, u.registeredAs, u.mobileNumber, "
			+ "l.language, u.userProfilePic, cs.idClassStandard, cs.classStandadName, u.activeFlag, "
			+ "u.createdAt, CASE WHEN ss.idStudentSubscription is null THEN FALSE ELSE TRUE END, s.isProfileEdited, s.idSyllabus) "
			+ "from User u "
			+ "left join Student s on u.userSurId = s.user.userSurId "
			+ "left join Language l on l.idLanguage = s.idLangauage "
			+ "left join ClassStandard cs on u.classStandard = cs.idClassStandard "
			+ "left join StudentSubscription ss on u.userSurId = ss.userSurId and ss.idproductLine=11 "
			+ "left join State st on s.idState = st.idState "
			+ "where u.registeredAs = :registeredAs "
			+ "and (:firstName is null or u.firstName LIKE %:firstName%) "
			+ "and (:email is null or u.email LIKE %:email%) "
			+ "and (:mobileNumber is null or u.mobileNumber LIKE %:mobileNumber%) "
			+ "and (:idClassStandard is null or s.idClassStandard = :idClassStandard) "
			+ "and (:idSyllabus is null or s.idSyllabus = :idSyllabus) "
			+ "and (:idMedium is null or s.idStudentMedium = :idMedium) "
			+ "and (:idState is null or s.idState = :idState) "
			+ "and (DATE(u.createdAt)>=:from and DATE(u.createdAt)<=:to) "
			+ "ORDER BY u.createdAt DESC")
	Page<UserListDTO> findAllRoleStudentWithDate(@Param(value = "registeredAs") String registeredAs, 
			@Param(value = "firstName") String firstName, @Param(value = "email") String email,
			@Param(value = "mobileNumber") String mobileNumber, @Param(value = "idClassStandard") Long idClassStandard,
			@Param(value = "idSyllabus") Long idSyllabus, @Param(value = "idMedium") Long idMedium, @Param(value = "idState") Long idState,
			@Param(value="from") Date from, @Param(value="to") Date to,
			Pageable page);
	
	
	
	@Query(value = "SELECT \n" + "            COUNT(*) AS login_count\n" + "        FROM (\n" + "            SELECT \n"
			+ "                uat.userId,\n" + "                uat.activity_time,\n"
			+ "                LAG(uat.activity_time) OVER (PARTITION BY uat.userId ORDER BY uat.activity_time) AS prev_activity_time\n"
			+ "            FROM \n" + "                user_activity_timeseries uat\n" + "            WHERE \n"
			+ "                uat.userId = :userId\n" + "        ) subquery\n" + "        WHERE \n"
			+ "            TIMESTAMPDIFF(HOUR, prev_activity_time, activity_time) > 24", nativeQuery = true)
	Long totalLoginCount(@Param("userId") Long userId);
	

	@Query("select u.userSurId from User u   join Student s on u.userSurId = s.user.userSurId "
			+ "where  s.idClassStandard=:idClassStandard and s.idSyllabus=:idSyllabus and s.idState =:idState")
	List<Long> findUserIds(
			@Param(value = "idClassStandard") Long idClassStandard,
			@Param(value = "idSyllabus") Long idSyllabus,
			@Param(value = "idState") Long idState);
	
}
