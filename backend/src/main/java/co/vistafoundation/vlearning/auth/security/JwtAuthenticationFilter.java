/**
 * 
 */
package co.vistafoundation.vlearning.auth.security;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import co.vistafoundation.vlearning.analytics.model.UserActivityLiveLogs;
import co.vistafoundation.vlearning.user_activity.UserActivity;
import co.vistafoundation.vlearning.user_activity.config.LoggedUserHandler;
import co.vistafoundation.vlearning.utils.LambdaInvoker;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * @author vk
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private LoggedUserHandler loggedUserHandler;

	@Autowired
	LambdaInvoker lambdaInvoker;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (request.getServletPath().equals("/api/v1/authorize/refreshtoken")) {
			filterChain.doFilter(request, response);
		} else {
			try {
				String header = request.getHeader("Authorization");
				Long userSurId = null;
				String authToken = null;
				if (header != null && header.startsWith("Bearer ")) {
					authToken = header.replace("Bearer ", "");
					try {
						userSurId = tokenProvider.getUserId(authToken);
					} catch (IllegalArgumentException e) {
						logger.error("an error occured during getting username from token", e);
					} catch (ExpiredJwtException e) {
						logger.warn("The token is expired and not valid anymore", e);
					} catch (SignatureException e) {
						logger.error("Authentication Failed. Username or Password not valid.");
					}
				}

				if (userSurId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserById(userSurId);

					if (tokenProvider.validateToken(authToken, userDetails)) {

						String device = tokenProvider.getAudienceType(authToken);

						UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(authToken,
								userDetails);
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						String ipAddress = request.getHeader("X-Forwarded-For") == null ? "NA"
								: request.getHeader("X-Forwarded-For");
						loggedUserHandler
								.updateUserCount(new UserActivity(userDetails.getUsername(), userDetails.getUserSurId(),
										Instant.now().toString(), userDetails.getFirstName(), device));

						loggedUserHandler.addUserActivity(new UserActivityLiveLogs(userDetails.getUsername(),
								userDetails.getUserSurId(), userDetails.getFirstName(), device));

						lambdaInvoker.userActivityLamda(userSurId, device == null ? "NA" : device, Instant.now(),
								ipAddress);
					}
				}

			} catch (Exception ex) {
				logger.error("Could not set user authentication in security context", ex);
			}
			filterChain.doFilter(request, response);
		}

	}

	@SuppressWarnings("unused")
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
