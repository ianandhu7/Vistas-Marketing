/**
 * 
 */
package co.vistafoundation.vlearning.auth.config;

import java.util.List;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author vk
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final long MAX_AGE_SECS = 3600;

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE").maxAge(MAX_AGE_SECS);
//	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
	    return jacksonObjectMapperBuilder -> 
	        jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("IST"));
	}
	
//	@Bean
//	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
//		return new DeviceResolverHandlerInterceptor();
//	}
//
//	@Bean
//	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
//		return new DeviceHandlerMethodArgumentResolver();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(deviceResolverHandlerInterceptor());
//	}
//
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		argumentResolvers.add(deviceHandlerMethodArgumentResolver());
//	}
	
	 @Bean
	    public CustomDeviceResolver customDeviceResolver() {
	        return new CustomDeviceResolver();
	    }

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new DeviceResolverHandlerInterceptor(customDeviceResolver()));
	    }

	    @Override
	    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	    	argumentResolvers.add(new CustomDeviceHandlerMethodArgumentResolver(customDeviceResolver()));
	    }

}

