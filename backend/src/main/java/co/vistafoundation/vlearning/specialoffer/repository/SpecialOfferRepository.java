/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import co.vistafoundation.vlearning.specialoffer.dto.SpecialOfferDTO;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;
import co.vistafoundation.vlearning.user.dto.BatchPromoCodeDTO;

/**
 * @author Naveen Kumar
 *
 */
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {

	public SpecialOffer findByIdSpecialOffer(Long idSpecialOffer);

	public SpecialOffer findByCouponCodeAndActiveFlagAndSpecialOfferEndDateGreaterThanEqual(String promoCode,boolean activeFlag,LocalDate todayDate);
	
	@Query(value = "select new co.vistafoundation.vlearning.user.dto.BatchPromoCodeDTO(b.idBatch,pl.productLineCd,sop.idSpecialOffer,so.couponCode,p.idProduct,p.monthlySubcrAmt,p.annualSubscrAmt,p.qtrSubscrAmt)"+
			"    from Batch b " + 
			"    inner join Product p on b.idProduct=p.idProduct" + 
			"    inner join ProductLine pl on p.idProductLine=pl.idProductLine" + 
			"    left join SpecialOfferProduct sop on sop.idBatch=b.idBatch" + 
			"    left join SpecialOffer so on sop.idSpecialOffer=so.idSpecialOffer" +
			"    where sop.idBatch IN (:idBatchs)")
	public List<BatchPromoCodeDTO> findByPromocodeByIdBatchs(List<Long> idBatchs);
	
	public List<SpecialOffer> findBySpecialOfferEndDateGreaterThanEqualAndActiveFlag(LocalDate todayDate,boolean activeFlag);
	
	
	@Query(value = "select new co.vistafoundation.vlearning.specialoffer.dto.SpecialOfferDTO(so.couponCode,so.specialOfferDetails)"+
			"    from SpecialOffer so" + 
			"   inner join SpecialOfferProduct sop on sop.idSpecialOffer=so.idSpecialOffer" + 
			"    where sop.idBatch=:idBatch")
	public Set<SpecialOfferDTO> findSpecialOfferByBatchId(Long idBatch );
	
	
	@Query(value ="select distinct so.id_special_offer,so.coupon_code, so.special_offer_details from user_cart uc join special_offer_product sop on uc.idbatch = sop.idbatch join special_offer so on sop.id_special_offer = so.id_special_offer where uc.idstudent =:idStudent and uc.idvluser= :userSurId",nativeQuery = true)
	public List<Object[]> getAllCouponCodes(Long idStudent, Long userSurId);
	
}
