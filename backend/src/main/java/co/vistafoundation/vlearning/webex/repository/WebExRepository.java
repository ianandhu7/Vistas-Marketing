package co.vistafoundation.vlearning.webex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.webex.dto.WebExMeetingDetails;

public interface WebExRepository extends JpaRepository<WebExMeetingDetails, Long> {

}
