package co.vistafoundation.vlearning.marketer.dto;

import java.time.Instant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VendorDto {
	
	@NotBlank
	private String firstName;
	
	@Email
	private String email;
	
	@NotBlank
	@Size(min=10,max=12)
	private String mobileNumber;
	
	@NotBlank
	@Size(min=4,max=10)
	private String ref_code;

	private Instant onBoarding_date;
	
	private String remarks;
	
	@NotBlank
	private Long idMarketer;
	
	

	public Long getIdMarketer() {
		return idMarketer;
	}

	public void setIdMarketer(Long idMarketer) {
		this.idMarketer = idMarketer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRef_code() {
		return ref_code;
	}

	public void setRef_code(String ref_code) {
		this.ref_code = ref_code;
	}

	public Instant getOnBoarding_date() {
		return onBoarding_date;
	}

	public void setOnBoarding_date(Instant onBoarding_date) {
		this.onBoarding_date = onBoarding_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
