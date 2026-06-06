
package co.vistafoundation.vlearning.marketer.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO;
import co.vistafoundation.vlearning.marketer.model.Marketer;
import co.vistafoundation.vlearning.marketer.model.Vendor;
import co.vistafoundation.vlearning.marketer.model.VendorStudentPayment;

/**
 * @author NAVEEN
 *
 */
public interface MarketerService {

	public Document<List<VendorResponseDTO>> getVendorsListByMarketer(Long onBoardingIdMarketer);

	public Document<List<Marketer>> getAllMarketers();
	
	public Document<VendorStudentPayment> makeVendorStudentPayment(Long idVendor, Long idStudent,Float amount,String status );
	
	public Document<Vendor> makeRunnerPayment(Long idVendor,Float amount,String status);

}