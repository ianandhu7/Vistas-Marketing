/**
 * 
 */
package co.vistafoundation.vlearning.marketer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.marketer.model.VendorStudentPayment;

/**
 * @author NAVEEN
 *
 */
public interface VendorStudentPaymentRepository extends JpaRepository<VendorStudentPayment, Long> {

	VendorStudentPayment findByIdVendorAndIdStudent(Long idVendor, Long idStudent);

}
