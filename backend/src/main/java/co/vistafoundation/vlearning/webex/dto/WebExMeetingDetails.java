package co.vistafoundation.vlearning.webex.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "webex_meeting_details")
public class WebExMeetingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meeting_key")
	private Long meetingKey;
	@Column(name = "meeting_password")
	private String meetingPassword;
	@Column(name = "meeting_uuid")
	private String meetingUUID;
	@Column(name = "meeting_guest_user_token")
	private String meetingGuestToken;

	public WebExMeetingDetails(Long meetingKey, String meetingPassword, String meetingUUID, String meetingGuestToken) {
		this.meetingKey = meetingKey;
		this.meetingPassword = meetingPassword;
		this.meetingUUID = meetingUUID;
		this.meetingGuestToken = meetingGuestToken;
	}

	public WebExMeetingDetails() {

	}

	public Long getMeetingKey() {
		return meetingKey;
	}

	public void setMeetingKey(Long meetingKey) {
		this.meetingKey = meetingKey;
	}

	public String getMeetingPassword() {
		return meetingPassword;
	}

	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}

	public String getMeetingUUID() {
		return meetingUUID;
	}

	public void setMeetingUUID(String meetingUUID) {
		this.meetingUUID = meetingUUID;
	}

	public String getMeetingGuestToken() {
		return meetingGuestToken;
	}

	public void setMeetingGuestToken(String meetingGuestToken) {
		this.meetingGuestToken = meetingGuestToken;
	}

	@Override
	public String toString() {
		return "WebExMeetingDetails [meetingKey=" + meetingKey + ", meetingPassword=" + meetingPassword
				+ ", meetingUUID=" + meetingUUID + ", meetingGuestToken=" + meetingGuestToken + "]";
	}

}
