package co.vistafoundation.vlearning.user.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.config.CustomDevice;
import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.auth.service.AuthService3;
import co.vistafoundation.vlearning.auth.service.AuthService3Impl;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.UserFetchDTOV2;
import co.vistafoundation.vlearning.user.dto.UserInfoDto;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;

@Service
public class UserServiceImpl2 implements UserService2 {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	AuthService3 authService3;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionrepository;
	
	@Autowired
	UserDeviceRepository userDeviceRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService3Impl.class);


	/**
	 * This method fetches a paginated list of UserListDTOs based on the provided
	 * UserFetchDTOV2 object. It retrieves user lists based on the role name, first
	 * name, email, mobile number, and optionally, date range.
	 *
	 * @param userFetchDTO The UserFetchDTOV2 object containing the search criteria.
	 * @return A Document object containing a paginated list of UserListDTOs wrapped
	 *         in a Page container. The Document also includes a status code and a
	 *         message indicating the result of the operation.
	 * 
	 * @author Abdul Elahi
	 */
	@Override
	public Document<Page<UserListDTO>> fetchAllVLUsersLists(UserFetchDTOV2 userFetchDTO) {
		Document<Page<UserListDTO>> result = new Document<>();

		try {

			Pageable paging = PageRequest.of(userFetchDTO.getPage(), userFetchDTO.getSize());
			Page<UserListDTO> usersLists = null;
			Long idSyllabus = userFetchDTO.getIdSyllabus() == -1 ? null : userFetchDTO.getIdSyllabus();
			Long idClassStandard = userFetchDTO.getIdClassStandard() == -1 ? null : userFetchDTO.getIdClassStandard();
			Long idMedium = userFetchDTO.getIdMedium() == -1 ? null : userFetchDTO.getIdMedium();
			Long idState = userFetchDTO.getIdState() == -1 ? null : userFetchDTO.getIdState();

			if (userFetchDTO.getDateFrom() != null && userFetchDTO.getDateTo() != null
					&& userFetchDTO.getRoleName().equalsIgnoreCase("Student")) {

				usersLists = userRepository.findAllRoleStudentWithDate(userFetchDTO.getRoleName(),
						userFetchDTO.getFirstName(), userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(),
						idClassStandard, idSyllabus, idMedium, idState, userFetchDTO.getDateFrom(),
						userFetchDTO.getDateTo(), paging);

			} else if (userFetchDTO.getRoleName().equalsIgnoreCase("Student")) {
				usersLists = userRepository.findAllRoleStudent(userFetchDTO.getRoleName(), userFetchDTO.getFirstName(),
						userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(), idClassStandard, idSyllabus, idMedium,
						idState, paging);

			} else if (userFetchDTO.getDateFrom() != null && userFetchDTO.getDateTo() != null
					&& !userFetchDTO.getRoleName().equalsIgnoreCase("Student")) {

				usersLists = userRepository.findAllRolesExceptStudentWithDates(userFetchDTO.getRoleName(),
						userFetchDTO.getFirstName(), userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(),
						userFetchDTO.getDateFrom(), userFetchDTO.getDateTo(), paging);
			} else if (!userFetchDTO.getRoleName().equalsIgnoreCase("Student")) {

				usersLists = userRepository.findAllRolesExceptStudent(userFetchDTO.getRoleName(),
						userFetchDTO.getFirstName(), userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(), paging);

			}

			if (usersLists==null) {
				result.setData(usersLists);
				result.setMessage("No Users Available");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			} else {
				result.setData(usersLists);
				result.setMessage("All VLUser Lists...");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	/**
	 * @author Abdul Elahi
	 * 
	 * This method fetches information about a user and constructs a UserInfoDto object
	 * containing various details about the user, including subscription information if applicable.
	 *
	 * @param userSurId The user's unique identifier.
	 * 
	 * @return A Document containing UserInfoDto with user information.
	 */
	@Override
	public Document<UserInfoDto> fetchAllVLUsersinfo(Long userSurId) {
		Document<UserInfoDto> result = new Document<>();
		try {
			User user = userRepository.findByUserSurId(userSurId);
			if (user == null) {
				throw new AppException("Invalid User info , user id: " + userSurId + " .");
			}

			Long idClassStandard = null;
			Long idSyllabus = null;
			Long idState = null;
			Long idLanguage = null;
			Instant createTime = null;
			Instant endTime = null;
			Boolean isSubscribed = false;
			String className = null;
			String secondaryLanguage = null;
			String syllabusName = null;
			String stateName = null;
			String remarks = null;

			UserInfoDto info = new UserInfoDto();

			if (user.getRegisteredAs().equalsIgnoreCase("Student")) {
				
				Student student = studentRepository.findByUser_userSurId(user.getUserSurId());

				if (student == null) {
					throw new AppException("Invalid User");
				}
				State state = stateRepository.findByIdState(student.getIdState());

				if (state == null) {
					throw new AppException("Invalid State");
				}

				ClassStandard classStandard = classRepository.findByIdClassStandard(student.getIdClassStandard());

				if (classStandard == null) {
					throw new AppException("Invalid ClassStandard");
				}

				Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());

				if (syllabus == null) {
					throw new AppException("Invalid syllabus");
				}

				Language language = languageRepository.findByIdLanguage(student.getIdLangauage());
				if (language == null) {
					throw new AppException("Invalid language");
				}

				idClassStandard = classStandard.getIdClassStandard();
				className = classStandard.getClassStandadName();
				idSyllabus = syllabus.getIdSyllabus();
				syllabusName = syllabus.getSyllabusName();
				idState = state.getIdState();
				stateName = state.getState();
				idLanguage = language.getIdLanguage();
				secondaryLanguage = language.getLanguage();
				remarks = student.getRemarks();

				StudentSubscription studentSubscription = studentSubscriptionrepository
						.findFirstByIdproductLineAndUserSurIdAndActiveFlag(11L, userSurId, Boolean.TRUE);

				if (studentSubscription != null) {
					createTime = studentSubscription.getPurchaseDate();
					endTime = studentSubscription.getSubscriptionEndDate();
					isSubscribed = true;
				}

			} else {

				idClassStandard = 4L;
				className = "NA";
				idSyllabus = 4L;
				syllabusName = "NA";
				idState = 6L;
				stateName = "NA";
				idLanguage = 0L;
				secondaryLanguage = "NA";
			}

			info.setName(user.getFirstName());
			info.setRemarks(remarks);
			info.setUserSurid(userSurId);
			info.setUserRole(user.getRegisteredAs());
			info.setSubscribed(true);
			info.setSubscriptionStartDate(createTime);
			info.setSubscriptionEndDate(endTime);
			info.setIdClassStandard(idClassStandard);
			info.setClassStandard(className);
			info.setIdSecondaryLanguage(idLanguage);
			info.setSecondaryLanguage(secondaryLanguage);
			info.setIdSyllabus(idSyllabus);
			info.setSyllabusType(syllabusName);
			info.setIdState(idState);
			info.setState(stateName);
			info.setSubscribed(isSubscribed);
			info.setUserPicURL(user.getUserProfilePic());

			result.setData(info);
			result.setMessage("Request successfull!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
		 logger.error(e.getMessage());
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<Object> getUserActiveStatus(String jwt,Device device,Boolean isBrowser) {

		Document<Object> result = new Document<Object>();
		System.out.println(jwt);

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
			
			String checkDevice = "";
			if (device.isMobile()) {
				checkDevice = "MOBILE";
				
			} else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {
				checkDevice = "TV";
				
			} else {
				checkDevice = "WEB";
			}

			UserDevice user = userDeviceRepository.findByUserSurIdAndDeviceType(loggedInUser.getUserSurId(),
					checkDevice);

		
			Map<String, Object> temp = new HashMap<>();
			temp.put("isActive", loggedInUser.getActiveFlag());
			temp.put("validFlag", true);

			Boolean authorityFlag = authService3.isValidDevice(
					jwt.trim(), device, loggedInUser);
			
			if(!authorityFlag || user==null) {
				temp.put("validFlag", authorityFlag);
				result.setData(temp);
				result.setMessage("Your session has been expired. Your account has been logged in into another device.");
				result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				
				return result;
			}

			if (!loggedInUser.getActiveFlag()) {
				result.setData(temp);
				result.setMessage("Your account is deactivated, please contact us.");
				result.setStatusCode(200);

			} else {
				result.setData(temp);
				result.setMessage("Request Successfull");
				result.setStatusCode(200);
			}

		} catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);
		}

		return result;
	}
}