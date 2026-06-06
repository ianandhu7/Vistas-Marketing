package co.vistafoundation.vlearning.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.SignupPlatform;

public interface SignupPlatformRepository extends JpaRepository<SignupPlatform, Long> {
}