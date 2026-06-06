package co.vistafoundation.vlearning.analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.auth.model.User;

public interface UserRoleCountRepo extends JpaRepository<User, Long> {

	@Query(value = "select  SUBSTRING(role.role_name,6,length(role.role_name)), \r\n"
			+ "sum(case when user.active_flag=true  then 1 else 0 end) as Active,\r\n"
			+ "sum(case when user.active_flag=false  then 1 else 0 end) as Inactive,\r\n"
			+ "count(role_map.vl_role_sur_id) as Total\r\n"
			+ "from vl_user as user\r\n"
			+ "join user_role_map as role_map on \r\n"
			+ "user.user_sur_id= role_map.vl_user_sur_id\r\n"
			+ "right  join vl_role  as role on \r\n"
			+ "role.idrole= role_map.vl_role_sur_id\r\n"
			+ "group by role.role_name;", nativeQuery = true)
	public List<Object[]> getUserRoleCount();
}
