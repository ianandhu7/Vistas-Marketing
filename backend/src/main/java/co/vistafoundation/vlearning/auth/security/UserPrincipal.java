/**
 * 
 */
package co.vistafoundation.vlearning.auth.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author vk
 *
 */
public class UserPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userSurId;

    private String firstName;
    
    private String lastName;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    
    private String secondaryLanguage;
    
    private Long idClassStandard;
    
    private Long idSyllabus;
    
    private Long idState;
    
    private Boolean isSubscribed;



	/**
	 * @param userSurId
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param password
	 * @param authorities
	 * @param secondaryLanguage
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @param isSubscribed
	 */
	public UserPrincipal(Long userSurId, String firstName, String lastName, String username, String email,
			String password, Collection<? extends GrantedAuthority> authorities, String secondaryLanguage,
			Long idClassStandard, Long idSyllabus, Long idState, Boolean isSubscribed) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.secondaryLanguage = secondaryLanguage;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.isSubscribed = isSubscribed;
	}



	public static UserPrincipal create(User user,UserMetaClaim umc) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserSurId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,user.getSecondaryLanguage(),
                umc.getIdClassStandard(),
                umc.getIdSyllabus(),
                umc.getIdState(),
                umc.getIsSubscribed()
        );
    }

    public Long getUserSurId() {
        return userSurId;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(userSurId, that.userSurId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userSurId);
    }

	/**
	 * @return the secondaryLanguage
	 */
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}



	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}



	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}



	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}



	/**
	 * @return the isSubscribed
	 */
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}

	
    
}
