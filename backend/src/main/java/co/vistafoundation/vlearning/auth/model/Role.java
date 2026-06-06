/**
 * 
 */
package co.vistafoundation.vlearning.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "VL_ROLE")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idROLE", nullable = false)
	private Long idRole;

	@Enumerated(EnumType.STRING)
	@NaturalId
	@Size(max = 45)
	@Column(name = "ROLE_NAME", nullable = false)
	private RoleName roleName;

	/**
	 * @param idRole
	 * @param roleName
	 */
	public Role(@Size(max = 60) RoleName roleName) {
		super();
		this.roleName = roleName;
	}

	/**
	 * 
	 */
	public Role() {
		super();
	}

	/**
	 * @return the idRole
	 */
	public Long getIdRole() {
		return idRole;
	}

	/**
	 * @return the roleName
	 */
	public RoleName getRoleName() {
		return roleName;
	}

	/**
	 * @param idRole the idRole to set
	 */
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
	
}
