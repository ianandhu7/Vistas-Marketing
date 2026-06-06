package co.vistafoundation.vlearning.auth.service;

import org.springframework.mobile.device.Device;

import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;

public interface AuthService2 {


	public Document<?> userRegistrationNewSignUp(NewSignupRequestDTO signUpRequest, Device device,String ip,String userAgent);

	public Document<Boolean> verifyReferralCode(String referralCode);

	Document<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(LeadBatchDetailsRequest leadBatchDetailsRequest);

	Document<?> googleOAuthSignup(NewSignupRequestDTO signUpRequest, Device device,String ip, String userAgent);
	
}
