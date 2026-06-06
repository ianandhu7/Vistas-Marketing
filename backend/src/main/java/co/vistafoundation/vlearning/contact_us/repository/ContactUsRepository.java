/**
 * 
 */
package co.vistafoundation.vlearning.contact_us.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.contact_us.modal.ContactUs;

/**
 * @author NAVEEN
 *
 */
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

	@Query("select cu from ContactUs cu where (:name is null or cu.name like %:name%) and "
			+ "(:email is null or cu.email like %:email%) and (:mobileNumber is null or cu.phoneNumber like %:mobileNumber%)"
			+ "and (DATE(cu.requestedDate)>=:from and DATE(cu.requestedDate)<=:to) ORDER BY cu.requestedDate DESC")
	Page<ContactUs> findAllByNameOrEmailOrMobileWithGivenDates(String name, String email, String mobileNumber,
			Date from, Date to, Pageable page);

	@Query("select cu from ContactUs cu where (:name is null or cu.name like %:name%) and "
			+ "(:email is null or cu.email like %:email%) and (:mobileNumber is null or cu.phoneNumber like %:mobileNumber%)"
			+ "and (DATE(cu.requestedDate)>=:from ) ORDER BY cu.requestedDate DESC")
	Page<ContactUs> findAllByNameOrEmailOrMobileWithFromDate(String name, String email, String mobileNumber, Date from,
			Pageable page);

	@Query("select cu from ContactUs cu where (:name is null or cu.name like %:name%) and "
			+ "(:email is null or cu.email like %:email%) and (:mobileNumber is null or cu.phoneNumber like %:mobileNumber%)"
			+ "and (DATE(cu.requestedDate)<=:to) ORDER BY cu.requestedDate DESC")
	Page<ContactUs> findAllByNameOrEmailOrMobileToDate(String name, String email, String mobileNumber, Date to,
			Pageable page);

	@Query("select cu from ContactUs cu where (:name is null or cu.name like %:name%) and "
			+ "(:email is null or cu.email like %:email%) and (:mobileNumber is null or cu.phoneNumber like %:mobileNumber%) ORDER BY cu.requestedDate DESC")
	Page<ContactUs> findAllByNameOrEmailOrMobile(String name, String email, String mobileNumber, Pageable page);

	@Query("select cu from ContactUs cu ORDER BY cu.requestedDate DESC")
	Page<ContactUs> findAllOrderByRequestedDateDesc(Pageable paging);

}
