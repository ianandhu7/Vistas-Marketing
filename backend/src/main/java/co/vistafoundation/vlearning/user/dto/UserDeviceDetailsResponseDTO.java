package co.vistafoundation.vlearning.user.dto;
public class UserDeviceDetailsResponseDTO {
    private String deviceLocation;
    private String deviceType;
    private String userType;
    private Long userSurId;

    // Default constructor
    public UserDeviceDetailsResponseDTO() {}

    // Parameterized constructor
    public UserDeviceDetailsResponseDTO(String deviceLocation, String deviceType, String userType, Long userSurId) {
        this.deviceLocation = deviceLocation;
        this.deviceType = deviceType;
        this.userType = userType;
        this.userSurId = userSurId;
    }

    // Getters and Setters
    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserSurId() {
        return userSurId;
    }

    public void setUserSurId(Long userSurId) {
        this.userSurId = userSurId;
    }

	@Override
	public String toString() {
		return "UserDeviceDetailsResponseDTO [deviceLocation=" + deviceLocation + ", deviceType=" + deviceType
				+ ", userType=" + userType + ", userSurId=" + userSurId + "]";
	}
    
}
