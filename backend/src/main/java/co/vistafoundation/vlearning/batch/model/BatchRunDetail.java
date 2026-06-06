package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "BATCH_RUN_DETAIL")
public class BatchRunDetail extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idBATCH_RUN_DETAIL", nullable = false)
	private Long idBatchRunDetail;

	@Column(name = "BATCH_RUN_DATE")
	private String batchRundate;

	@Column(name = "ACTUAL_START_TIME")
	private String actualStartTime;

	@Column(name = "ACTUAL_END_TIME")
	private String actualEndTime;

	@Column(name = "MEETING_ID", length = 100)
	private String meetingId;
	@Column(name = "MEETING_GUEST_USER_TOKEN", length = 255)
	private String meetingGuestUserToken;

	@Column(name = "MEETING_UU_ID", length = 255)
	private String meetingUuId;

	@Column(name = "MEETING_PASSWORD", length = 100)
	private String meetingPassword;

	@Column(name = "ATTENDEE_MEETING_URL", length = 2083)
	private String attendeeMeetingUrl;

	@Column(name = "HOST_MEETING_URL", length = 2083)
	private String hostMeetingUrl;

	@Column(name = "MEETING_TITLE", length = 255)
	private String meetingTitle;

	@Column(name = "MEETING_DESCRIPTION", length = 500)
	private String meetingDescription;

	@Column(name = "ID_WEBEX_POOL")
	private Long idWebExPool;

	@Column(name = "idBATCH", nullable = false)
	private Long idBatch;

	public Long getIdBatchRunDetail() {
		return idBatchRunDetail;
	}

	public void setIdBatchRunDetail(Long idBatchRunDetail) {
		this.idBatchRunDetail = idBatchRunDetail;
	}

	public String getBatchRundate() {
		return batchRundate;
	}

	public void setBatchRundate(String batchRundate) {
		this.batchRundate = batchRundate;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingGuestUserToken() {
		return meetingGuestUserToken;
	}

	public void setMeetingGuestUserToken(String meetingGuestUserToken) {
		this.meetingGuestUserToken = meetingGuestUserToken;
	}

	public String getMeetingUuId() {
		return meetingUuId;
	}

	public void setMeetingUuId(String meetingUuId) {
		this.meetingUuId = meetingUuId;
	}

	public String getMeetingPassword() {
		return meetingPassword;
	}

	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}

	public String getAttendeeMeetingUrl() {
		return attendeeMeetingUrl;
	}

	public void setAttendeeMeetingUrl(String attendeeMeetingUrl) {
		this.attendeeMeetingUrl = attendeeMeetingUrl;
	}

	public String getHostMeetingUrl() {
		return hostMeetingUrl;
	}

	public void setHostMeetingUrl(String hostMeetingUrl) {
		this.hostMeetingUrl = hostMeetingUrl;
	}

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public String getMeetingDescription() {
		return meetingDescription;
	}

	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the idWebExPool
	 */
	public Long getIdWebExPool() {
		return idWebExPool;
	}

	/**
	 * @param idWebExPool the idWebExPool to set
	 */
	public void setIdWebExPool(Long idWebExPool) {
		this.idWebExPool = idWebExPool;
	}

	/**
	 * @param idBatchRunDetail
	 * @param batchRundate
	 * @param actualStartTime
	 * @param actualEndTime
	 * @param meetingId
	 * @param meetingGuestUserToken
	 * @param meetingUuId
	 * @param meetingPassword
	 * @param attendeeMeetingUrl
	 * @param hostMeetingUrl
	 * @param meetingTitle
	 * @param meetingDescription
	 * @param idWebExPool
	 * @param idBatch
	 */
	public BatchRunDetail(Long idBatchRunDetail, String batchRundate, String actualStartTime, String actualEndTime,
			String meetingId, String meetingGuestUserToken, String meetingUuId, String meetingPassword,
			String attendeeMeetingUrl, String hostMeetingUrl, String meetingTitle, String meetingDescription,
			Long idWebExPool, Long idBatch) {
		super();
		this.idBatchRunDetail = idBatchRunDetail;
		this.batchRundate = batchRundate;
		this.actualStartTime = actualStartTime;
		this.actualEndTime = actualEndTime;
		this.meetingId = meetingId;
		this.meetingGuestUserToken = meetingGuestUserToken;
		this.meetingUuId = meetingUuId;
		this.meetingPassword = meetingPassword;
		this.attendeeMeetingUrl = attendeeMeetingUrl;
		this.hostMeetingUrl = hostMeetingUrl;
		this.meetingTitle = meetingTitle;
		this.meetingDescription = meetingDescription;
		this.idWebExPool = idWebExPool;
		this.idBatch = idBatch;
	}

	public BatchRunDetail() {

	}

}
