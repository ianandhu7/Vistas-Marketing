/**
 * 
 */
package co.vistafoundation.vlearning.videocipher.dto;

/**
 * @author Naveen Kumar 
 *
 */
public class VideoCipherDASH {
	
     
	private String manifest;
	
	private Object licenseServers;

	/**
	 * @return the manifest
	 */
	public String getManifest() {
		return manifest;
	}

	/**
	 * @param manifest the manifest to set
	 */
	public void setManifest(String manifest) {
		this.manifest = manifest;
	}

	/**
	 * @return the licenseServers
	 */
	public Object getLicenseServers() {
		return licenseServers;
	}

	/**
	 * @param licenseServers the licenseServers to set
	 */
	public void setLicenseServers(Object licenseServers) {
		this.licenseServers = licenseServers;
	}

	/**
	 * @param manifest
	 * @param licenseServers
	 */
	public VideoCipherDASH(String manifest, Object licenseServers) {
		super();
		this.manifest = manifest;
		this.licenseServers = licenseServers;
	}

	/**
	 * 
	 */
	public VideoCipherDASH() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
