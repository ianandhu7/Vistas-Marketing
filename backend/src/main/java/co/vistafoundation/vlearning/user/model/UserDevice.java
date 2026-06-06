/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import co.vistafoundation.vlearning.audit.model.DateAudit;

/**
 * @author Sarfaraz
 * 
 * modified by @author Abdul Elahi added device Location Device Name And date audit
 *
 */

@Entity
@Table(name = "USER_DEVICE",uniqueConstraints= @UniqueConstraint(columnNames={"idVLUser", "DEVICE_TYPE"}))
public class UserDevice extends DateAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idUSER_DEVICE", nullable = false)
	private Long idUserDevice;
	
	@Column(name = "DEVICE_ID")
	private String deviceId;
	
	@Column(name = "idVLUser")
	private Long userSurId;
	
	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;
	
	
	@Column(name = "DEVICE_TYPE")
	private String deviceType;
	
	@Column(name = "JWT_TOKEN", length = 4000)
	private String jwtToken;
	
	@Column(name = "IS_DEVICE_ACTIVE")
	private boolean isDeviceActive;
	
	@Column(name = "IS_NOTIFICATION_SENT")
	private boolean isNotificationSent ;
	
	@Column(name = "device_location")
	private String deviceLocation;
	
	@Column(name = "user_agent")
	private String userAgent;
	
	
	public UserDevice() {
		//super();
	}

	/**
	 * @param deviceId
	 * @param userSurId
	 * @param idClassStandard
	 */
	
	
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param idUserDevice
	 * @param deviceId
	 * @param userSurId
	 * @param idClassStandard
	 * @param deviceType
	 * @param jwtToken
	 */
	public UserDevice(Long idUserDevice, String deviceId, Long userSurId, Long idClassStandard, String deviceType,
			String jwtToken) {
		super();
		this.idUserDevice = idUserDevice;
		this.deviceId = deviceId;
		this.userSurId = userSurId;
		this.idClassStandard = idClassStandard;
		this.deviceType = deviceType;
		this.jwtToken = jwtToken;
	}

	
	
	/**
	 * @param idUserDevice
	 * @param deviceId
	 * @param userSurId
	 * @param idClassStandard
	 * @param deviceType
	 * @param jwtToken
	 * @param isDeviceActive
	 * @param isNotificationSent
	 */
	public UserDevice(Long idUserDevice, String deviceId, Long userSurId, Long idClassStandard, String deviceType,
			String jwtToken, boolean isDeviceActive, boolean isNotificationSent) {
		super();
		this.idUserDevice = idUserDevice;
		this.deviceId = deviceId;
		this.userSurId = userSurId;
		this.idClassStandard = idClassStandard;
		this.deviceType = deviceType;
		this.jwtToken = jwtToken;
		this.isDeviceActive = isDeviceActive;
		this.isNotificationSent = isNotificationSent;
	}

	
	
	/**
	 * @param idUserDevice
	 * @param deviceId
	 * @param userSurId
	 * @param idClassStandard
	 * @param deviceType
	 * @param jwtToken
	 * @param isDeviceActive
	 * @param isNotificationSent
	 * @param deviceLocation
	 */
	public UserDevice(Long idUserDevice, String deviceId, Long userSurId, Long idClassStandard, String deviceType,
			String jwtToken, boolean isDeviceActive, boolean isNotificationSent, String deviceLocation) {
		super();
		this.idUserDevice = idUserDevice;
		this.deviceId = deviceId;
		this.userSurId = userSurId;
		this.idClassStandard = idClassStandard;
		this.deviceType = deviceType;
		this.jwtToken = jwtToken;
		this.isDeviceActive = isDeviceActive;
		this.isNotificationSent = isNotificationSent;
		this.deviceLocation = deviceLocation;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the jwtToken
	 */
	public String getJwtToken() {
		return jwtToken;
	}

	/**
	 * @param jwtToken the jwtToken to set
	 */
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	/**
	 * @return the isDeviceActive
	 */
	public boolean isDeviceActive() {
		return isDeviceActive;
	}

	/**
	 * @param isDeviceActive the isDeviceActive to set
	 */
	public void setDeviceActive(boolean isDeviceActive) {
		this.isDeviceActive = isDeviceActive;
	}

	/**
	 * @return the isNotificationSent
	 */
	public boolean isNotificationSent() {
		return isNotificationSent;
	}

	/**
	 * @param isNotificationSent the isNotificationSent to set
	 */
	public void setNotificationSent(boolean isNotificationSent) {
		this.isNotificationSent = isNotificationSent;
	}

	/**
	 * @return the idUserDevice
	 */
	public Long getIdUserDevice() {
		return idUserDevice;
	}

	/**
	 * @param idUserDevice the idUserDevice to set
	 */
	public void setIdUserDevice(Long idUserDevice) {
		this.idUserDevice = idUserDevice;
	}

	/**
	 * @return the deviceLocation
	 */
	public String getDeviceLocation() {
		return deviceLocation;
	}

	/**
	 * @param deviceLocation the deviceLocation to set
	 */
	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param idUserDevice
	 * @param deviceId
	 * @param userSurId
	 * @param idClassStandard
	 * @param deviceType
	 * @param jwtToken
	 * @param isDeviceActive
	 * @param isNotificationSent
	 * @param deviceLocation
	 * @param userAgent
	 */
	public UserDevice(Long idUserDevice, String deviceId, Long userSurId, Long idClassStandard, String deviceType,
			String jwtToken, boolean isDeviceActive, boolean isNotificationSent, String deviceLocation,
			String userAgent) {
		super();
		this.idUserDevice = idUserDevice;
		this.deviceId = deviceId;
		this.userSurId = userSurId;
		this.idClassStandard = idClassStandard;
		this.deviceType = deviceType;
		this.jwtToken = jwtToken;
		this.isDeviceActive = isDeviceActive;
		this.isNotificationSent = isNotificationSent;
		this.deviceLocation = deviceLocation;
		this.userAgent = userAgent;
	}	
	
}