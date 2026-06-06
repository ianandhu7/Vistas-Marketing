/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

import java.util.Date;

/**
 * @author vk
 *
 */
public class UserFetchDTO {
	
	private int page;
	
	private int size;
	
	private String roleName;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private String firstName;
	
	private String email;
	
	private String mobileNumber;
	
	/**
	 * 
	 */
	public UserFetchDTO() {
		super();
	}

	/**
	 * @param page
	 * @param size
	 * @param roleName
	 * @param date
	 */
	public UserFetchDTO(int page, int size, String roleName, Date dateFrom, Date dateTo) {
		super();
		this.page = page;
		this.size = size;
		this.roleName = roleName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	/**
	 * @param page
	 * @param size
	 * @param roleName
	 * @param date
	 * @param firstName
	 * @param email
	 * @param mobileNumber
	 */
	public UserFetchDTO(int page, int size, String roleName, Date dateFrom, Date dateTo, String firstName, String email,
			String mobileNumber) {
		super();
		this.page = page;
		this.size = size;
		this.roleName = roleName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.firstName = firstName;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
