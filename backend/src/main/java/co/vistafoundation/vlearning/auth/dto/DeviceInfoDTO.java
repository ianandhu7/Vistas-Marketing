package co.vistafoundation.vlearning.auth.dto;
import java.time.Instant;

public class DeviceInfoDTO {
	
	private Long idUserActivity;
    private Instant lastLogin;
    private String deviceType;
    private String deviceName;
    private String location;

	public Long getIdUserActivity() {
		return idUserActivity;
	}
	
	public void setIdUserActivity(Long idUserActivity) {
		this.idUserActivity = idUserActivity;
	}

	public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DeviceInfoDTO{" +
                "lastLogin=" + lastLogin +
                ", deviceType='" + deviceType + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
