/**
 * 
 */
package co.vistafoundation.vlearning.user.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.exception.AppException;

/**
 * @author NAVEEN
 *
 */

@Service
public class UserContentAccessValidator {

	public Boolean isAllowedSubscription(Boolean execlusiveFlag) {
		UserPrincipal userPrincipal = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}

		if (userPrincipal == null)
			throw new AppException("Invalid user details found in user principal while fetching subscripitioni info");

		if (execlusiveFlag) {
			return userPrincipal.getIsSubscribed() ? true : false;
		}

		return false;
	}

	public Boolean isAllowedToAccessTheContent(Long idClasstandard, Long idSyllabus, Long idState) {
		UserPrincipal userPrincipal = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}
		
		
		
		//since extracurricullar dosent have any constrain as of we can give access to this content fully
		
		if (idClasstandard.equals(4L) && idSyllabus.equals(4L) && idState.equals(6L) ) 
		{
			return true;
		}
		 

		if (userPrincipal == null)
			throw new AppException("Invalid user details found in user principal while fetching subscripitioni info");

		return (idClasstandard.equals(userPrincipal.getIdClassStandard())
				&& idSyllabus.equals(userPrincipal.getIdSyllabus()) && idState.equals(userPrincipal.getIdState()))
						? true
						: false;

	}

}
