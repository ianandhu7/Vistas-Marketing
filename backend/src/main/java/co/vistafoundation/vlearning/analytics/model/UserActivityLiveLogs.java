package co.vistafoundation.vlearning.analytics.model;

import javax.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user_activity_live_logs")
public class UserActivityLiveLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserActivityLiveLogs;

    private String username;
    private Long userSurId;
    private Instant timestamp;
    private String firstName;
    private String device;

    public UserActivityLiveLogs() {
//        this.timestamp = GenerateTime.truncateToHour(Instant.now());
    }

    public UserActivityLiveLogs(String username, Long userSurId, String firstName, String device) {
        this.username = username;
        this.userSurId = userSurId;
        this.timestamp = Instant.now();
        this.firstName = firstName;
        this.device = device;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return idUserActivityLiveLogs;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.idUserActivityLiveLogs = id;
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
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the timestamp
	 */
	public Instant getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
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
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "UserActivityLiveLogs [id=" + idUserActivityLiveLogs + ", username=" + username + ", userSurId=" + userSurId + ", timestamp="
				+ timestamp + ", firstName=" + firstName + ", device=" + device + "]";
	}
    
}
