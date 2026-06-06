
package co.vistafoundation.vlearning.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.vistafoundation.vlearning.auth.dto.ApiResponse;
import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.dto.LiveUserFilterDTO;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.dto.UserFetchDTO;
import co.vistafoundation.vlearning.user.dto.UserFetchDTOV2;
import co.vistafoundation.vlearning.user.dto.UserInfoDto;
import co.vistafoundation.vlearning.user.dto.UserProfileDTO;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.user.service.UserService2;
import co.vistafoundation.vlearning.user_activity.UserActivity;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	AuthService authService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserService2 userServiceV2;
	
	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/fetch-vluser-lists")
	public ResponseEntity<?> fetchAllVLUserLists(@RequestBody UserFetchDTO userFetchDTO) {
		Document<?> reponses = authService.fetchAllUserLists(userFetchDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/fetch-vluser-lists-sum")
	public ResponseEntity<?> fetchAllVLUserListsSumData() {
		Document<Map<String, Long>> reponses = authService.fetchAllUserListsSumData();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-telecaller-or-moderator")
	public ResponseEntity<?> createTelecallerOrModerator(@Valid @RequestBody SignUpRequest signUpRequest,
			Device device) {

		User result = new User();

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ApiResponse(false, "Username or Email Id is already taken!", null),
					HttpStatus.BAD_REQUEST);
		}

		result = authService.registerUser(signUpRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully", null));
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT', 'ROLE_ADMIN','ROLE_TEACHER' )")
	@GetMapping("/user-profile")
	public ResponseEntity<?> getUserProfile() {
		Document<UserProfileDTO> reponses = authService.getUserProfile();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT' )")
	@PutMapping("/user-profile")
	public ResponseEntity<?> editUserProfile(@RequestBody @Valid UserProfileDTO userProfileDTO) {
		Document<UserProfileDTO> reponses = authService.editUserProfile(userProfileDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT' )")
	@GetMapping("/user-create-date")
	public ResponseEntity<?> userCreatedDateWithinTwoDays(@RequestParam Long userSurId) {
		Document<UserCreatedDTO> document = new Document<>();
		UserCreatedDTO reponses = userService.getUserCreatedWithinTwoDays(userSurId);
		if (reponses != null) {
			document.setData(reponses);
			document.setStatusCode(200);
			document.setMessage("Date fetched successfuly");
		} else {
			document.setData(null);
			document.setStatusCode(404);
			document.setMessage("Date not found");
		}
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/update-flag")
	public ResponseEntity<?> updateUserActiveFlag(@RequestParam Long userSurId, @RequestParam Boolean activeFlag) {
		Document<User> reponses = userService.updateUserActiveFlag(userSurId, activeFlag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/update-student-edit-flag")
	public ResponseEntity<?> updateStudentEditFlag(@RequestParam Long userSurId) {
		Document<Student> reponses = userService.updateStudentEditFlag(userSurId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/status")
	public ResponseEntity<?> getActiveUserStatus() {
		Document<Object> reponses = userService.getUserActiveStatus();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/user-analytics")
	public ResponseEntity<?> getTotalUnpaidUsers() {
		Document<Map<String, Long>> document = userService.getUserAnalytics();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @apiNote This api will be used to reset password of student users from admin
	 *          panel.
	 * 
	 *          This endpoint is restricted for all the role except admin.
	 * 
	 *          Expects Request Param userSurId which is not null.
	 * 
	 * @param userSurId
	 * @return MapObject
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/reset-user-password")
	public ResponseEntity<?> resetUserPassword(@RequestParam Long userSurId) {
		Document<Map<String, Object>> document = userService.resetUserPassword(userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/activity-count")
	public ResponseEntity<?> getUserLiveCount() {
		Document<Map<String, Object>> document = userService.getUserLiveCountFromActiveStore();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-activity-list")
	public ResponseEntity<?> getUserActivityList() {
		Document<List<UserActivity>> document = userService.getListOfUserActivity();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
    /**
     * @author Abdul Elahi
     * 
     * This endpoint is restricted for all the role except admin
     *     
     * Fetches a list of VL users based on specified filters.
     *
     * @param userFetchDTO The DTO containing filter parameters for user fetching 
     * @return A ResponseEntity containing the response document containing the paginated list of VL users.
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/fetch-vluser-with-filters")
    public ResponseEntity<Document<Page<UserListDTO>>> fetchAllVLUsersLists(@RequestBody UserFetchDTOV2 userFetchDTO) {
        Document<Page<UserListDTO>> response = userServiceV2.fetchAllVLUsersLists(userFetchDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	/**
	 * @author Abdul Elahi
	 * 
	 * This endpoint is restricted for all the role except admin
	 * 
     * Fetches detailed information about a VL user based user sur id.
     *
     * @param userSurId The unique identifier of the VL user.
     * @return A ResponseEntity containing the response document containing the detailed information about the VL user.
     */
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{userSurId}/info")
    public ResponseEntity< Document<UserInfoDto>> fetchAllVLUsersInfo(@PathVariable Long userSurId) {
        Document<UserInfoDto> response = userServiceV2.fetchAllVLUsersinfo(userSurId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	/**
	 * @author Abdul Elahi
	 * 
	 * This endpoint is used delete users
     *
     * @param userSurId The unique identifier of the VL user.
     * @return response message 
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/delete-user/{idvlUser}")
	public ResponseEntity<?> deleteUser(@PathVariable Long idvlUser) {
	    Document<?> response = userService.deleteUser(idvlUser);
	    return ResponseEntity.status(response.getStatusCode()).body(response); 
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/live-user-count")
	public ResponseEntity<?> getLiveUsersFromActiveStore(@RequestBody LiveUserFilterDTO filters) {
		Document<?> document = userService.getLiveUsersFromActiveStore(filters);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/live-users-Location")
	public ResponseEntity<?> getLiveUsersLocationdetails(@RequestParam String status) {
		Document<?> document = userService.liveUsersLocationDetails(status);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/update-class-standard")
	public ResponseEntity<?> updateClassStandard(@RequestParam String phoneNumber,@RequestParam(defaultValue = "-1") Long idClassStandard
			,@RequestParam(defaultValue = "-1") Long idSyllabus, @RequestParam(defaultValue = "-1") Long idStudentMedium,
			@RequestParam(defaultValue = "-1") Long idLanguage, @RequestParam(defaultValue = "-1") Long idState, 
			@RequestParam(defaultValue = "NA") String allowEdit, @RequestParam(defaultValue = "NA") String activeStatus
			, @RequestParam(defaultValue = "NA") String password, @RequestParam(defaultValue = "-1") Long userSurId) {
		Document<?> document = userService.updateClassStandard(phoneNumber,idClassStandard,idSyllabus,
				idStudentMedium,idLanguage,idState,activeStatus,password,userSurId );
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-update-class-standard")
	public ResponseEntity<Document<?>> uploadBulkClassStandarChange(@RequestParam("file") MultipartFile batchFile) {
		Document<?> result = userService.updateClassStandardBuk(batchFile);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@PostMapping("/batch-deactivate-users")
//	public ResponseEntity<Document<?>> uploadBulkIdDeactivate(@RequestParam("file") MultipartFile batchFile) {
//		Document<?> result = userService.deactivateIdsBulk(batchFile);
//		return ResponseEntity.status(result.getStatusCode()).body(result);
//	}

}