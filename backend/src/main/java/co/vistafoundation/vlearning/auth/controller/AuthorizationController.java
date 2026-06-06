/**
 * 
 */
package co.vistafoundation.vlearning.auth.controller;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.auth.dto.TokenRefreshRequest;
import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user.util.UserSubscriptionCheck;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/authorize")
public class AuthorizationController {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;
	
	@Value("${app.jwt.name}")
	private String appName;

	@Value("${app.jwtSecret}")
	private String jwtSecret;
	
	@Autowired
	StudentRepository  studentRepository;
	
	@Autowired
	ParentRepository  parentRepository;
	
	@Autowired
	UserSubscriptionCheck userSubscriptionCheck;
	
	@Autowired
	UserDeviceRepository userDeviceRepository;
	

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

	@PostMapping(value = "/refreshtoken")
	public ResponseEntity<Document<Map<String, Object>>> refreshJWTToken(@Valid @RequestBody TokenRefreshRequest request, 
			Device device){
		Document<Map<String, Object>> document = new Document<>();
		String requestRefreshToken = request.getRefreshToken();
		
		/** 
		 * Updated by @author NaveenKumar 
		 * token add with additional claims 
		 * in order to send student meta info.
		 * user name has  info contains PII data
		 * for security reason , we constrained it
		 * from token.
		 * 
		 * */
		
		try {
			Long userSurId = null;
			Boolean validRefreshToken;
			if (requestRefreshToken != null) {
				validRefreshToken= jwtTokenProvider.isTokenExpired(requestRefreshToken);
				
				if (validRefreshToken) {
					document.setData(null);
					document.setMessage("Refresh token was expired. Please make a new signin request");
					document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				}
				userSurId = jwtTokenProvider.getUserId(requestRefreshToken);
			}

			if (userSurId != null) {
				User user = userRepository.findByUserSurId(userSurId);
				if (user==null) {
					throw new AppException("Invalid user!");
				}
				Date now = new Date();
				
				final List<RoleName> roles = user.getRoles().stream()
						.map(Role::getRoleName)
						.collect(Collectors.toList());
				final String authorities = roles.stream()
						.map(RoleName::toString)
						.collect(Collectors.joining(","));
				String audience = jwtTokenProvider.generateAudience(device);
				UserMetaClaim umc = this.getUserMetaClaim(user);
				String accessToken = Jwts.builder()
						.setIssuer(appName)
//						.setSubject(user.getUsername())
						.claim("roles", authorities)
						.claim("usid", userSurId)
						.claim("roles", authorities)
						.claim("isc", umc.getIdClassStandard())
						.claim("issy",umc.getIdSyllabus())
						.claim("isst",umc.getIdState())
						.claim("ssf",umc.getIsSubscribed())
						.setAudience(audience)
						.setIssuedAt(now)
						.setNotBefore(now)
						.setExpiration(jwtTokenProvider.generateExpirationDate(device))
						.signWith(SignatureAlgorithm.HS512, jwtSecret)
						.compact();
				
				UserDevice userdevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId, "MOBILE");
				if(userdevice != null) {
					userdevice.setUpdatedAt(Instant.now());
					userDeviceRepository.save(userdevice);
				}
				
				Map<String, Object> jwtTokens = new HashMap<>();
				jwtTokens.put("accessToken", accessToken);
				jwtTokens.put("refreshToken", requestRefreshToken);
				
				
				if (requestRefreshToken != null && accessToken != null) {
					document.setData(jwtTokens);
					document.setMessage("Token refresh is successful");
					document.setStatusCode(HttpStatus.OK.value());
				} else {
					document.setData(null);
					document.setMessage("Access Denied");
					document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				}
			}
			else 
			{  // if token dosen't contain any user id information 
				document.setData(null);
				document.setMessage("Refresh token was expired. Please make a new signin request");
				document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			}
			
		} catch (IllegalArgumentException e) {
			logger.error("An error occured during getting username from token", e);
			document.setData(null);
			document.setMessage("An error occured during getting username from token");
			document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		} catch (ExpiredJwtException e) {
			logger.warn("The refresh token is expired and not valid anymore", e);
			document.setData(null);
			document.setMessage("The refresh token is expired and not valid anymore");
			document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		} catch(SignatureException e){
			logger.error("Authentication Failed. Username or Password not valid.");
			document.setData(null);
			document.setMessage("Authentication Failed. Username or Password not valid.");
			document.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			document.setData(null);
			document.setMessage("Error while fetching access token");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	private UserMetaClaim getUserMetaClaim(User user)
	{  
		if (user.getRegisteredAs().equalsIgnoreCase("Student")) 
		 { //student information will be fetched here
			 
			 Student student = studentRepository.findByUser(user);
			 
			 if (student == null)
			 throw new AppException("Invalid Student info found in user data.");
			 StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
	         Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;

	         return new UserMetaClaim(student.getIdClassStandard(),student.getIdSyllabus(),student.getIdState(),isSubscribed);
		     
			 
		 }
		 else if(user.getRegisteredAs().equalsIgnoreCase("Parent")) 
		 {   
			 Parent parent = parentRepository.findByUser(user);
			 
			 if (parent == null)
			 throw new AppException("Invalid Parent info found in user data.");
			 StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
	         Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;

		     return new UserMetaClaim(parent.getIdClassStandard(),parent.getIdSyllabus(),parent.getIdState(),isSubscribed);
		     
		 }
		 else 
		 {
			 return new UserMetaClaim(); 
		 }
	}

}
