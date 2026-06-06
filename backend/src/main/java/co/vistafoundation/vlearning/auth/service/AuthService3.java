package co.vistafoundation.vlearning.auth.service;

import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.stepfunctions.builder.conditions.StringGreaterThanCondition;

import co.vistafoundation.vlearning.auth.dto.GoogleOauthSignupV3;
import co.vistafoundation.vlearning.auth.dto.ManualSignupDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupClassInfoRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV3DTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV4DTO;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequestV3;

public interface AuthService3 {
	public Document<?> userRegistrationNewSignUp(NewSignupRequestV3DTO signUpRequest, Device device,String ip,String userAgent);

	public Document<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			LeadBatchDetailsRequestV3 leadBatchDetailsRequest);

	public Document<?> googleOAuthSignup(GoogleOauthSignupV3 signUpRequest, Device device,String ip,String userAgent);
	
	public Document<?> validateUploadBatchMannualSignUp(MultipartFile batchFile);

	public Document<?> uploadBulkSignupData(MultipartFile batchFile);

	public Document<ManualSignupDTO> manualSignup(ManualSignupDTO signUpRequest);


	/**
	 * @param signUpRequest
	 * @param device
	 * @return
	 */
	Document<?> newSignup(NewSignupRequestV4DTO signUpRequest, Device device, String ip, String userAgent);

	/**
	 * @param dto
	 * @param device
	 * @return
	 */
	Document<?> fillDetailsAfterSignup(NewSignupClassInfoRequestDTO dto, Device device,String ip,String userAgent);

	public Boolean isValidDevice(String jwt, Device device, User user);

	Document<Boolean> defaultSignupPasswordValidation();

	/**
	 * @param batchFile
	 * @return
	 */
	Document<?> uploadBulkSignupDataV2(MultipartFile batchFile);



}
