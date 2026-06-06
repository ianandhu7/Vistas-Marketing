/**
 * 
 */
package co.vistafoundation.vlearning.contact_us.dto;

import java.util.Date;

/**
 * @author NAVEEN
 *
 */
public class ContactUsSearchDTO {
	
	
	private int page;
	
	private int size;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private String name;
	
	private String email;
	
	private String mobileNumber;

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
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

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
	 * @param page
	 * @param size
	 * @param dateFrom
	 * @param dateTo
	 * @param name
	 * @param email
	 * @param mobileNumber
	 */
	public ContactUsSearchDTO(int page, int size, Date dateFrom, Date dateTo, String name, String email,
			String mobileNumber) {
		super();
		this.page = page;
		this.size = size;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}

	/**
	 * 
	 */
	public ContactUsSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
