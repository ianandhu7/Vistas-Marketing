/**
 * 
 */
package co.vistafoundation.vlearning.notification.dto;

/**
 * @author NAVEEN
 *
 */
public class FCMTokenResponse {
	
	
	private String  applicationVersion;
	
	private String application;
	
	private String scope;
	
    private String  authorizedEntity;
	
	private String appSigner;
	
	private String platform;

	/**
	 * @return the applicationVersion
	 */
	public String getApplicationVersion() {
		return applicationVersion;
	}

	/**
	 * @param applicationVersion the applicationVersion to set
	 */
	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the authorizedEntity
	 */
	public String getAuthorizedEntity() {
		return authorizedEntity;
	}

	/**
	 * @param authorizedEntity the authorizedEntity to set
	 */
	public void setAuthorizedEntity(String authorizedEntity) {
		this.authorizedEntity = authorizedEntity;
	}

	/**
	 * @return the appSigner
	 */
	public String getAppSigner() {
		return appSigner;
	}

	/**
	 * @param appSigner the appSigner to set
	 */
	public void setAppSigner(String appSigner) {
		this.appSigner = appSigner;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @param applicationVersion
	 * @param application
	 * @param scope
	 * @param authorizedEntity
	 * @param appSigner
	 * @param platform
	 */
	public FCMTokenResponse(String applicationVersion, String application, String scope, String authorizedEntity,
			String appSigner, String platform) {
		super();
		this.applicationVersion = applicationVersion;
		this.application = application;
		this.scope = scope;
		this.authorizedEntity = authorizedEntity;
		this.appSigner = appSigner;
		this.platform = platform;
	}

	
	public FCMTokenResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
