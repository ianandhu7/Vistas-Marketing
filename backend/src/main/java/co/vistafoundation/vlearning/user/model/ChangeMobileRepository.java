package co.vistafoundation.vlearning.user.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeMobileRepository extends JpaRepository<ChangeMobileNumber, Long> {

	ChangeMobileNumber findByUserSurId(Long userSurId);

}
