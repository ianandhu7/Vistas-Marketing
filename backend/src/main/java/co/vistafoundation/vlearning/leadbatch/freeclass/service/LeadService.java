/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.service;

import java.util.List;

import javax.validation.Valid;

import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsExtraCurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchExtraCurDetailDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetailsExtraCurricular;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public interface LeadService {

	@SuppressWarnings("rawtypes")
	Document saveLeadData(LeadRequest leadBatchRequest);

	@SuppressWarnings("rawtypes")
	Document saveLeadBatchDetailsData(LeadBatchDetailsRequest leadBatchDetailsRequest);

	@SuppressWarnings("rawtypes")
	Document fetchAllSyllabus();

	@SuppressWarnings("rawtypes")
	Document sendOtp(String mobile, String email, String name);
	
	@SuppressWarnings("rawtypes")
	Document verifyOtp(String mobile, String otp);
	
	@SuppressWarnings("rawtypes")
	Document sendOtpForResetPassword(String mobile);
	
	@SuppressWarnings("rawtypes")
	Document verifyOtpForResetPassword(String mobile, String otp);

	User registerUser(@Valid SignUpRequest signUpRequest);

	@SuppressWarnings("rawtypes")
	Document checkMobileNumberExistence(String mobile);

	@SuppressWarnings("rawtypes")
	Document fetchAllAvailableSlots();

	@SuppressWarnings("rawtypes")
	Document saveLeadBatchDetailsDataAfterLogginInBookFreeClass(LeadBatchDetailsRequest leadBatchDetailsRequest);

	@SuppressWarnings("rawtypes")
	Document saveLeadBatchDetailsExtraCurricularData(
			LeadBatchDetailsExtraCurricularDTO leadBatchDetailsExtraCurricular);

	@SuppressWarnings("rawtypes")
	Document saveLeadBatchExtraCurricularDetailsDataAfterLoggedIn(
			LeadBatchDetailsExtraCurricular leadBatchDetailsExtraCurricular);

	@SuppressWarnings("rawtypes")
	Document fetchAllLevelsExtraCurricular();

	@SuppressWarnings("rawtypes")
	Document checkForDemoClassBookedHistoryForUser(Long userSurId, String category);

	@SuppressWarnings("rawtypes")
	Document filterExtraCurricularLeadBatchDetails(Long idSubject, Long idLevel);

	/**
	 * 
	 * @author Sajini
	 * 
	 */

	public Document<List<LeadBatchDetailsDTO>> AllLeadBatchDetailsCount();

	public Document<List<LeadBatchExtraCurDetailDTO>> getAllExtraCurLeadsCount();

	@SuppressWarnings("rawtypes")
	Document getHomepageBookFreeTrialAndPersonalCoachingData(String category, Long idVlUser, Long idStudent);

	@SuppressWarnings("rawtypes")
	Document checkForLeadBatchLogsAndLeadAttendedEntry(String category, Long idVlUser);

	@SuppressWarnings("rawtypes")
	Document checkForDemoClassBookedInLeadBatchDetails(Long userSurId, String category);

	@SuppressWarnings("rawtypes")
	Document checkForDemoClassBookedInLeadBatchLog(Long userSurId, String category);

}
