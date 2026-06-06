/**
 * 
 */
package co.vistafoundation.vlearning.marketer.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author NAVEEN
 *
 */
public class EditVendorRequestDTO {
	
	@NotNull
	private Long idVendor;
	
	@NotNull
	private String vendorName;
	
	@NotNull
	private String email;
	
	@NotNull
	private String mobileNumber;

	private String remarks;
	
	@NotNull
	private LocalDate onBoardedDate;
	
	private Long relationshipIdMarketer;

	/**
	 * @return the idVendor
	 */
	public Long getIdVendor() {
		return idVendor;
	}

	/**
	 * @param idVendor the idVendor to set
	 */
	public void setIdVendor(Long idVendor) {
		this.idVendor = idVendor;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the onBoardedDate
	 */
	public LocalDate getOnBoardedDate() {
		return onBoardedDate;
	}

	/**
	 * @param onBoardedDate the onBoardedDate to set
	 */
	public void setOnBoardedDate(LocalDate onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}
	
	

	/**
	 * @return the relationshipIdMarketer
	 */
	public Long getRelationshipIdMarketer() {
		return relationshipIdMarketer;
	}

	/**
	 * @param relationshipIdMarketer the relationshipIdMarketer to set
	 */
	public void setRelationshipIdMarketer(Long relationshipIdMarketer) {
		this.relationshipIdMarketer = relationshipIdMarketer;
	}

	/**
	 * @param idVendor
	 * @param vendorName
	 * @param email
	 * @param mobileNumber
	 * @param remarks
	 * @param onBoardedDate
	 */
	public EditVendorRequestDTO(@NotBlank Long idVendor, @NotBlank String vendorName, @NotBlank String email,
			@NotBlank String mobileNumber, String remarks, @NotBlank LocalDate onBoardedDate) {
		super();
		this.idVendor = idVendor;
		this.vendorName = vendorName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.remarks = remarks;
		this.onBoardedDate = onBoardedDate;
	}

	/**
	 * 
	 */
	public EditVendorRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
