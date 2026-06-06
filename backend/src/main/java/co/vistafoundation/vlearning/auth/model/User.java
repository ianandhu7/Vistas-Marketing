/**
 * 
 */
package co.vistafoundation.vlearning.auth.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import co.vistafoundation.vlearning.audit.model.DateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "VL_USER", uniqueConstraints = { @UniqueConstraint(columnNames = {"username"}),@UniqueConstraint(columnNames={"mobileNumber"}),@UniqueConstraint(columnNames = {"email"})})
public class User extends DateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSurId;

	@NotBlank
	@Column(name = "FIRST_NAME")
	private String firstName;

	@NotBlank
	@Column(name = "LAST_NAME")
	private String lastName;

	@NotBlank
	@Size(max = 100)
	@Column(unique = true)
	private String username;

	private String email;

	@NotBlank
	@Size(max = 100)
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "REGISTERED_AS")
	private String registeredAs;

	private String mobileNumber;

	@Column(name = "SECONDARY_LANGUAGE")
	private String secondaryLanguage;

	@Column(name = "CLASS_STANDARD")
	private Long classStandard;

	@Column(name = "USER_PROFILE_PIC")
	private String userProfilePic;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE_MAP", joinColumns = @JoinColumn(name = "VL_USER_SUR_ID"), inverseJoinColumns = @JoinColumn(name = "VL_ROLE_SUR_ID"))
	private Set<Role> roles = new HashSet<>();

	
	@Column(name = "ACTIVE_FLAG", columnDefinition="BOOLEAN DEFAULT TRUE" , nullable=false)
	private Boolean activeFlag = Boolean.TRUE;

	
	@Column(name = "MAX_ATTEMPTS" , columnDefinition="INT DEFAULT 0" , nullable=false)
	private Integer maxAttempts = 0;
	
	@Column(name = "REGISTERED_SOURCE")
	private String registeredSource;

	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	public User(@NotBlank @Size(max = 40) String firstName, @NotBlank @Size(max = 40) String lastName,
			@NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password, Long classStandard, String mobileNumber, String registeredAs,
			String secondaryLanguage) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.classStandard = classStandard;
	}
	
	public User(@NotBlank @Size(max = 40) String firstName, @NotBlank @Size(max = 40) String lastName,
			@NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password, Long classStandard, String mobileNumber, String registeredAs,
			String secondaryLanguage,String registeredSource) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.classStandard = classStandard;
		this.registeredSource = registeredSource;
	}

	public User(@NotBlank @Size(max = 40) String firstName, @NotBlank @Size(max = 40) String lastName,
			@NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password, String registeredAs, String mobileNumber,
			String secondaryLanguage, Long classStandard, Set<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.classStandard = classStandard;
		this.roles = roles;
	}

	public User(@NotBlank @Size(max = 40) String firstName, @NotBlank @Size(max = 40) String lastName,
			@NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password, String registeredAs, String mobileNumber,
			String secondaryLanguage, Long classStandard, Set<Role> roles,String userProfilePic) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.classStandard = classStandard;
		this.roles = roles;
		this.userProfilePic = userProfilePic;
	}
	
	/**
	 * @param firstName
	 * @param email
	 * @param mobileNumber
	 */
	public User(@NotBlank String firstName, String email, String mobileNumber) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the registeredAs
	 */
	public String getRegisteredAs() {
		return registeredAs;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @return the secondaryLanguage
	 */
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}

	/**
	 * @return the classStandard
	 */
	public Long getClassStandard() {
		return classStandard;
	}

	/**
	 * @return the userProfilePic
	 */
	public String getUserProfilePic() {
		return userProfilePic;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param registeredAs the registeredAs to set
	 */
	public void setRegisteredAs(String registeredAs) {
		this.registeredAs = registeredAs;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @param secondaryLanguage the secondaryLanguage to set
	 */
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(Long classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @param userProfilePic the userProfilePic to set
	 */
	public void setUserProfilePic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the maxAttempts
	 */
	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	/**
	 * @param maxAttempts the maxAttempts to set
	 */
	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	/**
	 * @return the registeredSource
	 */
	public String getRegisteredSource() {
		return registeredSource;
	}

	/**
	 * @param registeredSource the registeredSource to set
	 */
	public void setRegisteredSource(String registeredSource) {
		this.registeredSource = registeredSource;
	}
		
}
