/**
 * 
 */
package co.vistafoundation.vlearning.auth.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.auth.config.CustomDevice;
import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author vk
 *
 */
@Component
public class JwtTokenProvider {

	@Value("${app.jwt.name}")
	private String appName;

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@Value("${app.mobile.jwtExpirationInMs}")
	private int jwtMobileExpirationInMs;
	
	@Value("${app.jwt.refresh}")
	private long jwtRefreshInMs;

	static final String AUDIENCE_UNKNOWN = "unknown";
	static final String AUDIENCE_WEB = "web";
	static final String AUDIENCE_MOBILE = "mobile";
	static final String AUDIENCE_TABLET = "tablet";
	static final String AUDIENCE_TV = "tv";

	public String generateToken(Authentication authentication, Device device,UserMetaClaim umc) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		final String authorities = userPrincipal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		String audience = generateAudience(device);
		
		/** 
		 * Updated by @author NaveenKumar 
		 * token add with additional claims 
		 * in order to send student meta info.
		 * user name has  info contains PII data
		 * for security reason , we constrained it
		 * from token.
		 * 
		 * */
		return Jwts.builder()
				.setIssuer(appName)
				//.setSubject(userPrincipal.getUsername())
				.claim("usid", userPrincipal.getUserSurId())
				.claim("roles", authorities)
				.claim("isc", umc.getIdClassStandard())
				.claim("issy",umc.getIdSyllabus())
				.claim("isst",umc.getIdState())
				.claim("ssf",umc.getIsSubscribed())
				.setAudience(audience)
				.setIssuedAt(now)
				.setNotBefore(now)
				.setExpiration(generateExpirationDate(device))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String refreshToken(Authentication authentication, Device device) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		String audience = generateAudience(device);
		return Jwts.builder()
				.setIssuer(appName)
				.claim("usid", userPrincipal.getUserSurId())
//				.setSubject(userPrincipal.getUsername()))
				.setAudience(audience)
				.setIssuedAt(now)
				.setNotBefore(now)
				.setExpiration(new Date(new Date().getTime() + jwtRefreshInMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return expiration.before(new Date());
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		return claimsResolver.apply(claims);
	}

	public Boolean validateToken(String token, UserPrincipal userprincipal) {

		Long usid = null;
		 Long isc = null;
		 Long issy = null;
		 Long isst = null;
		 Boolean ssf = null;
		try {
			usid = getUserId(token);
			isc = getUserClassStandard(token);
			issy = getUserSyllabus(token);
			isst = getUserState(token);
			ssf = getUserSubscriptionFlag(token);
		}
		catch(ExpiredJwtException eje) 
		{   //token is expired return false
			return false;
		}
		catch(PrematureJwtException pje) 
		{
			return false;
		}
		
		// This block will be executed only for student and parent roles 
		if (isc != null && issy != null && isst !=null && ssf != null)
		{
			return (usid.equals(userprincipal.getUserSurId()) && isc.equals(userprincipal.getIdClassStandard())
					&& issy.equals(userprincipal.getIdSyllabus()) && isst.equals(userprincipal.getIdState()) 
					&& !isTokenExpired(token));
		}
		else 
		{ // this block will be executed for all the internal user, since they will not have any info related to user meta 
			return (usid.equals(userprincipal.getUserSurId()) && !isTokenExpired(token));
		}
		
	}

	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		
		return ((Number) claims.get("usid")).longValue();
	}
	
	public Long getUserClassStandard(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return  (claims.get("isc") != null ? ((Number) claims.get("isc")).longValue() : null);
	}
	
	public Long getUserSyllabus(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return (claims.get("issy") != null ? ((Number) claims.get("issy")).longValue() : null);
	}
	
	public Long getUserState(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return (claims.get("isst") != null ? ((Number) claims.get("isst")).longValue() : null);
	}
	
	public String getAudienceType(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return (claims.get("isst") != null ? (String) claims.get("aud") : null);
	}
	
	public Boolean getUserSubscriptionFlag(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return  (claims.get("ssf") != null ? ((Boolean) claims.get("ssf")) : null ) ;
	}

	public UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {
		final JwtParser jwtParser = Jwts.parser().setSigningKey(jwtSecret);
		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
		final Claims claims = claimsJws.getBody();
		final Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get("roles").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public String generateAudience(Device device) {
		String audience = AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = AUDIENCE_MOBILE;
		}else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {
			audience = AUDIENCE_TV;
		}
		return audience;
	}

	public Date generateExpirationDate(Device device) {
		long expiresIn = device.isTablet() || device.isMobile() ? jwtMobileExpirationInMs : jwtExpirationInMs;
		return new Date(new Date().getTime() + expiresIn);
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader( request );
		if ( authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	public String getAuthHeaderFromHeader( HttpServletRequest request ) {
		return request.getHeader("Authorization");
	}
}