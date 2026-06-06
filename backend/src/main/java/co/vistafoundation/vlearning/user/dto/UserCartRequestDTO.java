/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vk
 *
 */
public class UserCartRequestDTO {
	
	private List<UserCartResponseDTO> usercartResponseDTO;
	
	private Boolean promoCodeApplied;
	
	private Boolean promoCodeValid;
	
	private String promoCode;
	
	private Long clientTimeZone;
	
	private LocalDateTime clientDateTime;

	/**
	 * @return the usercartResponseDTO
	 */
	public List<UserCartResponseDTO> getUsercartResponseDTO() {
		return usercartResponseDTO;
	}

	/**
	 * @return the promoCodeApplied
	 */
	public Boolean getPromoCodeApplied() {
		return promoCodeApplied;
	}

	/**
	 * @return the promoCodeValid
	 */
	public Boolean getPromoCodeValid() {
		return promoCodeValid;
	}

	/**
	 * @param usercartResponseDTO the usercartResponseDTO to set
	 */
	public void setUsercartResponseDTO(List<UserCartResponseDTO> usercartResponseDTO) {
		this.usercartResponseDTO = usercartResponseDTO;
	}

	/**
	 * @param promoCodeApplied the promoCodeApplied to set
	 */
	public void setPromoCodeApplied(Boolean promoCodeApplied) {
		this.promoCodeApplied = promoCodeApplied;
	}

	/**
	 * @param promoCodeValid the promoCodeValid to set
	 */
	public void setPromoCodeValid(Boolean promoCodeValid) {
		this.promoCodeValid = promoCodeValid;
	}

	/**
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	/**
	 * @return the clientTimeZone
	 */
	public Long getClientTimeZone() {
		return clientTimeZone;
	}

	/**
	 * @param clientTimeZone the clientTimeZone to set
	 */
	public void setClientTimeZone(Long clientTimeZone) {
		this.clientTimeZone = clientTimeZone;
	}

	/**
	 * @return the clientDateTime
	 */
	public LocalDateTime getClientDateTime() {
		return clientDateTime;
	}

	/**
	 * @param clientDateTime the clientDateTime to set
	 */
	public void setClientDateTime(LocalDateTime clientDateTime) {
		this.clientDateTime = clientDateTime;
	}
	
}
