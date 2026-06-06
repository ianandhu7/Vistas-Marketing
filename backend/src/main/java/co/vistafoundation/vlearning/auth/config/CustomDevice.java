package co.vistafoundation.vlearning.auth.config;


import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;

/**
 * @author Mohan Kumar K M
 *
 */
public class CustomDevice implements Device {
    private final DevicePlatform devicePlatform;
    private final CustomDeviceType deviceType;
    private final String userAgent;

    public CustomDevice(DevicePlatform devicePlatform, CustomDeviceType deviceType, String userAgent) {
        this.devicePlatform = devicePlatform;
        this.deviceType = deviceType;
        this.userAgent = userAgent;
    }

    @Override
    public boolean isNormal() {
        return this.deviceType == CustomDeviceType.NORMAL;
    }

    @Override
    public boolean isMobile() {
        return this.deviceType == CustomDeviceType.MOBILE;
    }

    @Override
    public boolean isTablet() {
        return this.deviceType == CustomDeviceType.TABLET;
    }

    public boolean isTv() {
        return this.deviceType == CustomDeviceType.TV;
    }

    @Override
    public DevicePlatform getDevicePlatform() {
        return this.devicePlatform;
    }

    public CustomDeviceType getDeviceType() {
        return this.deviceType;
    }

    public String getUserAgent() {
        return this.userAgent;
    }
}