/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.Report;

/**
 * @author Naveen Kumar
 *
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

}
