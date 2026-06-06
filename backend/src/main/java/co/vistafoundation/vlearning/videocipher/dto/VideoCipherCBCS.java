package co.vistafoundation.vlearning.videocipher.dto;

/**
 * 
 * 
 * @author Naveen Kumar
 *
 */
public class VideoCipherCBCS {

	private String contentId;
	
	private String licenseServer;
	
	private String urlI;

	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the licenseServer
	 */
	public String getLicenseServer() {
		return licenseServer;
	}

	/**
	 * @param licenseServer the licenseServer to set
	 */
	public void setLicenseServer(String licenseServer) {
		this.licenseServer = licenseServer;
	}

	/**
	 * @return the urlI
	 */
	public String getUrlI() {
		return urlI;
	}

	/**
	 * @param urlI the urlI to set
	 */
	public void setUrlI(String urlI) {
		this.urlI = urlI;
	}

	/**
	 * @param contentId
	 * @param licenseServer
	 * @param urlI
	 */
	public VideoCipherCBCS(String contentId, String licenseServer, String urlI) {
		super();
		this.contentId = contentId;
		this.licenseServer = licenseServer;
		this.urlI = urlI;
	}

	/**
	 * 
	 */
	public VideoCipherCBCS() {
		super();
		// TODO Auto-generated constructor stub
	}

}
