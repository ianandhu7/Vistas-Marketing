/**
 * 
 */
package co.vistafoundation.vlearning.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import co.vistafoundation.vlearning.auth.security.CustomUserDetailsService;
import co.vistafoundation.vlearning.auth.security.JwtAuthenticationEntryPoint;
import co.vistafoundation.vlearning.auth.security.JwtAuthenticationFilter;
import com.vistas.filter.RateLimitFilter;

/**
 * @author vk
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = {"com.vistas"})
@EnableJpaRepositories(basePackages = {"com.vistas.repository"})
@EntityScan(basePackages = {"co.vistafoundation.vlearning", "com.vistas.entity"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		/*
		 * This inMemoryAuthentication is available only during testing, Below
		 * credentials cannot be used for normal login.
		 */
		authenticationManagerBuilder.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
				.withUser("mock_user").credentialsExpired(false).disabled(false).accountExpired(false)
				.password(new BCryptPasswordEncoder().encode("secret@123")).roles("STUDENT");

		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
			"https://www.student.vistaslearning.com",
			"https://your-marketing-site.com",
			"http://localhost:5173",
			"http://localhost:5174"
		));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "ngrok-skip-browser-warning"));
		configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "ngrok-skip-browser-warning"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.headers()
					.contentSecurityPolicy(
						"default-src 'self'; " +
						"script-src 'self' 'unsafe-inline' 'unsafe-eval' https://checkout.razorpay.com https://cdn.razorpay.com https://www.google.com/ https://www.gstatic.com/ https://*.firebaseapp.com https://apis.google.com; " +
						"connect-src 'self' ws://localhost:* http://localhost:* https://api.razorpay.com https://lumberjack.razorpay.com https://www.google.com/ https://*.googleapis.com https://identitytoolkit.googleapis.com https://securetoken.googleapis.com https://www.googleapis.com https://*.firebase.com; " +
						"style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
						"font-src 'self' https://fonts.gstatic.com; " +
						"img-src 'self' data: https:; " +
						"frame-src 'self' https://checkout.razorpay.com https://api.razorpay.com https://www.google.com/ https://recaptcha.google.com https://*.firebaseapp.com https://*.firebase.com https://accounts.google.com; " +
						"worker-src 'self' blob: https://checkout.razorpay.com"
					)
				.and()
				.and().authorizeRequests()
				.antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/index.html", "/").permitAll()
				.antMatchers("/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers(
					"/api/v1/auth/send-otp",
					"/api/v1/auth/verify-otp",
					"/api/v1/auth/refresh-token",
					"/api/v1/auth/refresh",
					"/api/v1/payment/webhook",
					"/api/v1/payment/verify-status"
				).permitAll()
				.antMatchers("/api/v1/auth/**").permitAll().antMatchers("/api/v2/auth/**").permitAll().antMatchers("/api/v3/auth/**")
				.permitAll().antMatchers("/actuator/**").permitAll().antMatchers("/api/v1/classStandard/**").permitAll()
				.antMatchers("/api/v1/lead/**").permitAll().antMatchers("/api/v1/batch/subjects").permitAll()
				.antMatchers("/api/v1/subject/list").permitAll().antMatchers("/api/v1/subject/{idSubject}/chapters")
				.permitAll().antMatchers("/api/v1/teacher/fetch-all-teachers").permitAll()
				.antMatchers("/api/v1/subscription/paymentresponse").permitAll().antMatchers("/api/v1/social-video/")
				.permitAll().antMatchers("/api/v1/social-video/fetch-all-comments").permitAll()
				.antMatchers("/api/v1/social-video/latest").permitAll().antMatchers("/api/v1/social-video/id/**")
				.permitAll().antMatchers("/api/v1/state/**").permitAll()
				.antMatchers("/api/v1/subscription/subject-info").permitAll()
				.antMatchers("/api/v1/subscription/streaming-info").permitAll()
				.antMatchers("http://localhost:4200/component/offline").permitAll().antMatchers("/api/v1/discover/**")
				.permitAll().antMatchers("/api/v1/liveClass/future-class/").permitAll()
				.antMatchers("/api/v1/liveClass/**").permitAll().antMatchers("/api/v1/liveClass/extra-broadcast/**")
				.permitAll().antMatchers("/api/v1/teacher/getteacherByCategory").permitAll()
				.antMatchers("/api/v1/teacher/teacher-by-category").permitAll().antMatchers("/api/v1/teacher/search/**")
				.permitAll().antMatchers("/api/v1/democlass/getLeastFutureDemoClass").permitAll()
				.antMatchers("/api/v1/liveClass/getLiveAcademicClasses/**").permitAll()
				.antMatchers("/api/v1/liveClass/getUpcomingAcademicClasses/**").permitAll()
				.antMatchers("/api/v1/liveClass/getLiveExtraCurricular/**").permitAll()
				.antMatchers("/api/v1/liveClass/getUpcomingExtraCurricular/**").permitAll()
				.antMatchers("/api/v1/subject/academic").permitAll().antMatchers("/api/v1/subject/eca").permitAll()
				.antMatchers("/api/v1/subject/batch").permitAll().antMatchers("/api/v1/student/medium").permitAll()
				.antMatchers("/ws/**").permitAll().antMatchers("/api/v1/liveClass/allcategory-live-classes/**")
				.permitAll().antMatchers("/api/v1/liveClass/all-broadcast/**").permitAll()
				.antMatchers("/api/v1/liveClass/getBroadCastVideo/**").permitAll()
				.antMatchers("https://vistaslearning.com/component/offline").permitAll()
				.antMatchers("/api/v1/batch/weekcode").permitAll().antMatchers("/api/v1/social-video/popular")
				.permitAll().antMatchers("/api/v1/batch/eca-personal-coaching-info").permitAll()
				.antMatchers("/api/v1/batch/getBatchInformationForPersonalCoaching").permitAll()
				.antMatchers("/api/v1/batch/getProductLineForBatchOfOneStudents").permitAll()
				.antMatchers("/api/v1/batch/getProductLineForBatchOfOneStudentsExtraCurricular").permitAll()
				.antMatchers("/api/v1/batch/browse-course").permitAll().antMatchers("/api/v1/special-offer/all")
				.permitAll().antMatchers("/api/v1/authorize/refreshtoken/**").permitAll()
				.antMatchers("/api/v1/Product/user-subscription-product/**").permitAll()
				.antMatchers("/api/v1/blog/**").permitAll()
				.antMatchers("/api/v1/blog-category/").permitAll()
				.antMatchers("/api/v1/blog-category/get-all-blogs-cat").permitAll()
				.antMatchers("/api/v1/blog-category/get-all-blogs-cat-public").permitAll()
				.antMatchers("/api/v1/blog-comments/listby-blog/**").permitAll()
				.antMatchers("/api/v1/subject/browse-course").permitAll()
				.antMatchers("/api/v1/discover/category/{idDiscoverVideoCategory").permitAll()
				.antMatchers("/api/v1/social-video/category/{idCategory}/videos").permitAll()
				.antMatchers("/api/v1/subject/academic/list").permitAll()
				.antMatchers("/api/v1/contact-us").permitAll()
				.antMatchers("/api/v1/exam-preparation/**").permitAll()
				.antMatchers("/api/v1/subject/{categoryCode}/getSubjects").permitAll()
				.antMatchers("/api/v1/faq/**").permitAll()
				.antMatchers("/api/v1/share/**").permitAll()
				.antMatchers("/api/v1/syllabus/**").permitAll()
				.antMatchers("/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
						"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**",
						"/webjars/**", "/swagger-ui.html")
				.permitAll()
				.antMatchers("/api/v1/subscription/payment-intial-token").permitAll()
				.antMatchers("/api/v1/special-offer/get-coupon-description").permitAll()
				.antMatchers("/api/v2/Product/user-subscription-product").permitAll()
				.antMatchers("/api/v1/offlineCourse/vdocipher-web-hook").permitAll()
				.antMatchers("/api/v1/subject/chapter-filter").permitAll()
				.antMatchers("/api/v1/subject/static-extracurr-subject").permitAll()
				.antMatchers("/api/v1/auth/save-subscription-click-details").permitAll()
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler);
		// Rate limiting filter runs first — before JWT authentication
		http.addFilterBefore(rateLimitFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public RateLimitFilter rateLimitFilter() {
		return new RateLimitFilter();
	}

}
