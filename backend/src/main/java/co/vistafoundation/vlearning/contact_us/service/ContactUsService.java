package co.vistafoundation.vlearning.contact_us.service;

import org.springframework.data.domain.Page;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.contact_us.dto.ContactUsSearchDTO;
import co.vistafoundation.vlearning.contact_us.modal.ContactUs;

/**
 * @author NAVEEN
 *
 */
public interface ContactUsService {

	public Document<?> saveContactUs(ContactUs contactUs);
	
	public Document<Page<ContactUs>> fetchAllUserContact(ContactUsSearchDTO contactUsSearchDTO);

}
