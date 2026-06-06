package co.vistafoundation.vlearning.auth.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "signup_platform")
public class SignupPlatform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_signup_platform")
	private Long id;

	
	@Column(name = "device_platform")
	private String devicePlatform;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "roles", "registeredAs",
			"classStandard", "password" })
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId")
	private User user;

	// Constructors, getters, and setters
	// Constructor(s)

	public SignupPlatform() {
	}

	public SignupPlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}
	

	/**
	 * @param devicePlatform
	 * @param user
	 */
	public SignupPlatform(String devicePlatform, User user) {
		super();
		this.devicePlatform = devicePlatform;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
