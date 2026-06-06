/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author Ahmed
 *
 */
public class WebExMetaDataDTO {

	private String meetingKey;
	private String meetingPassword;
	private String meetingUuid;
	private String meetingGuestUserToken;
	private String hostStartMeetingUrl;
	private String attendeeJoinMeetingUrl;

	/**
	 * @param meetingKey
	 * @param meetingPassword
	 * @param meetingUuid
	 * @param meetingGuestUserToken
	 * @param hostStartMeetingUrl
	 * @param attendeeJoinMeetingUrl
	 */
	public WebExMetaDataDTO(String meetingKey, String meetingPassword, String meetingUuid, String meetingGuestUserToken,
			String hostStartMeetingUrl, String attendeeJoinMeetingUrl) {
		super();
		this.meetingKey = meetingKey;
		this.meetingPassword = meetingPassword;
		this.meetingUuid = meetingUuid;
		this.meetingGuestUserToken = meetingGuestUserToken;
		this.hostStartMeetingUrl = hostStartMeetingUrl;
		this.attendeeJoinMeetingUrl = attendeeJoinMeetingUrl;
	}

	/**
	 * 
	 */
	public WebExMetaDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the meetingKey
	 */
	public String getMeetingKey() {
		return meetingKey;
	}

	/**
	 * @param meetingKey the meetingKey to set
	 */
	public void setMeetingKey(String meetingKey) {
		this.meetingKey = meetingKey;
	}

	/**
	 * @return the meetingPassword
	 */
	public String getMeetingPassword() {
		return meetingPassword;
	}

	/**
	 * @param meetingPassword the meetingPassword to set
	 */
	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}

	/**
	 * @return the meetingUuid
	 */
	public String getMeetingUuid() {
		return meetingUuid;
	}

	/**
	 * @param meetingUuid the meetingUuid to set
	 */
	public void setMeetingUuid(String meetingUuid) {
		this.meetingUuid = meetingUuid;
	}

	/**
	 * @return the meetingGuestUserToken
	 */
	public String getMeetingGuestUserToken() {
		return meetingGuestUserToken;
	}

	/**
	 * @param meetingGuestUserToken the meetingGuestUserToken to set
	 */
	public void setMeetingGuestUserToken(String meetingGuestUserToken) {
		this.meetingGuestUserToken = meetingGuestUserToken;
	}

	/**
	 * @return the hostStartMeetingUrl
	 */
	public String getHostStartMeetingUrl() {
		return hostStartMeetingUrl;
	}

	/**
	 * @param hostStartMeetingUrl the hostStartMeetingUrl to set
	 */
	public void setHostStartMeetingUrl(String hostStartMeetingUrl) {
		this.hostStartMeetingUrl = hostStartMeetingUrl;
	}

	/**
	 * @return the attendeeJoinMeetingUrl
	 */
	public String getAttendeeJoinMeetingUrl() {
		return attendeeJoinMeetingUrl;
	}

	/**
	 * @param attendeeJoinMeetingUrl the attendeeJoinMeetingUrl to set
	 */
	public void setAttendeeJoinMeetingUrl(String attendeeJoinMeetingUrl) {
		this.attendeeJoinMeetingUrl = attendeeJoinMeetingUrl;
	}

}
