package co.vistafoundation.vlearning.marketer.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.marketer.dto.EditVendorRequestDTO;
import co.vistafoundation.vlearning.marketer.dto.VendorDto;
import co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO;
import co.vistafoundation.vlearning.marketer.dto.VendorStudentResponseDTO;
import co.vistafoundation.vlearning.marketer.model.Vendor;

public interface VendorService {

	public Document<Object> saveVendor(VendorDto vendor);

	public Document<List<VendorResponseDTO>> getListOfVendors();

	public Document<List<VendorStudentResponseDTO>> getListOfStudentByVendor(Long idVendor);

	public Document<Vendor> editVendorData(EditVendorRequestDTO request);

}
