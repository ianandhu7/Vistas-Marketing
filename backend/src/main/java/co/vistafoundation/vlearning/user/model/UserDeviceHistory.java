package co.vistafoundation.vlearning.user.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.DateAudit;

@Entity
@Table(name = "user_device_history")
public class UserDeviceHistory extends DateAudit {

	private static final long serialVersionUID = 2931880984409631386L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idUSER_DEVICE_HISTORY", nullable = false)
	private Long idUserDeviceHistory;

    @Column(name = "DEVICE_ID", length = 255) 
    private String deviceId;

    @Column(name = "idVLUser") 
    private Long idVLUser; 

    @Column(name = "DEVICE_TYPE", length = 15, nullable = false) 
    private String deviceType;

    @Column(name = "DEVICE_LOCATION", length = 800)
    private String deviceLocation;

    @Column(name = "USER_AGENT", length = 500) 
    private String userAgent;




	/**
	 * @return the idUserDeviceHistory
	 */
	public Long getIdUserDeviceHistory() {
		return idUserDeviceHistory;
	}

	/**
	 * @param idUserDeviceHistory the idUserDeviceHistory to set
	 */
	public void setIdUserDeviceHistory(Long idUserDeviceHistory) {
		this.idUserDeviceHistory = idUserDeviceHistory;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the idVLUser
	 */
	public Long getIdVLUser() {
		return idVLUser;
	}

	/**
	 * @param idVLUser the idVLUser to set
	 */
	public void setIdVLUser(Long idVLUser) {
		this.idVLUser = idVLUser;
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

	@Override
	public String toString() {
		return "UserDeviceHistory [idUserDeviceHistory=" + idUserDeviceHistory + ", deviceId=" + deviceId
				+ ", idVLUser=" + idVLUser + ", deviceType=" + deviceType + ", deviceLocation=" + deviceLocation
				+ ", userAgent=" + userAgent + "]";
	}

	


}
