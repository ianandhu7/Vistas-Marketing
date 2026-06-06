package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.marketer.dto.VendorStudentResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserReportDTO;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {


	public Student getStudentByUser_UserSurId(Long userSurId);

	public Student getStudentByUser_UserSurIdAndIsProfileEdited(Long userSurId, Boolean isProfileEdited);

	public List<Student> findByParent(Parent parent);

	public List<Student> findByParent_IdParent(Long idParent);

	public Student findByIdStudent(Long idStudent);
    
	public Student findByUser(User user);

	public Long countByIdReferralCode(Long idReferralCode);
	

	@Query(value = "select new co.vistafoundation.vlearning.marketer.dto.VendorStudentResponseDTO(s.idStudent, s.idClassStandard,"
			+ "    s.idSyllabus, s.idStudentMedium, s.idState, u.firstName,"
			+ "	 u.email,u.mobileNumber, vsp.onBoardedDate, vsp.vendorPaymentStatus, vsp.paymentDate,vsp.paymentAmount,"
			+ "	 cs.classStandadName,sy.syllabusName,st.state,m.medium) " + "    from Student s "
			+ " inner join VendorStudentPayment  vsp on s.idStudent=vsp.idStudent"
			+ "    inner join ClassStandard cs on s.idClassStandard=cs.idClassStandard"
			+ "    inner join User u on s.user.userSurId=u.userSurId"
			+ "    inner join Syllabus sy on s.idSyllabus=sy.idSyllabus inner join State st on s.idState = st.idState "
			+ "    inner join StudentMedium m on s.idStudentMedium = m.idStudentMedium "
			+ "    where s.idReferralCode=:idReferralCode")
	public List<VendorStudentResponseDTO> getAllStudentListByReferralCode(Long idReferralCode);
	
	@Query(value ="select new co.vistafoundation.vlearning.user.dto.UserReportDTO( s1.syllabusName, s2.state, s3.medium, count(idStudent)) from Student s "
			+ "inner join Syllabus as s1 on s.idSyllabus = s1.idSyllabus "
			+ "inner join State as s2 on s.idState=s2.idState "
			+ "inner join StudentMedium as s3 on s.idStudentMedium=s3.idStudentMedium group by s.idSyllabus,s.idState,s.idStudentMedium order by count(idStudent) desc")
	public List<UserReportDTO> getUsersReport();
	
	public Student findByIdStudentAndUser(Long idStudent,User user);

	public Student findByUser_userSurId(Long userSurId);

}
