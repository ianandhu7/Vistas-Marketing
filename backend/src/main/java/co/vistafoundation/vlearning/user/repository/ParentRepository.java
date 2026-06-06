package co.vistafoundation.vlearning.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.user.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long>{
	
	public Parent findByIdParent(Long idParent);
	
	public Parent findByUser(User user);
	
	public Parent getParentByUser_UserSurId(Long userSurId);
	
	
}
