
package co.vistafoundation.vlearning.marketer.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO;
import co.vistafoundation.vlearning.marketer.model.Marketer;
import co.vistafoundation.vlearning.marketer.model.Vendor;
import co.vistafoundation.vlearning.marketer.model.VendorStudentPayment;
import co.vistafoundation.vlearning.marketer.repository.MarketerRepository;
import co.vistafoundation.vlearning.marketer.repository.VendorRepository;
import co.vistafoundation.vlearning.marketer.repository.VendorStudentPaymentRepository;
import co.vistafoundation.vlearning.marketer.service.MarketerService;
import co.vistafoundation.vlearning.user.repository.StudentRepository;

/**
 * @author NAVEEN
 *
 */

@Service
public class MarketerServiceImpl implements MarketerService {

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	MarketerRepository marketerRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	VendorStudentPaymentRepository vendorStudentPaymentRepository;

	@Override
	public Document<List<VendorResponseDTO>> getVendorsListByMarketer(Long onBoardingIdMarketer) {

		Document<List<VendorResponseDTO>> result = new Document<>();

		try {

			Marketer marketer = marketerRepository.findByIdMarketer(onBoardingIdMarketer);

			if (marketer == null)

				throw new AppException("Invalid Marketer Id.");



			List<VendorResponseDTO> relVendorDTOList = vendorRepository
					.findVendorByIdRelationshipMarketer(onBoardingIdMarketer);


			if (relVendorDTOList.isEmpty())
				throw new AppException("No vendors Available for this marketer.");

			List<VendorResponseDTO> finalList = new ArrayList<>();


			for (VendorResponseDTO vrd : relVendorDTOList) {
				vrd.setTotalStudents(studentRepository.countByIdReferralCode(vrd.getIdReferralCode()));
				finalList.add(vrd);
			}

			result.setData(finalList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Vendor list fetched sucessfully.");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;

	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	@Override
	public Document<List<Marketer>> getAllMarketers() {
		// TODO Auto-generated method stub
		Document<List<Marketer>> result = new Document<>();
		try {
			List<Marketer> marketerList = marketerRepository.findAll();
			if (marketerList.isEmpty()) {
				result.setData(null);
				result.setMessage("No Marketers available");
				result.setStatusCode(500);
			} else {
				result.setData(marketerList);
				result.setMessage("");
				result.setStatusCode(200);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage("Internal server error");
			result.setStatusCode(500);
		}

		return result;
	}

	@Override
	public Document<VendorStudentPayment> makeVendorStudentPayment(Long idVendor, Long idStudent, Float amount,
			String status) {
		// TODO Auto-generated method stub
		Document<VendorStudentPayment> result = new Document<>();
		try {
			VendorStudentPayment vendorStudentPayment = vendorStudentPaymentRepository
					.findByIdVendorAndIdStudent(idVendor, idStudent);

			if (vendorStudentPayment == null) {
				result.setData(null);
				result.setMessage("Invalid Vendor Student payment record.");
				result.setStatusCode(500);
			} else {

				if (amount <= 0 && status.equalsIgnoreCase("NOT_PAID")) {

					vendorStudentPayment.setVendorPaymentStatus("NOT_PAID");
					vendorStudentPayment.setPaymentAmount(0);
					vendorStudentPayment.setPaymentDate(null);

				} else {
					vendorStudentPayment.setVendorPaymentStatus("PAID");
					vendorStudentPayment.setPaymentAmount(amount);
					vendorStudentPayment.setPaymentDate(LocalDate.now());

				}

				VendorStudentPayment temp = vendorStudentPaymentRepository.save(vendorStudentPayment);

				result.setData(temp);
				result.setMessage("Vendor student payment updation suceess!");
				result.setStatusCode(200);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage("Internal server error");
			result.setStatusCode(500);
		}

		return result;
	}

	@Override
	public Document<Vendor> makeRunnerPayment(Long idVendor, Float amount, String status) {

		Document<Vendor> result = new Document<>();
		try {

			Vendor vendor = vendorRepository.findByIdVendor(idVendor);

			if (vendor == null) {
				result.setData(null);
				result.setMessage("Invalid Vendor Student payment record.");
				result.setStatusCode(500);
			} else {

				if (amount <= 0 && status.equalsIgnoreCase("NOT_PAID")) {

					vendor.setPaymentAmount(0.0F);
					vendor.setRunnerPaidStatus("NOT_PAID");

				} else {

					vendor.setPaymentAmount(amount);
					vendor.setRunnerPaidStatus("PAID");

				}

				Vendor temp = vendorRepository.save(vendor);

				result.setData(temp);
				result.setMessage("Vendor student payment updation suceess!");
				result.setStatusCode(200);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage("Internal server error");
			result.setStatusCode(500);
		}

		return result;

	}

}
