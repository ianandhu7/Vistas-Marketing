/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.service;

import java.util.List;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.specialoffer.dto.CouponCodeDTO;
import co.vistafoundation.vlearning.specialoffer.dto.CouponDTO;
import co.vistafoundation.vlearning.specialoffer.dto.CouponReqDTO;
import co.vistafoundation.vlearning.specialoffer.dto.UserCouponDTO;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;

/**
 * @author Naveen Kumar
 *
 */
public interface SpecialOfferService {

	public Document<List<SpecialOffer>> getAllSpecialOffers();
	
	public Document<List<CouponCodeDTO>> getAllCouponCodes(Long idStudent, Long userSurId);
	
	public Document<Coupon> createCoupon(Coupon coupon,String emailOrPhone);
	
	public Document<String> createCoupons(Coupon coupon,int size);
	
	public Document<List<Coupon>> getAllCoupons();

	public Document<List<Coupon>> getCouponByIdOrBatch(Long idCoupon, String batchName);

	public Document<String> disableCoupon(CouponReqDTO couponReqDTO);

	public Document<String> disableVisibleCoupon(CouponReqDTO couponReqDTO);

	public Document<List<UserCouponDTO>> getCouponByEmailOrPhone(String emailOrPhone);

	public Document<String> updateCoupon(Coupon coupon,Long idCoupon);
	
	public Document<String> updateCouponBatch(Coupon coupon,String batchName);

	public Document<String> deleteCoupon(Long idCoupon, String batchName);

	public Document<List<Coupon>> getBatchCoupons();

	/**
	 * @author Abdul Elahi
	 * @param couponCode
	 * @return Coupon
	 */
	public Document<CouponDTO> getCouponDescByCouponCode(String couponCode);

	

	
}
