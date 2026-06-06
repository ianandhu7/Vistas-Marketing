/**
 * 
 */
package co.vistafoundation.vlearning.user.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.BulkClassStandardUpdateDTO;
import co.vistafoundation.vlearning.user.dto.DeactivatedIdsDTO;
import co.vistafoundation.vlearning.user.dto.LiveUserFilterDTO;
import co.vistafoundation.vlearning.user.dto.LiveUsersDataResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.dto.UserDeviceDetailsResponseDTO;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.StudentMedium;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentMediumRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user.repository.VlUserRepository;
import co.vistafoundation.vlearning.user.util.DeleteUser;
import co.vistafoundation.vlearning.user_activity.UserActivity;
import co.vistafoundation.vlearning.user_activity.repository.UserActivityRepository;
import co.vistafoundation.vlearning.utils.FileUploadService;

/**
 * @author vk
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	VlUserRepository vlUserRepository;

	@Autowired
	ClassRepository classStandardRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	AuthService authService;
	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserActivityRepository userActivityRepository;

	@Autowired
	DeleteUser deleteService;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	UserDeviceRepository userDeviceRepository;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	StudentMediumRepository studentMediumRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	StateRepository stateRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserCreatedDTO getUserCreatedWithinTwoDays(Long userSurId) {
		User loggedInUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
		}

		if (loggedInUser == null)
			throw new AppException("Invalid User");
		UserCreatedDTO userCreatedDTO = new UserCreatedDTO();
		User user = userRepository.findByUserSurId(loggedInUser.getUserSurId());
		if (user == null)
			return userCreatedDTO = null;
		LocalDate userCreatedDate = user.getCreatedAt().atZone(zoneIndia).toLocalDate();
		LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
		Long intervalBetweenDays = ChronoUnit.DAYS.between(userCreatedDate, dateToday);
		if (intervalBetweenDays <= 2) {
			userCreatedDTO.setCreatedAt(user.getCreatedAt());
			userCreatedDTO.setUserSurId(user.getUserSurId());
		} else {
			userCreatedDTO = null;
		}
		return userCreatedDTO;
	}

	@Override
	public Document<User> updateUserActiveFlag(Long userSurId, Boolean activeFlag) {

		Document<User> result = new Document<User>();

		try {

			User user = vlUserRepository.findByUserSurId(userSurId);

			if (user == null)
				throw new NullPointerException("No user found !");

			user.setActiveFlag(activeFlag);
			user = vlUserRepository.save(user);

			result.setData(user);
			result.setMessage("Successfully updated");
			result.setStatusCode(200);
		} catch (Exception exp) {
			result.setData(null);
			result.setMessage("Internal Server Error");
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<Object> getUserActiveStatus() {

		Document<Object> result = new Document<Object>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			Map<String, Object> temp = new HashMap<>();
			temp.put("isActive", loggedInUser.getActiveFlag());

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

	@Override
	public Document<Student> updateStudentEditFlag(Long userSurId) {
		Document<Student> result = new Document<Student>();
		try {
			Student student = studentRepository.getStudentByUser_UserSurId(userSurId);
			boolean validation = student.getIsProfileEdited() ? false : true;
			if (validation) {
				result.setData(null);
				result.setMessage("Student edit flag is already updated or not edited yet");
				result.setStatusCode(500);
				return result;
			}
			student.setUpdatedAt(student.getCreatedAt());
			student.setIsProfileEdited(false);
			student = studentRepository.save(student);
			result.setData(student);
			result.setMessage("Student flag successfully updated");
			result.setStatusCode(200);
		} catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<Map<String, Long>> getUserAnalytics() {

		Document<Map<String, Long>> result = new Document<>();
		Long totalActiveUsers = userRepository.getActiveStudentCount();

		Long totalPaidAndActiveUsers = studentSubscriptionRepository.getTotalSubscribersCount();

		Long totalSubscriptionExpiredUsers = studentSubscriptionRepository.getExpiredSubscribersCount();

		Long totalFreeSubscriptions = totalActiveUsers - totalPaidAndActiveUsers;

		Map<String, Long> analyticsMap = new HashMap<>();

		analyticsMap.put("totalActiveUsers", totalActiveUsers);
		analyticsMap.put("totalPaidAndActiveUsers", totalPaidAndActiveUsers);
		analyticsMap.put("totalSubscriptionExpiredUsers", totalSubscriptionExpiredUsers);
		analyticsMap.put("totalFreeSubscriptions", totalFreeSubscriptions);
		result.setData(analyticsMap);
		result.setMessage("Successfull");
		result.setStatusCode(200);

		return result;
	}

	@Override
	public Document<Map<String, Object>> resetUserPassword(Long idVluser) {

		Document<Map<String, Object>> result = new Document<>();

		try {

			User user = userRepository.findByUserSurId(idVluser);

			if (user == null)

				throw new NullPointerException("User Record not found!");

			Student student = studentRepository.findByUser(user);

			if (student == null)

				throw new NullPointerException(
						"User password reset only allowed for student. Student record not found.");

			String userMobile = user.getMobileNumber();
			String staticCharacters = "vista@";
			// In case user dosen't gave any mobile numbers. By default dynamic character
			// will be 1234.
			String dynamicCharacters = userMobile != null ? userMobile.substring(userMobile.length() - 4) : "1234";

			String finalGeneratedPassword = staticCharacters + dynamicCharacters;

			String hashedPassword = passwordEncoder.encode(finalGeneratedPassword);

			// updating the user password.

			user.setPassword(hashedPassword);

			userRepository.save(user);

			Map<String, Object> userData = new HashMap<>();

			userData.put("un", user.getUsername());
			userData.put("ufn", user.getFirstName());
			userData.put("umn", user.getMobileNumber());
			userData.put("uup", finalGeneratedPassword);

			result.setData(userData);
			result.setMessage("user password reseted sucessfully!");
			result.setStatusCode(200);
		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;
	}

	@Override
	public Document<Map<String, Object>> getUserLiveCountFromActiveStore() {
		Document<Map<String, Object>> result = new Document<>();

		try {
			Map<String, Object> response = new HashMap<>();

			List<UserActivity> userActvities = userActivityRepository.getAllUserActivity();

			if (userActvities.isEmpty()) {
				response.put("mobileActiveSession", 0);
				response.put("mobileInActiveSession", 0);
				response.put("webActiveSession", 0);
				response.put("webInActiveSession", 0);
				response.put("totalSession", 0);

			} else {
				int mobileActiveSession, mobileInActiveSession, webActiveSession, webInActiveSession;

				mobileActiveSession = 0;
				mobileInActiveSession = 0;
				webActiveSession = 0;
				webInActiveSession = 0;

				for (UserActivity ua : userActvities) {
					Instant thresholdTime = Instant.now().minus(30, ChronoUnit.MINUTES);

					if (Instant.parse(ua.getLastActivityTime()).compareTo(thresholdTime) > 0) {
						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileActiveSession++;
						else
							webActiveSession++;

					} else {

						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileInActiveSession++;
						else
							webInActiveSession++;
					}

				}

				response.put("mobileActiveSession", mobileActiveSession);
				response.put("mobileInActiveSession", mobileInActiveSession);
				response.put("webActiveSession", webActiveSession);
				response.put("webInActiveSession", webInActiveSession);
				response.put("totalSession", userActvities.size());

			}

			result.setData(response);
			result.setMessage("user online  active status fetched sucessfully!");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;
	}

	@Override
	public Document<List<UserActivity>> getListOfUserActivity() {

		Document<List<UserActivity>> result = new Document<>();

		try {

			List<UserActivity> userActvities = userActivityRepository.getAllUserActivity();

			result.setData(userActvities);
			result.setMessage("user online active status fetched sucessfully!");
			result.setStatusCode(200);

		} catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;
	}

	/**
	 * @author Abdul Elahi
	 * 
	 *         Deletes a user and associated data from the system.
	 * 
	 * @param idvlUser The ID of the user to be deleted.
	 * @return A {@link Document} object containing the result of the deletion
	 *         operation.
	 * 
	 * @throws AppException If an application-specific error occurs during the
	 *                      deletion process, such as invalid user, unauthorized
	 *                      access, or error in the environment.
	 * 
	 * @throws Exception    If an unexpected error occurs during the deletion
	 *                      process. This could include database errors or other
	 *                      runtime exceptions.
	 */

	@Value("${SWAGGER_UI_FLAG}")
	Boolean ACTIVE_FLAG;

	@Override
	@Transactional
	public Document<?> deleteUser(Long idvlUser) {
		Document<Boolean> result = new Document<>();
		System.out.println("--------------------------------------------");
		try {
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (loggedInUser.getUserSurId() != 1)
				throw new AppException("Unauthorized to delete User");

			if (!ACTIVE_FLAG)
				throw new AppException("Invalid Enviornment");

			try {
				System.out.println("Deleting user with id: " + idvlUser);

				User user = userRepository.findByUserSurId(idvlUser);
				if (user == null)
					throw new AppException("User Details not found");

				if (!user.getRegisteredAs().equalsIgnoreCase("Student"))
					throw new AppException("Only Student Account can be deleted");

				boolean response = deleteService.deleteDataForIdvlUser(idvlUser);
				if (!response)
					throw new AppException("Error occurred while deleting the User. Please Refer Logs");

				result.setData(response);
				result.setMessage("User " + user.getFirstName() + " Associated with User Sur Id " + user.getUserSurId()
						+ " deleted successfully");
				result.setStatusCode(HttpStatus.ACCEPTED.value());

			} catch (Exception exp) {
				exp.printStackTrace();

				result.setData(null);
				result.setMessage("Error occurred while deleting user and associated data: " + exp.getMessage());
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

//	@Override
	public Document<?> getLiveUsersFromActiveStoree(String type) {
		Document<Map<String, Object>> result = new Document<>();

		try {
			Map<String, Object> response = new HashMap<>();

			List<UserActivity> userActvities = userActivityRepository.getAllUserActivity();

			List<LiveUsersDataResponseDTO> liveUsersData = new ArrayList<>();

			if (userActvities.isEmpty()) {
				response.put("mobileActiveSession", 0);
				response.put("mobileInActiveSession", 0);
				response.put("webActiveSession", 0);
				response.put("webInActiveSession", 0);
				response.put("totalSession", 0);

			} else {
				int mobileActiveSession = 0;
				int mobileInActiveSession = 0;
				int webActiveSession = 0;
				int webInActiveSession = 0;

				Instant thresholdTime = Instant.now().minus(10, ChronoUnit.MINUTES);

				for (UserActivity ua : userActvities) {

					LiveUsersDataResponseDTO dto = new LiveUsersDataResponseDTO(); // Move inside the loop
//		            dto.setRole("Admin");
					Student student = studentRepository.findByUser_userSurId(ua.getUserSurId());
//		            if (student == null) 
//		            	dto.setRole("Student");

					boolean isSubscribed = studentSubscriptionRepository
							.existsByIdproductLineAndUserSurIdAndActiveFlag(11L, ua.getUserSurId(), true);

					dto.setUserSurId(ua.getUserSurId());
					dto.setName(ua.getName());
					dto.setSchool(student == null ? " " : student.getSchoolName());
					dto.setClassStandard(student == null ? " " : student.getIdClassStandard().toString());
					dto.setSyllabus(type);
					dto.setTiming(ua.getLastActivityTime());
					dto.setuserType(isSubscribed ? "Premium" : "Basic");

					if (Instant.parse(ua.getLastActivityTime()).compareTo(thresholdTime) > 0) {
						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileActiveSession++;
						else
							webActiveSession++;

					} else {

						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileInActiveSession++;
						else
							webInActiveSession++;
					}
					liveUsersData.add(dto);
				}
				liveUsersData.size();

				response.put("mobileActiveSession", mobileActiveSession);
				response.put("mobileInActiveSession", mobileInActiveSession);
				response.put("webActiveSession", webActiveSession);
				response.put("webInActiveSession", webInActiveSession);
				response.put("totalSession", userActvities.size());
				response.put("data size", liveUsersData.size());

			}

			result.setData(response);
			result.setMessage("user online  active status fetched sucessfully!");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;

	}

	@Override
	public Document<Page<LiveUsersDataResponseDTO>> getLiveUsersFromActiveStore(LiveUserFilterDTO filter) {
		Document<Page<LiveUsersDataResponseDTO>> result = new Document<>();
		System.out.println("Creating PageRequest with page: " + filter.getPage() + " and size: {}" + filter.getSize());

		Pageable paging = PageRequest.of(filter.getPage(), filter.getSize());
		Long classStandardId = filter.getIdClasssStandard() == -1 ? null : filter.getIdClasssStandard();
		Long idstate = filter.getIdState() == -1 ? null : filter.getIdState();
		Long syllabusId = filter.getIdSyllabus() == -1 ? null : filter.getIdSyllabus();
		Long userSurId = filter.getUserSurID() == -1 ? null : filter.getUserSurID();

		try {
			List<UserActivity> userActivities = userActivityRepository.getAllUserActivity();
			List<LiveUsersDataResponseDTO> liveUsersData = new ArrayList<>();
			List<Syllabus> syllabuses = syllabusRepository.findAll();
			List<ClassStandard> classStandards = classStandardRepository.findAll();

			int mobileActiveSession = 0;
			int mobileInactiveSession = 0;
			int webActiveSession = 0;
			int webInactiveSession = 0;
			Instant thresholdTime = Instant.now().minus(10, ChronoUnit.MINUTES);

			for (UserActivity ua : userActivities) {
				Student student = studentRepository.findByUser_userSurId(ua.getUserSurId());
				String syllabusName = null;
				String classStandardName = null;
				UserDevice userDevice = null;

				if (student != null) {
					userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(ua.getUserSurId(),
							ua.getDeviceType().toLowerCase().trim());

					syllabusName = syllabuses.stream().filter(s -> s.getIdSyllabus().equals(student.getIdSyllabus()))
							.map(Syllabus::getSyllabusName).findFirst().orElse(null);

					classStandardName = classStandards.stream()
							.filter(cs -> cs.getIdClassStandard().equals(student.getIdClassStandard()))
							.map(ClassStandard::getClassStandadName).findFirst().orElse(null);
				}

				boolean isSubscribed = studentSubscriptionRepository.existsByIdproductLineAndUserSurIdAndActiveFlag(11L,
						ua.getUserSurId(), true);
				boolean isActive = Instant.parse(ua.getLastActivityTime()).isAfter(thresholdTime);
				boolean isMobile = "mobile".equalsIgnoreCase(ua.getDeviceType());

				// Filtering based on parameters
				if ((classStandardId == null
						|| (student != null && student.getIdClassStandard().equals(classStandardId)))
						&& (idstate == null || (student != null && idstate.equals(student.getIdState())))
						&& (syllabusId == null || (student != null && student.getIdSyllabus().equals(syllabusId)))
						&& (filter.getSchoolName() == null || (student != null && student.getSchoolName() != null
								&& student.getSchoolName().toLowerCase().trim()
										.contains(filter.getSchoolName().toLowerCase().trim())))
						&& (userSurId == null || ua.getUserSurId().equals(userSurId))
						&& (filter.getUserName() == null || ua.getUserName().toLowerCase().trim()
								.contains(filter.getUserName().toLowerCase().trim()))) {

					LiveUsersDataResponseDTO dto = new LiveUsersDataResponseDTO();
					dto.setUserSurId(ua.getUserSurId());
					dto.setPhone(ua.getUserName());
					dto.setName(ua.getName());
					dto.setSchool(student == null ? "" : student.getSchoolName());
					dto.setClassStandard(classStandardName == null ? "" : classStandardName);
					dto.setSyllabus(syllabusName == null ? "" : syllabusName);
					dto.setTiming(ua.getLastActivityTime());
					dto.setuserType(isSubscribed ? "Premium" : "Basic");
					if (userDevice != null) {
						dto.setdeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
								: userDevice.getDeviceLocation());
					} else {
						dto.setdeviceLocation("");
					}
//					dto.setdeviceLocation(userDevice != null ? userDevice.getDeviceLocation() : "");

					switch (filter.getType().toLowerCase()) {

					case "online":
						if (isActive) {
							dto.setStatus("Online");
							dto.setdeviceType(isMobile ? "Mobile" : "Web");
							if (isMobile) {
								mobileActiveSession++;
							} else {
								webActiveSession++;
							}
							liveUsersData.add(dto);
						}
						break;

					case "web-online":
						if (isActive && !isMobile) {
							dto.setStatus("Online");
							dto.setdeviceType("Web");
							webActiveSession++;
							liveUsersData.add(dto);
						}
						break;

					case "mobile-online":
						if (isActive && isMobile) {
							dto.setStatus("Online");
							dto.setdeviceType("Mobile");
							mobileActiveSession++;
							liveUsersData.add(dto);
						}
						break;

					case "idle":
						if (!isActive) {
							dto.setStatus("Idle");
							dto.setdeviceType(isMobile ? "Mobile" : "Web");
							if (isMobile) {
								mobileInactiveSession++;
							} else {
								webInactiveSession++;
							}
							liveUsersData.add(dto);
						}
						break;

					case "web-idle":
						if (!isActive && !isMobile) {
							dto.setStatus("Idle");
							dto.setdeviceType("Web");
							webInactiveSession++;
							liveUsersData.add(dto);
						}
						break;

					case "mobile-idle":
						if (!isActive && isMobile) {
							dto.setStatus("Idle");
							dto.setdeviceType("Mobile");
							mobileInactiveSession++;
							liveUsersData.add(dto);
						}
						break;

					case "total":
						dto.setStatus(isActive ? "Online" : "Idle");
						dto.setdeviceType(isMobile ? "Mobile" : "Web");
						if (isMobile && isActive) {
							mobileActiveSession++;
						} else if (isMobile && !isActive) {
							mobileInactiveSession++;
						} else if (!isMobile && isActive) {
							webActiveSession++;
						} else {
							webInactiveSession++;
						}
						liveUsersData.add(dto);
						break;

					default:
						throw new AppException("invalid input type " + filter.getType());
					}
				}
			}

			// Logging
			System.out.println("Total User Activities: " + userActivities.size());
			System.out.println("Total Live Users Data: " + liveUsersData.size());
			System.out.println("Mobile Online Sessions: " + mobileActiveSession);
			System.out.println("Mobile Idle Sessions: " + mobileInactiveSession);
			System.out.println("Web Online Sessions: " + webActiveSession);
			System.out.println("Web Idle Sessions: " + webInactiveSession);

			// Applying pagination
			int start = Math.min((int) paging.getOffset(), liveUsersData.size());
			int end = Math.min((start + paging.getPageSize()), liveUsersData.size());
			Page<LiveUsersDataResponseDTO> pagination = new PageImpl<>(liveUsersData.subList(start, end), paging,
					liveUsersData.size());

			// Setting response data
			result.setData(pagination);
			result.setMessage("User online active status fetched successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {
			exp.printStackTrace();
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<List<UserDeviceDetailsResponseDTO>> liveUsersLocationDetails(String status) {
		Document<List<UserDeviceDetailsResponseDTO>> result = new Document<>();

		try {
			Instant thresholdTime = Instant.now().minus(10, ChronoUnit.MINUTES);

			List<UserActivity> userActivities = userActivityRepository.getAllUserActivity();
			List<UserDeviceDetailsResponseDTO> response = new ArrayList<>();

			List<UserDevice> userDevices = userDeviceRepository.findAll();
			if (userDevices == null || userDevices.isEmpty())
				throw new AppException("No User Data found");

			for (UserActivity userActivity : userActivities) {
				UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userActivity.getUserSurId(),
						userActivity.getDeviceType().toLowerCase().trim());
//				if(userDevice==null) continue;
				UserDeviceDetailsResponseDTO resultDTO = new UserDeviceDetailsResponseDTO();

				boolean isSubscribed = studentSubscriptionRepository.existsByIdproductLineAndUserSurIdAndActiveFlag(11L,
						userActivity.getUserSurId(), true);
				boolean isActive = Instant.parse(userActivity.getLastActivityTime()).isAfter(thresholdTime);
				boolean isMobile = "mobile".equalsIgnoreCase(userActivity.getDeviceType());

				switch (status.toLowerCase()) {
				case "online":
					if (isActive) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "web-online":
					if (isActive && !isMobile) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "mobile-online":
					if (isActive && isMobile) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "idle":
					if (!isActive) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "web-idle":
					if (!isActive && !isMobile) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "mobile-idle":
					if (!isActive && isMobile) {

						resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
						if (userDevice != null) {
							resultDTO
									.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
											: userDevice.getDeviceLocation());
						} else {
							resultDTO.setDeviceLocation("");
						}
						resultDTO.setUserSurId(userActivity.getUserSurId());
						resultDTO.setDeviceType(userActivity.getDeviceType());

						response.add(resultDTO);
					}
					break;

				case "total":

					resultDTO.setUserType(isSubscribed ? "Premium" : "Basic");
					if (userDevice != null) {
						resultDTO.setDeviceLocation(userDevice.getDeviceLocation().trim().equalsIgnoreCase("NA") ? ""
								: userDevice.getDeviceLocation());
					} else {
						resultDTO.setDeviceLocation("");
					}
					resultDTO.setUserSurId(userActivity.getUserSurId());
					resultDTO.setDeviceType(userActivity.getDeviceType());

					response.add(resultDTO);
					break;

				default:
					throw new AppException("invalid input type " + status);
				}
			}
			System.out.println("size is " + response.size());
			result.setData(response);
			result.setMessage("Fetched User Details Successfully");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<?> updateClassStandard(String phoneNumber, Long idClassStandard, Long idSyllabus,
			Long idStudentMedium, Long idLanguage, Long idState, String activeStatus, String newPassword,
			Long userSurId) {
		Document<User> result = new Document<>();

		try {

			User user = (userSurId != null && userSurId != -1) ? userRepository.findByUserSurId(userSurId)
					: userRepository.findByMobileNumber(phoneNumber);

			if (user == null) {
				throw new AppException(userSurId == null ? "User not found for Id " + userSurId
						: "User not found for mobile number " + phoneNumber);
			}

			Student student = studentRepository.findByUser_userSurId(user.getUserSurId());
			if (student == null)
				throw new AppException("User is not a student");

			if (idClassStandard != -1) {
				ClassStandard classStandard = classStandardRepository.findByIdClassStandard(idClassStandard);
				if (classStandard == null)
					throw new AppException("Invalid Class Standard.");
			}

			if (idClassStandard != -1) {
				student.setIdClassStandard(idClassStandard);
				user.setClassStandard(idClassStandard);
			}

			if (idSyllabus != -1) {
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(idSyllabus);
				if (syllabus == null)
					throw new AppException("Invalid Syllabus.");
				student.setIdSyllabus(idSyllabus);
			}

			if (idStudentMedium != -1) {
				StudentMedium studentMedium = studentMediumRepository.findByIdStudentMedium(idStudentMedium);
				if (studentMedium == null)
					throw new AppException("Invalid Student Medium.");
				student.setIdStudentMedium(idStudentMedium);
			}

			if (idLanguage != -1) {
				Language language = languageRepository.findByIdLanguage(idLanguage);
				if (language == null)
					throw new AppException("Invalid Language.");
				student.setIdLangauage(idLanguage);
			}

			if (idState != -1) {
				State state = stateRepository.findByIdState(idState);
				if (state == null)
					throw new AppException("Invalid State.");
				student.setIdState(idState);
			}

			studentRepository.save(student);

			if (idClassStandard != -1) {
				user.setClassStandard(idClassStandard);
			}

			if (!activeStatus.equalsIgnoreCase("NA")) {
				user.setActiveFlag(Boolean.valueOf(activeStatus));
			}
			if (newPassword != null && !newPassword.equalsIgnoreCase("NA")) {
				user.setPassword(passwordEncoder.encode(newPassword));
			}

			userRepository.save(user);

			result.setData(user);
			result.setMessage("Updated Class Standard and Student Details Successfully");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	@Override
	public Document<?> updateClassStandardBuk(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();
		Map<String, Object> dataMap = new HashMap<>();
		List<String> errorLogList = new ArrayList<>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			List<ClassStandard> classStandards = classStandardRepository.findAll();
			List<StudentMedium> studentMediums = studentMediumRepository.findAll();
			List<State> states = stateRepository.findAll();
			List<Language> languages = languageRepository.findAll();
			List<Syllabus> syllabuses = syllabusRepository.findAll();

			errorLogList.add("Bulk Meta Data Change Process started at: " + Instant.now());
			errorLogList
					.add("Bulk Meta Data Change Uploaded started by the user : " + userPrincipal.getUserSurId() + ".");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			}

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// Read CSV data
			MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
					.readValues(file);

			// read CSV data
			List<Map<String, String>> readAll = mappingIterator.readAll();

			readAll.forEach(o -> {

				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("phone_number".trim()) || !header.containsKey("id_class_standard".trim())
						|| !header.containsKey("id_state".trim()) || !header.containsKey("idstudent_medium".trim())
						|| !header.containsKey("id_syllabus".trim()) || !header.containsKey("id_language".trim())
						|| !header.containsKey("change_password".trim()) || !header.containsKey("active_status".trim())
						|| !header.containsKey("user_sur_id")) {
					throw new AppException("Invalid file or missing header in the upload csv.");
				}
			});

			List<BulkClassStandardUpdateDTO> changeList = new ArrayList<>();
			int totalRecords = 0;

			for (Map<String, String> row : readAll) {
				totalRecords++;

				String phoneNumber = row.get("phone_number".trim());

				String userSurIdString = row.get("user_sur_id").trim().isBlank() ? null : row.get("user_sur_id").trim();

				String classStandardIdString = row.get("id_class_standard").trim().isBlank() ? null
						: row.get("id_class_standard").trim();
				Long classStandardId = classStandardIdString != null ? Long.parseLong(classStandardIdString) : -1L;

				String idStateString = row.get("id_state").trim().isBlank() ? null : row.get("id_state").trim();
				Long idState = idStateString != null ? Long.parseLong(idStateString) : -1L;

				String idSyllabusString = row.get("id_syllabus").trim().isBlank() ? null
						: row.get("id_syllabus").trim();
				Long idSyllabus = idSyllabusString != null ? Long.parseLong(idSyllabusString) : -1L;

				String idStudentMediumString = row.get("idstudent_medium").trim().isBlank() ? null
						: row.get("idstudent_medium").trim();
				Long idStudentMedium = idStudentMediumString != null ? Long.parseLong(idStudentMediumString) : -1L;

				String idLanguageString = row.get("id_language").trim().isBlank() ? null
						: row.get("id_language").trim();
				Long idLanguage = idLanguageString != null ? Long.parseLong(idLanguageString) : -1L;

				String editPassword = row.get("change_password").trim().isBlank() ? null
						: row.get("change_password").trim();

				String activeStatusString = row.get("active_status").trim().isBlank() ? null
						: row.get("active_status").trim();

				Long userSurIdLong = row.get("user_sur_id").trim().isBlank() ? null : Long.parseLong(userSurIdString);

				BulkClassStandardUpdateDTO dto = new BulkClassStandardUpdateDTO();

				if (editPassword == null || editPassword.equalsIgnoreCase("NA")) {
					dto.setIsValid(false);
					dto.setMessage("invalid password");
				}

				if (!activeStatusString.equalsIgnoreCase("NA") && !activeStatusString.equalsIgnoreCase("true")
						&& !activeStatusString.equalsIgnoreCase("false")) {
					dto.setIsValid(false);
					dto.setMessage("invalid type");
				}

				boolean isAlreadyExists = changeList.stream().anyMatch(ph -> ph.getPhone().equals(phoneNumber));
				if (isAlreadyExists) {
					dto.setIsValid(false);
					dto.setMessage("phone number already exist in csv ");
				}

				if (!phoneNumber.matches("\\d+")) {
					dto.setIsValid(false);
					dto.setMessage("invald characters in mobile number ");
				}
				if (phoneNumber.length() != 10) {
					dto.setIsValid(false);
					dto.setMessage("phone number length should be 10 digits ");
				}
				if (!phoneNumber.matches("[98762].*")) {
					dto.setIsValid(false);
					dto.setMessage(" phone number is invalid! ");
				}

				if (userSurIdLong == null) {
					dto.setIsValid(false);
					dto.setMessage(" invalid user id ");
				}
				if (userSurIdLong != null && !userSurIdString.matches("\\d+")) {
					dto.setIsValid(false);
					dto.setMessage("invald characters in user id ");
				}

				dto.setIdClassStandard(classStandardId);
				dto.setPhone(phoneNumber);
				dto.setIdLanguage(idLanguage);
				dto.setIdState(idState);
				dto.setIdStudentMedium(idStudentMedium);
				dto.setIdSyllabus(idSyllabus);
				dto.setMessage("user details updated");
				dto.setIsValid(true);
				dto.setActiveStatus(activeStatusString);
				dto.setPassword(editPassword);
				dto.setUserSurId(userSurIdLong);

				User user = (userSurIdLong != null && userSurIdLong != -1)
						? userRepository.findByUserSurId(userSurIdLong)
						: userRepository.findByMobileNumber(phoneNumber);

				if (user == null) {
					dto.setIsValid(false);
					dto.setMessage(userSurIdLong == null ? "User not found for Id " + userSurIdLong
							: "User not found for mobile number " + phoneNumber);
				} else {

					dto.setFirstName(user.getFirstName());
					dto.setUserSurId(user.getUserSurId());

					if (classStandardId == 4) {
						dto.setIsValid(false);
						dto.setMessage("Invalid class standard ID in CSV: " + classStandardId);
					}

					boolean isValidClassStandard = classStandards.stream()
							.anyMatch(cs -> cs.getIdClassStandard().equals(classStandardId));
					if (!isValidClassStandard) {
						dto.setIsValid(false);
						dto.setMessage("Invalid class standard ID in CSV: " + classStandardId);
					}

					if (idState == 6 || idState == -1) {
						dto.setIsValid(false);
						dto.setMessage("Invalid State ID in CSV: " + idState);
					}
					boolean isValidState = states.stream().anyMatch(cs -> cs.getIdState().equals(idState));
					if (!isValidState) {
						dto.setIsValid(false);
						dto.setMessage("Invalid State ID in CSV: " + idState);
					}

					if (idStudentMedium == -1) {
						dto.setIsValid(false);
						dto.setMessage("Invalid Student Medium ID in CSV: " + idStudentMedium);
					}
					boolean isValidStudentMedium = studentMediums.stream()
							.anyMatch(cs -> cs.getIdStudentMedium().equals(idStudentMedium));
					if (!isValidStudentMedium) {
						dto.setIsValid(false);
						dto.setMessage("Invalid StudentMedium ID in CSV: " + idStudentMedium);
					}

					if (idSyllabus == 4 || idSyllabus == -1) {
						dto.setIsValid(false);
						dto.setMessage("Invalid Syllabus ID in CSV: " + idSyllabus);
					}
					boolean isValidSyllabus = syllabuses.stream().anyMatch(cs -> cs.getIdSyllabus().equals(idSyllabus));
					if (!isValidSyllabus) {
						dto.setIsValid(false);
						dto.setMessage("Invalid Syllabus ID in CSV: " + idSyllabus);
					}

					if (idLanguage == -1) {
						dto.setIsValid(false);
						dto.setMessage("Invalid Language ID in CSV: " + idLanguage);
					}
					boolean isValidLanguage = languages.stream().anyMatch(cs -> cs.getIdLanguage().equals(idLanguage));
					if (!isValidLanguage) {
						dto.setIsValid(false);
						dto.setMessage("Invalid Syllabus ID in CSV: " + idSyllabus);
					}

				}
				changeList.add(dto);

			}
			long successfulCount = changeList.stream().filter(BulkClassStandardUpdateDTO::getIsValid).count();
			long rejectedRecords = totalRecords - successfulCount;

			if (rejectedRecords <= 0) {
				for (BulkClassStandardUpdateDTO bulkDTO : changeList) {

					Document<?> queryResponse = this.updateClassStandard(bulkDTO.getPhone(),
							bulkDTO.getIdClassStandard(), bulkDTO.getIdSyllabus(), bulkDTO.getIdStudentMedium(),
							bulkDTO.getIdLanguage(), bulkDTO.getIdState(), bulkDTO.getActiveStatus(),
							bulkDTO.getPassword(), bulkDTO.getUserSurId());
					System.out.println(queryResponse.getData());
					errorLogList.add("Uploaded Successfully.");
				}
			} else
				errorLogList.add("Please Correct all the details in the csv and upload again.");

			errorLogList.add("Total no of records: " + totalRecords + ".");
			errorLogList.add("Total no of valid records: " + successfulCount + ".");
			errorLogList.add("Total no of invalid records: " + rejectedRecords + ".");
			errorLogList.add("Bulk Meta Data Change Process ended at: " + Instant.now());

			// Prepare data for response
			dataMap.put("updatedRecord", changeList);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", successfulCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {

			e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {

			// Upload log file to S3 bucket
			String fileName = "BulkSignupLog" + Instant.now() + ".txt";
			try (FileWriter writer = new FileWriter(fileName)) {
				for (String log : errorLogList) {
					writer.write(log + System.lineSeparator());
				}
				File logFile = new File(fileName);
				fileUploadService.uploadFileToS3Bucket("/logs/updates/classstandard/", fileName, logFile);
				boolean isDeletedLogFile = logFile.delete();
				boolean isDeletedFile = file.delete();
				logger.info("Logs file and batch file deleted from the system: " + isDeletedLogFile + " - "
						+ isDeletedFile);
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}

		}

		return result;
	}

}
