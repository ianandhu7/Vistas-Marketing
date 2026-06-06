/**
 * 
 */
package co.vistafoundation.vlearning.contact_us.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.contact_us.dto.ContactUsSearchDTO;
import co.vistafoundation.vlearning.contact_us.modal.ContactUs;
import co.vistafoundation.vlearning.contact_us.service.ContactUsService;


@RestController
@RequestMapping("/api/v1/contact-us")
public class ContactUsController {

	@Autowired
	ContactUsService contactUsService;

	/**
	 * @author NAVEEN KUMAR
	 * 
	 *         Saves the contact data provided by the user after validating the
	 *         data.
	 * 
	 * @param contactUs the {@link ContactUs} object containing the contact data
	 * @return a {@link Document} object containing the saved {@link ContactUs}
	 *         data, along with a status code and a message
	 * @throws AppException if any of the provided contact data is invalid or
	 *                      missing
	 * 
	 *                      Handles the HTTP POST request for uploading social
	 *                      videos to YouTube.
	 * 
	 * @param contactUs the {@link ContactUs} object containing the contact data
	 *                  provided by the user
	 * @return a {@link ResponseEntity} object containing the response data,
	 *         including a {@link Document} object containing the saved
	 *         {@link ContactUs} data, along with a status code and a message
	 */

	@PostMapping(value = "")
	public ResponseEntity<?> uploadSocialVideoToYoutube(@RequestBody ContactUs contactUs) {
		Document<?> reponses = contactUsService.saveContactUs(contactUs);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar
	 * 
	 * Fetches a list of ContactUs objects based on the provided search criteria.
	 *
	 * @param contactUsSearchDTO The DTO containing the search criteria.
	 * @return A Document object containing the paginated list of ContactUs objects.
	 * 
	 *         Endpoint to fetch a list of ContactUs objects based on the provided
	 *         search criteria.
	 *
	 * @param contactUsDTO The DTO containing the search criteria.
	 * @return A ResponseEntity object containing a Document object with the
	 *         paginated list of ContactUs objects.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/all")
	public ResponseEntity<?> uploadSocialVideoToYoutube(@RequestBody ContactUsSearchDTO contactUsDTO) {
		Document<?> reponses = contactUsService.fetchAllUserContact(contactUsDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
}
