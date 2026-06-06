/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "DEMO_CLASS_SCHEDULE")
public class DemoClassSchedule extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDEMO_CLASS_SCHEDULE")
	private Long idDemoClassSchedule;

	@Column(name = "idDEMO_CLASS")
	private Long idDemoClass;

	@Column(name = "WEBEX_MEETING_KEY")
	private String webExMeetingKey;

	@Column(name = "WEBEX_MEETING_UUID")
	private String webExMeetingUuid;

	@Column(name = "WEBEX_MEETING_GUEST_USER_TOKEN")
	private String webExMeetingGuestUserToken;

	@Column(name = "WEBEX_MEETING_PASSWORD")
	private String webExMeetingPassword;

	@Column(name = "WEBEX_HOST_START_MEETING_URL",length = 2048)
	private String webExHostStartMeetingUrl;

	@Column(name = "WEBEX_ATTENDEE_JOIN_MEETING_URL",length = 2048)
	private String webExAttendeeJoinMeetingUrl;

	/**
	 * @param idDemoClassSchedule
	 * @param idDemoClass
	 * @param webExMeetingKey
	 * @param webExMeetingUuid
	 * @param webExMeetingGuestUserToken
	 * @param webExMeetingPassword
	 * @param webExHostStartMeetingUrl
	 * @param webExAttendeeJoinMeetingUrl
	 */
	public DemoClassSchedule(Long idDemoClassSchedule, Long idDemoClass, String webExMeetingKey,
			String webExMeetingUuid, String webExMeetingGuestUserToken, String webExMeetingPassword,
			String webExHostStartMeetingUrl, String webExAttendeeJoinMeetingUrl) {
		super();
		this.idDemoClassSchedule = idDemoClassSchedule;
		this.idDemoClass = idDemoClass;
		this.webExMeetingKey = webExMeetingKey;
		this.webExMeetingUuid = webExMeetingUuid;
		this.webExMeetingGuestUserToken = webExMeetingGuestUserToken;
		this.webExMeetingPassword = webExMeetingPassword;
		this.webExHostStartMeetingUrl = webExHostStartMeetingUrl;
		this.webExAttendeeJoinMeetingUrl = webExAttendeeJoinMeetingUrl;
	}

	/**
	 * 
	 */
	public DemoClassSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idDemoClassSchedule
	 */
	public Long getIdDemoClassSchedule() {
		return idDemoClassSchedule;
	}

	/**
	 * @param idDemoClassSchedule the idDemoClassSchedule to set
	 */
	public void setIdDemoClassSchedule(Long idDemoClassSchedule) {
		this.idDemoClassSchedule = idDemoClassSchedule;
	}

	/**
	 * @return the idDemoClass
	 */
	public Long getIdDemoClass() {
		return idDemoClass;
	}

	/**
	 * @param idDemoClass the idDemoClass to set
	 */
	public void setIdDemoClass(Long idDemoClass) {
		this.idDemoClass = idDemoClass;
	}

	/**
	 * @return the webExMeetingKey
	 */
	public String getWebExMeetingKey() {
		return webExMeetingKey;
	}

	/**
	 * @param webExMeetingKey the webExMeetingKey to set
	 */
	public void setWebExMeetingKey(String webExMeetingKey) {
		this.webExMeetingKey = webExMeetingKey;
	}

	/**
	 * @return the webExMeetingUuid
	 */
	public String getWebExMeetingUuid() {
		return webExMeetingUuid;
	}

	/**
	 * @param webExMeetingUuid the webExMeetingUuid to set
	 */
	public void setWebExMeetingUuid(String webExMeetingUuid) {
		this.webExMeetingUuid = webExMeetingUuid;
	}

	/**
	 * @return the webExMeetingGuestUserToken
	 */
	public String getWebExMeetingGuestUserToken() {
		return webExMeetingGuestUserToken;
	}

	/**
	 * @param webExMeetingGuestUserToken the webExMeetingGuestUserToken to set
	 */
	public void setWebExMeetingGuestUserToken(String webExMeetingGuestUserToken) {
		this.webExMeetingGuestUserToken = webExMeetingGuestUserToken;
	}

	/**
	 * @return the webExMeetingPassword
	 */
	public String getWebExMeetingPassword() {
		return webExMeetingPassword;
	}

	/**
	 * @param webExMeetingPassword the webExMeetingPassword to set
	 */
	public void setWebExMeetingPassword(String webExMeetingPassword) {
		this.webExMeetingPassword = webExMeetingPassword;
	}

	/**
	 * @return the webExHostStartMeetingUrl
	 */
	public String getWebExHostStartMeetingUrl() {
		return webExHostStartMeetingUrl;
	}

	/**
	 * @param webExHostStartMeetingUrl the webExHostStartMeetingUrl to set
	 */
	public void setWebExHostStartMeetingUrl(String webExHostStartMeetingUrl) {
		this.webExHostStartMeetingUrl = webExHostStartMeetingUrl;
	}

	/**
	 * @return the webExAttendeeJoinMeetingUrl
	 */
	public String getWebExAttendeeJoinMeetingUrl() {
		return webExAttendeeJoinMeetingUrl;
	}

	/**
	 * @param webExAttendeeJoinMeetingUrl the webExAttendeeJoinMeetingUrl to set
	 */
	public void setWebExAttendeeJoinMeetingUrl(String webExAttendeeJoinMeetingUrl) {
		this.webExAttendeeJoinMeetingUrl = webExAttendeeJoinMeetingUrl;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
