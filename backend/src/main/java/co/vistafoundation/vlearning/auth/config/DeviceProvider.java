/**
 * 
 */
package co.vistafoundation.vlearning.auth.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

/**
 * @author vk
 *
 */
@Component
public class DeviceProvider {

	public Device getCurrentDevice(HttpServletRequest request) {
        return DeviceUtils.getCurrentDevice(request);
    }
	
}
