/**
 * 
 */
package co.vistafoundation.vlearning.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.StudentMedium;

/**
 * @author vk
 *
 */
public interface StudentMediumRepository extends JpaRepository<StudentMedium, Long> {
	
	StudentMedium findByIdStudentMedium(Long idStudentMedium);

}
