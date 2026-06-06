package co.vistafoundation.vlearning.auth.config;


import org.springframework.core.MethodParameter;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mohan Kumar K M
 *
 */
public class CustomDeviceHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final DeviceResolver deviceResolver;

    public CustomDeviceHandlerMethodArgumentResolver(DeviceResolver deviceResolver) {
        this.deviceResolver = deviceResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Device.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return deviceResolver.resolveDevice(request);
    }
}