/**
 * 
 */
package co.vistafoundation.vlearning.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;

/**
 * @author vk
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRoleName(RoleName roleName);

	Role findByIdRole(Long id);

}
