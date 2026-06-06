/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.specialoffer.dto.CouponCodeDTO;
import co.vistafoundation.vlearning.specialoffer.dto.CouponDTO;
import co.vistafoundation.vlearning.specialoffer.dto.CouponReqDTO;
import co.vistafoundation.vlearning.specialoffer.dto.UserCouponDTO;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.model.CouponUserMap;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.specialoffer.repository.CouponUserMapRepository;
import co.vistafoundation.vlearning.specialoffer.repository.RedemptionRepository;
import co.vistafoundation.vlearning.specialoffer.repository.SpecialOfferRepository;
import co.vistafoundation.vlearning.specialoffer.service.SpecialOfferService;

/**
 * @author Naveen Kumar
 *
 */
@Service
public class SpecialOfferImpl implements SpecialOfferService {

	@Autowired
	SpecialOfferRepository specialOfferRepository;
	
	@Autowired
	CouponRepository couponRepository; 
	
	@Autowired
	CouponUserMapRepository couponUserMapRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RedemptionRepository redemptionRepository;

	@Override
	public Document<List<SpecialOffer>> getAllSpecialOffers() {

		Document<List<SpecialOffer>> result = new Document<>();
		try {
			List<SpecialOffer> offerList = specialOfferRepository.
					findBySpecialOfferEndDateGreaterThanEqualAndActiveFlag(LocalDate.now(),true);

			if (offerList.isEmpty())
				throw new AppException("No Special Offer Available.");

			result.setData(offerList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;
	}

	@Override
	public Document<List<CouponCodeDTO>> getAllCouponCodes(Long idStudent, Long userSurId) {
		
		Document<List<CouponCodeDTO>> result = new Document<>();
		
		List<CouponCodeDTO> listOfCoupons = new ArrayList<>();
		
		try {
			
			List<Object[]> objList = specialOfferRepository.getAllCouponCodes(idStudent, userSurId);
			
			if(objList.isEmpty()) throw new NullPointerException("No valid Coupon codes available");
			
			for(Object[] obj : objList) {
				
				CouponCodeDTO couponCodeDTO = new CouponCodeDTO();
				
				couponCodeDTO.setIdSpecialOffer((BigInteger)obj[0]);
				couponCodeDTO.setCouponCode((String)obj[1]);
				couponCodeDTO.setSpecialOfferDetails((String)obj[2]);
				
				listOfCoupons.add(couponCodeDTO);	
			}
			
			
			result.setData(listOfCoupons);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			
		}
		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		
		
		return result;
	}

	@Override
	public Document<Coupon> createCoupon(Coupon coupon,String emailOrPhone) {

		Document<Coupon> doc = new Document<>();

		try {
			
			Coupon coupon1=null;
			
			if(!emailOrPhone.isEmpty()&&!emailOrPhone.isBlank()) {
			
			 Boolean isValidEmail = emailOrPhone
			    		.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
			 
			 Boolean isValidPhoneNumber =emailOrPhone.matches("[9876]\\d{9}");
			 
			 if(!isValidEmail&&!isValidPhoneNumber) {
				 
				 throw new AppException("Please provide valid Email address or Phone number");
				 
			 } 
			     coupon.setTotalCount(1L);
			     coupon1 = couponRepository.save(coupon);

				User user = userRepository.findByEmailOrMobileNumber(emailOrPhone);
				if (user==null) throw new AppException("No user found for this email  or Phone Number");
				
				if (!user.getRegisteredAs().equals("Student"))
				throw new AppException("Coupon creation is restricted to certain roles.");
				
				CouponUserMap couponUserMap=new CouponUserMap();
				couponUserMap.setIdCoupon(coupon1.getIdCoupon());
				couponUserMap.setIdVlUser(user.getUserSurId());
				couponUserMapRepository.save(couponUserMap);
			}else {
				   coupon1 = couponRepository.save(coupon);
			}
			
			doc.setData(coupon1);
			doc.setMessage("Coupon Created Successfully");
			doc.setStatusCode(HttpStatus.CREATED.value());
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	public static String generateCouponCode(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder couponCode = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			couponCode.append(characters.charAt(index));
		}

		return couponCode.toString();
	}

	@Override
	public Document<String> createCoupons(Coupon coupon, int size) {
		Document<String> doc = new Document<>();

		try {
			for (int i = 0; i < size; i++) {
				Coupon coupon1=new Coupon();
				coupon1.setCouponCode(generateCouponCode(8));
				coupon1.setCouponType(coupon.getCouponType());
				coupon1.setDescription(coupon.getDescription());
				coupon1.setDiscount(coupon.getDiscount());
				coupon1.setExtensionDuration(coupon.getExtensionDuration());
				coupon1.setIsActive(coupon.getIsActive());
				coupon1.setIsVisible(coupon.getIsVisible());
				coupon1.setStartDate(coupon.getStartDate());
				coupon1.setEndDate(coupon.getEndDate());
				coupon1.setTotalCount(1L);
				coupon1.setBatchName(coupon.getBatchName());
				couponRepository.save(coupon1);
			}
		
			doc.setData("Coupons Created Successfully");
			doc.setMessage("Coupons Created Successfully");
			doc.setStatusCode(HttpStatus.CREATED.value());
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}

	}

	@Override
	public Document<List<Coupon>> getAllCoupons() {

		Document<List<Coupon>> result = new Document<>();

		try {

			List<Coupon> coupons = couponRepository.findAll();

			if (coupons.isEmpty())
				throw new AppException("No  Coupons  available");

			result.setData(coupons);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;

	}

	@Override
	public Document<List<Coupon>> getCouponByIdOrBatch(Long idCoupon, String batchName) {
		Document<List<Coupon>> result = new Document<>();

		try {
			List<Coupon> coupons = new ArrayList<>();
			if (idCoupon == -1L && batchName == "") {
				throw new AppException("Please Provide idCoupon or Batch Name");
			}

			if (batchName != null && batchName.trim().length() > 0) {

				coupons = couponRepository.findByBatchName(batchName);

			}
			if (!idCoupon.equals(-1L)) {
				Coupon coupon = couponRepository.findByIdCoupon(idCoupon);
				if (coupon != null)
					coupons.add(coupon);
			}

			if (coupons.isEmpty()) {
				throw new AppException("Invalid Coupon Id or Coupon Batch Name");
			}

			result.setData(coupons);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;

	}

	@Override
	public Document<String> disableCoupon(CouponReqDTO couponReqDTO) {

		Document<String> result = new Document<>();
        
		try {
			List<Coupon> coupons=new ArrayList<>();
			if (couponReqDTO.getIdCoupon().isEmpty()&&couponReqDTO.getBatchName().equals("")) {
				throw new AppException("Please Provide idCoupon or Batch Name");
			}
			
			if (couponReqDTO.getBatchName() != null&& couponReqDTO.getBatchName().trim().length()>0) {

				coupons= couponRepository.findByBatchName(couponReqDTO.getBatchName());
				
			} 
			if (!couponReqDTO.getIdCoupon().isEmpty()) {
				 coupons = couponRepository.findAllById(couponReqDTO.getIdCoupon());	
			}

			if (coupons.isEmpty()) {
				throw new AppException("Invalid Coupon Id or Coupon Batch Name");
			}
			coupons.stream().forEach(c->c.setIsActive(couponReqDTO.getIsActive()));
			couponRepository.saveAll(coupons);
			
			result.setData("Coupon Disabled Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Coupon Disabled Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
	}

	@Override
	public Document<String> disableVisibleCoupon(CouponReqDTO couponReqDTO) {
		Document<String> result = new Document<>();
        
		try {
			List<Coupon> coupons=new ArrayList<>();
			if (couponReqDTO.getIdCoupon().isEmpty()&&couponReqDTO.getBatchName().equals("")) {
				throw new AppException("Please Provide idCoupon or Batch Name");
			}
			
			if (couponReqDTO.getBatchName() != null&& couponReqDTO.getBatchName().trim().length()>0) {

				coupons= couponRepository.findByBatchName(couponReqDTO.getBatchName());
				
			} 
			if (!couponReqDTO.getIdCoupon().equals(-1L)) 
				 coupons = couponRepository.findAllById(couponReqDTO.getIdCoupon());
			
			if (coupons.isEmpty()) {
				throw new AppException("Invalid Coupon Id or Coupon Batch Name");
			}
			coupons.stream().forEach(c->c.setIsVisible(couponReqDTO.getIsVisible()));
			couponRepository.saveAll(coupons);
			
			result.setData("Coupon Visible Disabled Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Coupon Visible Disabled Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
		
	}

	@Override
	public Document<List<UserCouponDTO>> getCouponByEmailOrPhone(String emailOrPhone) {
		
        Document<List<UserCouponDTO>> result = new Document<>();
	
		try {
			
			 Boolean isValidEmail = emailOrPhone
			    		.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
			 
			 Boolean isValidPhoneNumber =emailOrPhone.matches("[9876]\\d{9}");
			 
			 if(!isValidEmail&&!isValidPhoneNumber) {
				 
				 throw new AppException("Please provide valid Email address or Phone number");
				 
			 } 
			
			
			List<UserCouponDTO> coupons = new ArrayList<>();
			User user = userRepository.findByEmailOrMobileNumber(emailOrPhone);
			if (user==null) 
				throw new AppException("No user found for this email or Phone Number");
			
			List<CouponUserMap> couponUserMapList=couponUserMapRepository.findByIdVlUser(user.getUserSurId());
			if (couponUserMapList.isEmpty()) 
				throw new AppException("No user coupon map found");
			
	
           for(CouponUserMap couponUserMap:couponUserMapList)
           {
        	   Coupon coupon= couponRepository.findByIdCoupon(couponUserMap.getIdCoupon());
        	   UserCouponDTO userCouponDTO =new UserCouponDTO();
        	   BeanUtils.copyProperties(coupon, userCouponDTO);
        	   userCouponDTO.setEmailOrPhone(emailOrPhone);
			   coupons.add(userCouponDTO);
           }
			
			result.setData(coupons);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			
		}
		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
	}

	@Override
	public Document<String> updateCoupon(Coupon coupon,Long idCoupon) {

		Document<String> result = new Document<>();

		try {

			
				Coupon coupon1 = couponRepository.findByIdCoupon(idCoupon);

				if (coupon1 == null)
					throw new AppException("No  coupon found with this idCoupon:" + idCoupon);

				coupon1.setCouponCode(coupon.getCouponCode());
				coupon1.setCouponType(coupon.getCouponType());
				coupon1.setDescription(coupon.getDescription());
				coupon1.setDiscount(coupon.getDiscount());
				coupon1.setExtensionDuration(coupon.getExtensionDuration());
				coupon1.setStartDate(coupon.getStartDate());
				coupon1.setEndDate(coupon.getEndDate());
				coupon1.setTotalCount(coupon.getTotalCount());
				couponRepository.save(coupon1);
			

			result.setData("Coupon updated Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Coupon updated Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}

		return result;
	}
	
	@Override
	public Document<String> updateCouponBatch(Coupon coupon, String batchName) {
		Document<String> result = new Document<>();

		try {

			List<Coupon> couponList = couponRepository.findByBatchName(batchName);

			if (couponList.isEmpty())
				throw new AppException("No  coupons found with this batch name:" + batchName);

			couponList.forEach(idCoupon -> {
				idCoupon.setCouponType(coupon.getCouponType());
				idCoupon.setDescription(coupon.getDescription());
				idCoupon.setDiscount(coupon.getDiscount());
				idCoupon.setExtensionDuration(coupon.getExtensionDuration());
				idCoupon.setStartDate(coupon.getStartDate());
				idCoupon.setEndDate(coupon.getEndDate());
			});
			couponRepository.saveAll(couponList);

			result.setData("Coupon updated Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Coupon updated Sucessfully");
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}

		return result;

	}

	@Override
	public Document<String> deleteCoupon(Long idCoupon, String batchName) {

		Document<String> result = new Document<>();

		try {
			List<Coupon> coupons = new ArrayList<>();
			if (idCoupon == -1L && batchName == "") {
				throw new AppException("Please Provide idCoupon or Batch Name");
			}

			if (batchName != null && batchName.trim().length() > 0) {

				coupons = couponRepository.findByBatchName(batchName);

			}
			if (!idCoupon.equals(-1L)) {
				Coupon coupon = couponRepository.findByIdCoupon(idCoupon);
				if (coupon != null)
					coupons.add(coupon);
			}

			if (coupons.isEmpty()) {
				throw new AppException("Invalid Coupon Id or Coupon Batch Name");
			}

			for (Coupon coupon1 : coupons) {

				Boolean redemptionExists = redemptionRepository.existsByCouponCode(coupon1.getCouponCode());
				if (redemptionExists) {
					throw new AppException("Unable delete this coupon because there is a redemption record associated with it.");
				}

			}
			couponRepository.deleteAll(coupons); 
			result.setData("Coupon Deleted Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Coupon Deleted Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
	}

	@Override
	public Document<List<Coupon>> getBatchCoupons() {
		
		Document<List<Coupon>> result = new Document<>();

		try {

			List<Coupon> coupons = couponRepository.getBatchCoupons();
			
			List<Coupon> distinctElements = coupons.stream().filter(distinctByKey(coup -> coup.getBatchName()))
					.collect(Collectors.toList());

			if (coupons.isEmpty())
				throw new AppException("No  Coupons  available");

			result.setData(distinctElements);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
		
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
		return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}	
	
	@Override
	public Document<CouponDTO> getCouponDescByCouponCode(String couponCode) {
		
		Document<CouponDTO> result = new Document<>();
		try {
			Coupon coupon = couponRepository.findByCouponCode(couponCode.trim());
			
			if (coupon == null) 
				throw new AppException("Coupon does not exist!");
			
			if(coupon.getUsedCount() >= coupon.getTotalCount())
				throw new AppException("No Coupons Left");
			
			if(coupon.getIsActive() == false)
				throw new AppException("Coupon is not Activated");
			
			if(coupon.getEndDate().isBefore(LocalDateTime.now()))
				throw new AppException("Coupon got Expired");

		    CouponDTO  couponDto=new CouponDTO(coupon.getDescription(),coupon.getCouponType());
			result.setData(couponDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;

	}

}
