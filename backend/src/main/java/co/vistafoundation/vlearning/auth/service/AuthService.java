/**
 * 
 */
package co.vistafoundation.vlearning.auth.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.mobile.device.Device;

import co.vistafoundation.vlearning.auth.dto.AfterSignUpMetadataDTO;
import co.vistafoundation.vlearning.auth.dto.GoogleLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.InternalLoginRequest;
import co.vistafoundation.vlearning.auth.dto.LoginRequest;
import co.vistafoundation.vlearning.auth.dto.MobileNumberLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.dto.SubscriptionClickDTO;
import co.vistafoundation.vlearning.auth.dto.TeacherInfoDTO;
import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.auth.model.MobileOtp;
import co.vistafoundation.vlearning.auth.model.SubscriptionClick;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.subscription.dto.FreeSignUp;
import co.vistafoundation.vlearning.user.dto.UserFetchDTO;
import co.vistafoundation.vlearning.user.dto.UserProfileDTO;
import co.vistafoundation.vlearning.user.model.Language;

/**
 * @author vk
 *
 */
public interface AuthService {

	public Document<?> authenticateUser(LoginRequest loginRequest, Device device, String ip, String userAgent);

	@SuppressWarnings("rawtypes")
	public Document authenticateInternalUser(InternalLoginRequest loginRequest, Device device);

	public User registerUser(SignUpRequest signUpRequest);

	@SuppressWarnings("rawtypes")
	public Document getIdsOfdifferentRole(String role, Long userId);

	public Document<?> resetPassword(Long idVLUser, String oldPassword, String newPassword);

	@SuppressWarnings("rawtypes")
	public Document forgotPassword(String forgotPasswordUsername);

	@SuppressWarnings("rawtypes")
	public Document verifyForgotPassword(Long userSurId, String randomString, String forgotPasswordUsername,
			String newPassword);

	public Document<Page<UserListDTO>> fetchAllUserLists(UserFetchDTO userFetchDTO);

	public Document<Map<String, Long>> fetchAllUserListsSumData();

	public Document<List<Language>> getAllLanguage();

	@SuppressWarnings("rawtypes")
	public Document registerUserforFree(FreeSignUp signUpRequest, Device device);

	public Document<Page<TeacherInfoDTO>> getAllTeachersByIdSubjectAndSyllabusAndClassStandrdAndCategory(Long idSubject,
			Long idClassStandard, Long idSyllabus, String category, int pageNumber);

	@SuppressWarnings("rawtypes")
	public Document getTeacherProfile(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document getAllState();

	@SuppressWarnings("rawtypes")
	public Document userRegistrationNewSignUp(@Valid NewSignupRequestDTO signUpRequest, Device device, String ip,
			String userAgent);

	@SuppressWarnings("rawtypes")
	public Document takeMetadataAfterSignup(AfterSignUpMetadataDTO afterSignUpMetadataDTO);

	@SuppressWarnings("rawtypes")
	public Document getAllTeacher();

	@SuppressWarnings("rawtypes")
	public Document googleOAuthSignup(NewSignupRequestDTO signupRequestDTO, Device device, String ip, String userAgent);

	@SuppressWarnings("rawtypes")
	public Document googleOAuthSignin(GoogleLoginRequestDTO googleLoginRequestDTO, Device device, String ip,
			String userAgent);

	public Document<UserProfileDTO> getUserProfile();

	public Document<UserProfileDTO> editUserProfile(UserProfileDTO userProfileDTO);

	public Document<List<LevelExtraCurricular>> getAllExtraLevels();

	public Document<Boolean> sendOTPForUserLogin(String mobileNumber);

	public Document<Map<String, Object>> verifyOTPForUserLogin(MobileNumberLoginRequestDTO request, Device device,
			String ip, String userAgent);

	@SuppressWarnings("rawtypes")
	public Document forgotPasswordInternaluser(String forgotUsername);

	public Document<String> logoutUser(String actualDevice, Device device);

	public Document<Map<String, Object>> getInternalIdOfdifferentRole(String role, Long userId);

	public Long getActiveStudentCount();

	public Boolean checkAndUpdateUserDeviceId(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device, JSONObject deviceLocation, String userAgent) throws Exception;

	public MobileOtp getMobileOtp(String mobile) throws Exception;

	public MobileOtp verifyMobileOTP(String mobile, String otp) throws Exception;

	public Document<Boolean> getUserNameAvailablity(String userName);

	/**
	 * @param userSurId
	 * @param device
	 * @return
	 */
	Document<?> getUserLoggedinDevices();

	/**
	 * @param idUserDevice
	 * @return
	 */
	Document<String> logoutFromDevices(Long idUserDevice, boolean allDevices);

	public Document<?> getDevicesHistory();
	
	public Document<String> resetMaxAttempts(Long idVlUser);

	public Document<String> saveSubscriptionClickDetails(SubscriptionClickDTO subscriptionClickDTO);

	public Document<List<SubscriptionClick>> getAllSubsriptionTickets();

	public Document<Boolean> getSubscriptionClickStatus();

}
