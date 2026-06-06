package co.vistafoundation.vlearning.auth.config;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.DevicePlatform;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mohan Kumar K M
 *
 */
public class CustomDeviceResolver implements DeviceResolver {

    @Override
    public Device resolveDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        CustomDeviceType deviceType = detectDeviceType(userAgent);
        DevicePlatform platform = detectDevicePlatform(userAgent);

        return new CustomDevice(platform, deviceType, userAgent);
    }

    private CustomDeviceType detectDeviceType(String userAgent) {
        if (userAgent == null) {
            return CustomDeviceType.NORMAL;
        }
        if (userAgent.toUpperCase().contains("SMART-TV") || userAgent.toUpperCase().contains("APPLE-TV") ||
            userAgent.toUpperCase().contains("GOOGLE-TV") || userAgent.toUpperCase().contains("HBB-TV") ||
            userAgent.toUpperCase().contains("WEB-TV")||userAgent.toUpperCase().contains("TV")) {
            return CustomDeviceType.TV;
        }
        if (userAgent.toUpperCase().contains("MOBILE") || userAgent.toUpperCase().contains("ANDROID") ||
            userAgent.toUpperCase().contains("IPHONE") || userAgent.toUpperCase().contains("IPAD")) {
            return CustomDeviceType.MOBILE;
        }
        if (userAgent.toUpperCase().contains("TABLET") || userAgent.toUpperCase().contains("IPAD")) {
            return CustomDeviceType.TABLET;
        }
        return CustomDeviceType.NORMAL;
    }

    private DevicePlatform detectDevicePlatform(String userAgent) {
        if (userAgent == null) {
            return DevicePlatform.UNKNOWN;
        }
        if (userAgent.contains("Android")) {
            return DevicePlatform.ANDROID;
        }
        if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return DevicePlatform.IOS;
        }
        return DevicePlatform.UNKNOWN;
    }
}