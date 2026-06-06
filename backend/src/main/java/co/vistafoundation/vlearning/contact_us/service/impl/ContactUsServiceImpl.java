/**
 * 
 */
package co.vistafoundation.vlearning.contact_us.service.impl;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.contact_us.dto.ContactUsSearchDTO;
import co.vistafoundation.vlearning.contact_us.modal.ContactUs;
import co.vistafoundation.vlearning.contact_us.repository.ContactUsRepository;
import co.vistafoundation.vlearning.contact_us.service.ContactUsService;
import co.vistafoundation.vlearning.exception.AppException;

/**
 * @author NAVEEN
 *
 */
@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	ContactUsRepository contactUsRepository;

	@Override
	public Document<ContactUs> saveContactUs(ContactUs contactUs) {

		Document<ContactUs> result = new Document<ContactUs>();

		String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		String mobileRegex = "(0/91)?[7-9][0-9]{9}";
		Pattern mobilePattern = Pattern.compile(mobileRegex);

		try {
             
			if (contactUs.getName() != null) 
			{
				if (contactUs.getName().trim().length() <= 0)
					throw new AppException("Name Cannot be empty!");
			}
			else 
			{
				throw new AppException("Name Cannot be empty!");
			}
			
			if (contactUs.getEmail() != null) 
			{
				if (contactUs.getName().length() > 0)
				{
					Matcher emailmatcher = emailPattern.matcher(contactUs.getEmail());
					if (!emailmatcher.matches())
						throw new AppException("Invalid email provided in the contact data.");
				}
			}
			
			else {
				contactUs.setEmail(null);
			}
			
			if (contactUs.getPhoneNumber() != null) 
			{
				if (contactUs.getPhoneNumber().trim().length() <= 0)
					throw new AppException("Mobile Number Cannot be empty!");
				
				Matcher mobileMatcher = mobilePattern.matcher(contactUs.getPhoneNumber());

				System.out.println(mobileMatcher.matches());
				
				if (!mobileMatcher.matches())
					throw new AppException("Invalid mobile number provided in the contact data.");
			}
			else 
			{
				throw new AppException("Mobile Number Cannot be empty!");
			}
			
			if (contactUs.getMessage() != null) 
			{

				if (contactUs.getMessage().length() > 500)
					throw new AppException("Exceeds the maximum charcters allowed for message.");
			}else 
			{
				contactUs.setMessage(null);
			}
			
			contactUs.setRequestedDate(Instant.now());

			result.setData(contactUsRepository.save(contactUs));
			result.setMessage("Your request raised successfully.");
			result.setStatusCode(201);

		} catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);
		}

		return result;
	}

	@Override
	public Document<Page<ContactUs>> fetchAllUserContact(ContactUsSearchDTO contactUsSearchDTO) {
		Document<Page<ContactUs>> result = new Document<>();
		try {
			Pageable paging = PageRequest.of(contactUsSearchDTO.getPage(), contactUsSearchDTO.getSize());
			Page<ContactUs> usersLists;

			if (contactUsSearchDTO.getDateFrom() != null && contactUsSearchDTO.getDateTo() != null) {

				usersLists = contactUsRepository.findAllByNameOrEmailOrMobileWithGivenDates(
						contactUsSearchDTO.getName(), contactUsSearchDTO.getEmail(),
						contactUsSearchDTO.getMobileNumber(), contactUsSearchDTO.getDateFrom(),
						contactUsSearchDTO.getDateTo(), paging);

			} else if (contactUsSearchDTO.getDateFrom() != null && contactUsSearchDTO.getDateTo() == null) {
				usersLists = contactUsRepository.findAllByNameOrEmailOrMobileWithFromDate(contactUsSearchDTO.getName(),
						contactUsSearchDTO.getEmail(), contactUsSearchDTO.getMobileNumber(),
						contactUsSearchDTO.getDateFrom(), paging);
			} else if (contactUsSearchDTO.getDateFrom() == null && contactUsSearchDTO.getDateTo() != null) {
				usersLists = contactUsRepository.findAllByNameOrEmailOrMobileToDate(contactUsSearchDTO.getName(),
						contactUsSearchDTO.getEmail(), contactUsSearchDTO.getMobileNumber(),
						contactUsSearchDTO.getDateTo(), paging);
			} else if (contactUsSearchDTO.getEmail() != null || contactUsSearchDTO.getMobileNumber() != null
					|| contactUsSearchDTO.getName() != null) {
				usersLists = contactUsRepository.findAllByNameOrEmailOrMobile(contactUsSearchDTO.getName(),
						contactUsSearchDTO.getEmail(), contactUsSearchDTO.getMobileNumber(), paging);
			}

			else {
				usersLists = contactUsRepository.findAllOrderByRequestedDateDesc(paging);
			}

			result.setData(usersLists);
			result.setMessage("List fetched successfully");
			result.setStatusCode(200);
		} catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

}
