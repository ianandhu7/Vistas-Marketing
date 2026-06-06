package co.vistafoundation.vlearning.marketer.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.marketer.dto.EditVendorRequestDTO;
import co.vistafoundation.vlearning.marketer.dto.VendorDto;
import co.vistafoundation.vlearning.marketer.dto.VendorResponseDTO;
import co.vistafoundation.vlearning.marketer.dto.VendorStudentResponseDTO;
import co.vistafoundation.vlearning.marketer.model.Marketer;
import co.vistafoundation.vlearning.marketer.model.ReferralCode;
import co.vistafoundation.vlearning.marketer.model.Vendor;
import co.vistafoundation.vlearning.marketer.repository.MarketerRepository;
import co.vistafoundation.vlearning.marketer.repository.ReferralCodeRepository;
import co.vistafoundation.vlearning.marketer.repository.VendorRepository;
import co.vistafoundation.vlearning.marketer.service.VendorService;
import co.vistafoundation.vlearning.user.repository.StudentRepository;

@Service
public class VendorServiceImpl implements VendorService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	ReferralCodeRepository referralCodeRepo;

	@Autowired
	MarketerRepository marketerRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	ReferralCodeRepository referralCodeRepository;

	@Override
	public Document<List<VendorResponseDTO>> getListOfVendors() {

		Document<List<VendorResponseDTO>> result = new Document<>();

		try {

			List<VendorResponseDTO> vendorDTOList = vendorRepository.getAllVendorsList();

			List<VendorResponseDTO> finalList = new ArrayList<>();

			if (vendorDTOList.isEmpty())

				throw new AppException("No vendors Available for this marketer.");

			for (VendorResponseDTO vrd : vendorDTOList) {
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

	@Override
	public Document<List<VendorStudentResponseDTO>> getListOfStudentByVendor(Long idVendor) {

		Document<List<VendorStudentResponseDTO>> result = new Document<>();

		try {

			Vendor vendor = vendorRepository.findByIdVendor(idVendor);

			if (vendor == null)
				throw new AppException("Invalid Vendor id");

			ReferralCode referralCode = referralCodeRepository.findByIdVendor(idVendor);

			if (referralCode == null)
				throw new AppException("Vendor Dosent have refferal code info , Please contact admin");

			List<VendorStudentResponseDTO> studentDTOList = studentRepository
					.getAllStudentListByReferralCode(referralCode.getIdReferralCode());

			result.setData(studentDTOList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("student list fetched sucessfully.");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;

	}

	@Override
	public Document<Vendor> editVendorData(EditVendorRequestDTO request) {
		Document<Vendor> result = new Document<>();

		try {

			Vendor vendor = vendorRepository.findByIdVendor(request.getIdVendor());

			if (vendor == null)
				throw new AppException("Invalid Vendor id");

			User user = userRepository.findByUserSurId(vendor.getIdVlUser());

			if (user == null)
				throw new AppException("Invalid User found in vendor data.");

			if (!vendor.getEmail().equalsIgnoreCase(request.getEmail())) {

				Boolean activeRecord = userRepository.existsByUsernameOrEmail(request.getEmail(), request.getEmail());

				if (activeRecord)
					throw new AppException("Email already exist.");

			}

			user.setFirstName(request.getVendorName());
			user.setEmail(request.getEmail());
			user.setMobileNumber(request.getMobileNumber());
			user.setUsername(request.getMobileNumber());

			userRepository.save(user);

			vendor.setEmail(request.getEmail());
			vendor.setOnBoardedDate(request.getOnBoardedDate());
			vendor.setVendorName(request.getVendorName());
			vendor.setRemarks(request.getRemarks());

			if (request.getRelationshipIdMarketer() != null) {

				Marketer marketer = marketerRepository.findByIdMarketer(request.getRelationshipIdMarketer());

				if (marketer == null)
					throw new AppException("Invalid Marketer id");

				vendor.setRelationshipIdMarketer(marketer.getIdMarketer());

			}

			Vendor updatedVendor = vendorRepository.save(vendor);

			result.setData(updatedVendor);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Vendor updated sucessfully!");

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate username or mobile number or email id already in use.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}
		}
		return result;

	}

	@Override
	public Document<Object> saveVendor(VendorDto vendorDto) {
		// TODO Auto-generated method stub
		Document<Object> document = new Document<Object>();
		Marketer marketer;
		String pwd1,pwd2;
		User result = new User();
		try {
			if (vendorDto != null) {
				String name=vendorDto.getFirstName();
				if(name.length()>3) {
					pwd1=name.substring(0, 3);
				}else pwd1=name;
				String mobNo=vendorDto.getMobileNumber();
				pwd2=mobNo.substring(mobNo.length()-4,(mobNo.length()));
				User user = new User(vendorDto.getFirstName(), vendorDto.getFirstName(), vendorDto.getEmail(),
						vendorDto.getEmail(), pwd1+"@"+pwd2, null, vendorDto.getMobileNumber(), "Vendor", "");
				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_VENDOR);
				if (userRole == null)
					throw new AppException("User Role not set.");
				if (userRepository.existsByUsername(vendorDto.getEmail()))
					throw new AppException("Email id being used already.");

				if (userRepository.existsByMobileNumber(vendorDto.getMobileNumber()))
					throw new AppException("Mobile number being used already.");
				user.setRoles(Collections.singleton(userRole));
				user.setPassword(passwordEncoder.encode(user.getPassword()));

				result = userRepository.save(user);

				UserPrincipal userPrincipal = null;

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					userPrincipal = (UserPrincipal) authentication.getPrincipal();

				}

				if (userPrincipal == null) {
					document.setData(null);
					document.setStatusCode(500);
					document.setMessage("Invalid User");
					return document;
				}
				Vendor vendor = new Vendor();
				if (vendorDto.getIdMarketer() == null) {
					marketer = marketerRepository.findByIdVlUser(userPrincipal.getUserSurId());
					if (marketer != null) {
						vendor.setOnBoardingIdMarketer(marketer.getIdMarketer());
						vendor.setRelationshipIdMarketer(marketer.getIdMarketer());
					} else {
						document.setData(null);
						document.setStatusCode(500);
						document.setMessage("Marketer not found");
						return document;
					}

				} else {

					vendor.setVendorName(vendorDto.getFirstName());
					vendor.setEmail(vendorDto.getEmail());
					vendor.setOnBoardedDate(LocalDate.now());
					vendor.setIdVlUser(result.getUserSurId());
					vendor.setRemarks(vendorDto.getRemarks());
					vendor.setOnBoardingIdMarketer(vendorDto.getIdMarketer());
					vendor.setRelationshipIdMarketer(vendorDto.getIdMarketer());
					vendor.setRunnerPaidStatus("NOT_PAID");
					vendor.setPaymentAmount(0.0f);

					Vendor savedVendor = vendorRepository.save(vendor);
					ReferralCode refferalCode = new ReferralCode();
					refferalCode.setStartDate(LocalDate.now());
					refferalCode.setIdVendor(savedVendor.getIdVendor());
					refferalCode.setOfferType("VENDOR");
					refferalCode.setReferralCode(vendorDto.getRef_code());
					referralCodeRepo.save(refferalCode);

					document.setData(savedVendor);
					document.setStatusCode(200);
					document.setMessage("Vendor created successfully");

				}

			} else {
				document.setData(null);
				document.setStatusCode(500);
				document.setMessage("Internal server error");
				return document;
			}
		} catch (Exception e) {
			document.setData(null);
			document.setStatusCode(500);
			document.setMessage(e.getLocalizedMessage());
			return document;
		}

		return document;

	}

}
