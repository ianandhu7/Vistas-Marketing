package co.vistafoundation.vlearning.user.dto;

import java.time.Instant;

public class DeviceHistoryDTO {

   
    private Long idVLUser; 

    private String deviceType;

    private String deviceLocation;

    private String userAgent;
    
    private Instant lastLoginTime;
    
    
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

	/**
	 * @return the lastLoginTime
	 */
	public Instant getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Instant lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}   

}
