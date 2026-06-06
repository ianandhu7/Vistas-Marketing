package co.vistafoundation.vlearning.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.User;

public interface VlUserRepository extends JpaRepository<User, Long> {

	public User findByUserSurId(Long userSurId);

}
