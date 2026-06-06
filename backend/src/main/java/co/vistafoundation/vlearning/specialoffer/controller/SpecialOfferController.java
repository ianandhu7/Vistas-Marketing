/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.specialoffer.dto.CouponDTO;
import co.vistafoundation.vlearning.specialoffer.dto.CouponReqDTO;
import co.vistafoundation.vlearning.specialoffer.dto.UserCouponDTO;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.specialoffer.service.SpecialOfferService;


/**
 * @author Naveen Kumar
 *
 */
@RestController
@RequestMapping("/api/v1/special-offer")
public class SpecialOfferController {

	@Autowired
	SpecialOfferService specialOfferService;
	
	@Autowired
	CouponRepository couponRepository; 
	
//	@GetMapping(value = "/all")
//	public ResponseEntity<?> getbatchMetaData() {
//		Document<List<SpecialOffer>> reponses = specialOfferService.getAllSpecialOffers();
//		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
//
//	}
//	
//	@GetMapping(value = "/coupons")
//	public ResponseEntity<?> getAllCouponCodes(@RequestParam Long idStudent, @RequestParam Long userSurId) {
//		Document<List<CouponCodeDTO>> reponses = specialOfferService.getAllCouponCodes(idStudent,userSurId);
//		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
//
//	}
	

    /**
     * Creates a new coupon.
     * @author Mohan Kumar K M 
     * @param coupon       The coupon details to be created.
     * @param emailOrPhone The optional email or phone associated with the coupon.
     * @return ResponseEntity containing the created coupon or an error message.
     */
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-coupon")
	public ResponseEntity<Document<Coupon>> createCoupon(@RequestBody Coupon coupon,@RequestParam(defaultValue = "", required = false) String emailOrPhone) {
		Document<Coupon> responses = specialOfferService.createCoupon(coupon,emailOrPhone);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Mohan Kumar K M 
     * Creates multiple coupons based on the specified size.
     *
     * @param coupon The coupon details to be used as a template for creation.
     * @param size   The number of coupons to create.
     * @return ResponseEntity containing the result of the operation or an error message.
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-coupons")
	public ResponseEntity<Document<String>> createCoupons(@RequestBody Coupon coupon, int size) {
		Document<String> responses = specialOfferService.createCoupons(coupon, size);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Retrieves all coupons.
	 *
	 * @return ResponseEntity containing a document with a list of coupons or an error message.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/get-all-coupons")
	public ResponseEntity<Document<List<Coupon>>> getAllCoupons() {
		Document<List<Coupon>> reponses = specialOfferService.getAllCoupons();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
    

/**
 * @author Mohan Kumar K M 
 * Retrieves coupons based on the provided CouponReqDTO.
 *
 * @param idCoupon  The optional idCoupon of the coupon to retrieve. Defaults to -1 if not specified.
 * @param batchName The optional batch name of the coupons to retrieve. Defaults to an empty string if not specified.
 * @return ResponseEntity containing a document with a list of coupons matching the criteria or an error message.
 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/get-coupon")
	public ResponseEntity<Document<List<Coupon>>> getCouponByIdOrBatch(@RequestParam(defaultValue = "-1", required = false) Long idCoupon,
			@RequestParam(defaultValue = "", required = false) String batchName) {
		Document<List<Coupon>> reponses = specialOfferService.getCouponByIdOrBatch(idCoupon,batchName);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
    
	/**
	 * @author Mohan Kumar K M 
	 * Disables a coupon based on the provided CouponReqDTO.
	 *
	 * @param couponReqDTO The data transfer object containing information to disable the coupon.
	 * @return ResponseEntity containing a document with a status message or an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/coupon-disable")
	public ResponseEntity<Document<String>> disableCoupon(@RequestBody CouponReqDTO couponReqDTO) {
		Document<String> reponses = specialOfferService.disableCoupon(couponReqDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
    
	/**
	 * @author Mohan Kumar K M 
	 * Disables a visibility of coupon based on the provided CouponReqDTO.
	 *
	 * @param couponReqDTO The data transfer object containing information to disable the visible coupon.
	 * @return ResponseEntity containing a document with a status message or an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/coupon-visible-disable")
	public ResponseEntity<Document<String>> disableVisibleCoupon(@RequestBody CouponReqDTO couponReqDTO) {
		Document<String> reponses = specialOfferService.disableVisibleCoupon(couponReqDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Retrieves coupons associated with a specific email or phone number.
	 *
	 * @param emailOrPhone The email or phone number for which to retrieve associated coupons.
	 * @return ResponseEntity containing a document with a list of UserCouponDTO or an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/coupon-emailOrPhone")
	public ResponseEntity<Document<List<UserCouponDTO>>> getCouponByEmailOrPhone(@RequestParam String emailOrPhone) {
		Document<List<UserCouponDTO>> reponses = specialOfferService.getCouponByEmailOrPhone(emailOrPhone);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Updates a coupon based on its idCoupon.
	 *
	 * @param coupon    The updated coupon details.
	 * @param idCoupon  The ID of the coupon to update.
	 * @return ResponseEntity containing a document with a status message or an error message.
	 * @deprecated
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/update-coupon/{idCoupon}")
	public ResponseEntity<String> updateCoupon(@RequestBody Coupon coupon,@PathVariable Long idCoupon) {
		//Document<String> reponses = specialOfferService.updateCoupon(coupon,idCoupon);
		//return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
		return ResponseEntity.status(500).body("Currently, the coupon edit is not supported.");

	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Updates a batch of coupons based on the specified batch name.
	 *
	 * @param coupon    The updated coupon details.
	 * @param batchName The batch name of the coupons to update.
	 * @return ResponseEntity containing a document with a status message or an error message.
	 * @throws Exception 
	 * @deprecated
	 */
	@PutMapping(value = "/update-coupon-batch/{batchName}")
	public ResponseEntity<?> updateCouponBatch(@RequestBody Coupon coupon,@PathVariable String batchName) throws Exception {
		throw new AppException("Currently, the coupon edit feature is not supported.");
	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Deletes a coupon based on the provided ID or batch name.
	 *
	 * @param idCoupon  The optional ID of the coupon to delete. Defaults to -1 if not specified.
	 * @param batchName The optional batch name of the coupons to delete. Defaults to an empty string if not specified.
	 * @return ResponseEntity containing a document with a status message or an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/delete-coupon")
	public ResponseEntity<Document<String>> deleteCoupon(@RequestParam(defaultValue = "-1", required = false) Long idCoupon,
			@RequestParam(defaultValue = "", required = false) String batchName) {
		Document<String> reponses = specialOfferService.deleteCoupon(idCoupon,batchName);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
   
	/**
	 * @author Mohan Kumar K M 
	 * Retrieves a list of coupons associated with a specific batch.
	 *
	 * @return ResponseEntity containing a document with a list of coupons or an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/get-batch-coupons")
	public ResponseEntity<Document<List<Coupon>>> getBatchCoupons() {
		Document<List<Coupon>> reponses = specialOfferService.getBatchCoupons();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	/**
	 * @author Mohan Kumar K M 
	 * Retrieves the description of a coupon based on its coupon code.
	 *
	 * @param couponCode The coupon code for which to retrieve the description.
	 * @return ResponseEntity containing a document with the coupon description or an error message.
	 */
	
	@GetMapping(value = "/get-coupon-description")
	public ResponseEntity<Document<CouponDTO>> getCouponDescByCouponCode(@RequestParam String couponCode) {
		Document<CouponDTO> reponses = specialOfferService.getCouponDescByCouponCode(couponCode);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

}
