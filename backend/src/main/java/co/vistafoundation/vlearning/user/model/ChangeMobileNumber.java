package co.vistafoundation.vlearning.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="change_mobile")
public class ChangeMobileNumber {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="email")
	private String email;
	@Column(name="verification_code")
	private String verificationCode;
	@Column(name="user_sur_id")
	private Long userSurId;
	
	public ChangeMobileNumber(Long id, String email, String verificationCode, Long userSurId) {
		this.id = id;
		this.email = email;
		this.verificationCode = verificationCode;
		this.userSurId = userSurId;
	}

	public ChangeMobileNumber() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}
	
	
}
