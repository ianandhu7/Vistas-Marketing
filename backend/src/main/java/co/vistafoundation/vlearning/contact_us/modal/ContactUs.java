/**
 * 
 */
package co.vistafoundation.vlearning.contact_us.modal;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NAVEEN
 *
 */
@Entity
@Table(name = "CONTACT_US")
@JsonIgnoreProperties({"updatedBy", "createdBy","createdAt","updatedAt"})
public class ContactUs  extends UserDateAudit implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCONTACT_US", nullable = false)
	private Long idContactUs;
	
	@Column(name = "NAME", length = 50)
	private String name;
	
	@Column(name = "EMAIL", length = 50)
	private String email;
	
	@Column(name = "PHONE_NUMBER", length = 20)
	private String phoneNumber;
	
	@Column(name = "MESSAGE", length = 500)
	private String message;
	
	@Column(name = "REQUESTED_DATE")
	private Instant requestedDate;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the requestedDate
	 */
	public Instant getRequestedDate() {
		return requestedDate;
	}

	/**
	 * @param requestedDate the requestedDate to set
	 */
	public void setRequestedDate(Instant requestedDate) {
		this.requestedDate = requestedDate;
	}

	/**
	 * 
	 */
	public ContactUs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param email
	 * @param phoneNumber
	 * @param message
	 * @param requestedDate
	 */
	public ContactUs(String name, String email, String phoneNumber, String message, Instant requestedDate) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.message = message;
		this.requestedDate = requestedDate;
	}

	
	
}
