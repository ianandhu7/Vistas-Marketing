/**
 * 
 */
package co.vistafoundation.vlearning.auth.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.exception.ResourceNotFoundException;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.util.UserSubscriptionCheck;

/**
 * @author vk
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentRepository  studentRepository;
	
	@Autowired
	ParentRepository  parentRepository;
	
	@Autowired
	UserSubscriptionCheck userSubscriptionCheck;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		/**
		 * updated by @author Naveen Kumar
		 * 
		 */
		// Let people login with either email id (which will act like username)
		
		User user = userRepository.findByUsername(usernameOrEmail);

		if (user == null)
			throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
		
		 if (user.getRegisteredAs().equalsIgnoreCase("Student")) 
		 { //student information will be fetched here
			 
			 Student student = studentRepository.findByUser(user);
			 
			 if (student == null)
			 throw new AppException("Invalid Student info found in user data.");
			 StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
			 Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;
		     UserMetaClaim umc = new UserMetaClaim(student.getIdClassStandard(),student.getIdSyllabus(),student.getIdState(),isSubscribed);
		     return UserPrincipal.create(user,umc);
			 
		 }
		 else if(user.getRegisteredAs().equalsIgnoreCase("Parent")) 
		 {   
			 Parent parent = parentRepository.findByUser(user);
			 
			 if (parent == null)
			 throw new AppException("Invalid Parent info found in user data.");
             StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
	         Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;

		     UserMetaClaim umc = new UserMetaClaim(parent.getIdClassStandard(),parent.getIdSyllabus(),parent.getIdState(),isSubscribed);
		     return UserPrincipal.create(user,umc);
		 }
		 else 
		 {
			 return UserPrincipal.create(user,new UserMetaClaim() ); 
		 }
		 

		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set getAuthority(User user) {
        Set authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
		});
		return authorities;
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		 if (user.getRegisteredAs().equalsIgnoreCase("Student")) 
		 { //student information will be fetched here
			 
			 Student student = studentRepository.findByUser(user);
			 
			 if (student == null)
			 throw new AppException("Invalid Student info found in user data.");
			 StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
	         Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;

		     UserMetaClaim umc = new UserMetaClaim(student.getIdClassStandard(),student.getIdSyllabus(),student.getIdState(),isSubscribed);
		     return UserPrincipal.create(user,umc);
			 
		 }
		 else if(user.getRegisteredAs().equalsIgnoreCase("Parent")) 
		 {   
			 Parent parent = parentRepository.findByUser(user);
			 
			 if (parent == null)
			 throw new AppException("Invalid Parent info found in user data.");
			 StudentPostLoginDTO spl = userSubscriptionCheck.checkExistingSubscriptionLogin(user.getUserSurId());
	         Boolean isSubscribed = (spl.getSubscribedFlag() || spl.getTrialFlag() ) ? true : false;

		     UserMetaClaim umc = new UserMetaClaim(parent.getIdClassStandard(),parent.getIdSyllabus(),parent.getIdState(),isSubscribed);
		     return UserPrincipal.create(user,umc);
		 }
		 else 
		 {
			 return UserPrincipal.create(user,new UserMetaClaim() ); 
		 }
		 
	}
}