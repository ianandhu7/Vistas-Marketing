package co.vistafoundation.vlearning.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

	ForgotPassword findByUserSurId(Long userSurId);

}
