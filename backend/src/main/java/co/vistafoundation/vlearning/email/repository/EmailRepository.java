package co.vistafoundation.vlearning.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.email.model.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
