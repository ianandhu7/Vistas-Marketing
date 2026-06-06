/**
 * 
 */
package co.vistafoundation.vlearning.marketer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO;
import co.vistafoundation.vlearning.marketer.model.Vendor;
/**
 * @author NAVEEN
 *
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {
	
	
	@Query(value = "select new co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO(v.idVendor, v.vendorName, v.email,"
			+ "    v.idVlUser, v.onBoardingIdMarketer, obm.name, v.relationshipIdMarketer,"
			+ "	 rm.name, v.remarks, v.onBoardedDate, v.runnerPaidStatus, u.mobileNumber,"
			+ "	 r.referralCode,r.idReferralCode,v.paymentAmount) "
			+ "    from Vendor v " + " inner join Marketer obm on v.onBoardingIdMarketer=obm.idMarketer"
			+ "    inner join Marketer rm on v.relationshipIdMarketer=rm.idMarketer"
			+ "    inner join User u on v.idVlUser=u.userSurId"
			+ "    inner join ReferralCode r on v.idVendor=r.idVendor"
			+ "    where v.onBoardingIdMarketer=:onBoardingIdMarketer")
	public List<VendorResponseDTO> findVendorByIdOnBoardedMarketer(Long onBoardingIdMarketer);
	
	
	@Query(value = "select new co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO(v.idVendor, v.vendorName, v.email,"
			+ "    v.idVlUser, v.onBoardingIdMarketer, obm.name, v.relationshipIdMarketer,"
			+ "	 rm.name, v.remarks, v.onBoardedDate, v.runnerPaidStatus, u.mobileNumber,"
			+ "	 r.referralCode,r.idReferralCode,v.paymentAmount) "
			+ "    from Vendor v " + " inner join Marketer obm on v.onBoardingIdMarketer=obm.idMarketer"
			+ "    inner join Marketer rm on v.relationshipIdMarketer=rm.idMarketer"
			+ "    inner join User u on v.idVlUser=u.userSurId"
			+ "    inner join ReferralCode r on v.idVendor=r.idVendor"
			+ "    where v.relationshipIdMarketer=:idRelatioMarketer")
	public List<VendorResponseDTO> findVendorByIdRelationshipMarketer(Long idRelatioMarketer);
	
	
	@Query(value = "select new co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO(v.idVendor, v.vendorName, v.email,"
			+ "    v.idVlUser, v.onBoardingIdMarketer, obm.name, v.relationshipIdMarketer,"
			+ "	 rm.name, v.remarks, v.onBoardedDate, v.runnerPaidStatus, u.mobileNumber,"
			+ "	 r.referralCode,r.idReferralCode,v.paymentAmount) "
			+ "    from Vendor v " + "    inner join Marketer obm on v.onBoardingIdMarketer=obm.idMarketer"
			+ "    inner join Marketer rm on v.relationshipIdMarketer=rm.idMarketer"
			+ "    inner join User u on v.idVlUser=u.userSurId"
			+ "    inner join ReferralCode r on v.idVendor=r.idVendor")
	public List<VendorResponseDTO> getAllVendorsList();


	public Vendor findByIdVendor(Long idVendor);


	public Vendor findByIdVlUser(Long userId);


}
