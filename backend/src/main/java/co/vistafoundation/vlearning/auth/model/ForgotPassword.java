package co.vistafoundation.vlearning.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/*
 * @author Shaikh Ahmed Reza
 */

@Entity
@Table(name = "forgot_password")
public class ForgotPassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "random_string")
	private String randomString;

	@NotNull
	@Column(name = "ForgotPasswordUsername")
	private String forgotPasswordUsername;

	@Column(name = "IdVLUser")
	private Long userSurId;

	public ForgotPassword() {

	}

	public ForgotPassword(Long id, @NotNull String randomString, @NotNull String forgotPasswordUsername,
			Long userSurId) {
		this.id = id;
		this.randomString = randomString;
		this.forgotPasswordUsername = forgotPasswordUsername;
		this.userSurId = userSurId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRandomString() {
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	public String getForgotPasswordUsername() {
		return forgotPasswordUsername;
	}

	public void setForgotPasswordUsername(String forgotPasswordUsername) {
		this.forgotPasswordUsername = forgotPasswordUsername;
	}

}
