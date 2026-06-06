package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "batch_run_recording")
public class BatchRunRecording implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idbatch_run_recording")
	private Long idBatchRunRecording;
	@Column(name = "recording_url", length = 1000)
	private String recordingUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idbatch_run_details", referencedColumnName = "idBATCH_RUN_DETAIL")
	private BatchRunDetail batchRunDetail;

	@Column(name = "recording_id", length = 500)
	private String recordingId;
	@Column(name = "meeting_series_id", length = 1000)
	private String meetingSeriesId;
	@Column(name = "schedule_meeting_series_id", length = 1000)
	private String scheduleMeetingSeriesId;

	@Column(name = "webex_topic", length = 500)
	private String webExTopic;
	@Column(name = "meeting_id", length = 500)
	private String meetingId;
	@Column(name = "create_time")
	private String createTime;

	@Column(name = "duration_in_seconds")
	private String durationInSecond;
	@Column(name = "size_in_bytes")
	private String sizeInBytes;
	@Column(name = "password")
	private String password;

	@Column(name = "stream_url", length = 2000)
	private String streamUrl;

	@Column(name = "host_stream_url", length = 2000)
	private String hostStreamUrl;

	@Column(name = "file_url", length = 2000)
	private String fileUrl;

	@Column(name = "host_webex_id", length = 500)
	private String hostWebExId;

	@Column(name = "time_zone_id")
	private String timeZoneId;

	public BatchRunRecording(Long idBatchRunRecording, String recordingUrl, BatchRunDetail batchRunDetail,
			String recordingId, String meetingSeriesId, String scheduleMeetingSeriesId, String webExTopic,
			String meetingId, String createTime, String durationInSecond, String sizeInBytes, String password,
			String streamUrl, String hostStreamUrl, String fileUrl, String hostWebExId, String timeZoneId) {
		super();
		this.idBatchRunRecording = idBatchRunRecording;
		this.recordingUrl = recordingUrl;
		this.batchRunDetail = batchRunDetail;
		this.recordingId = recordingId;
		this.meetingSeriesId = meetingSeriesId;
		this.scheduleMeetingSeriesId = scheduleMeetingSeriesId;
		this.webExTopic = webExTopic;
		this.meetingId = meetingId;
		this.createTime = createTime;
		this.durationInSecond = durationInSecond;
		this.sizeInBytes = sizeInBytes;
		this.password = password;
		this.streamUrl = streamUrl;
		this.hostStreamUrl = hostStreamUrl;
		this.fileUrl = fileUrl;
		this.hostWebExId = hostWebExId;
		this.timeZoneId = timeZoneId;
	}

	public BatchRunRecording() {

	}

	public Long getIdBatchRunRecording() {
		return idBatchRunRecording;
	}

	public void setIdBatchRunRecording(Long idBatchRunRecording) {
		this.idBatchRunRecording = idBatchRunRecording;
	}

	public String getRecordingUrl() {
		return recordingUrl;
	}

	public void setRecordingUrl(String recordingUrl) {
		this.recordingUrl = recordingUrl;
	}

	public BatchRunDetail getBatchRunDetail() {
		return batchRunDetail;
	}

	public void setBatchRunDetail(BatchRunDetail batchRunDetail) {
		this.batchRunDetail = batchRunDetail;
	}

	public String getRecordingId() {
		return recordingId;
	}

	public void setRecordingId(String recordingId) {
		this.recordingId = recordingId;
	}

	public String getMeetingSeriesId() {
		return meetingSeriesId;
	}

	public void setMeetingSeriesId(String meetingSeriesId) {
		this.meetingSeriesId = meetingSeriesId;
	}

	public String getScheduleMeetingSeriesId() {
		return scheduleMeetingSeriesId;
	}

	public void setScheduleMeetingSeriesId(String scheduleMeetingSeriesId) {
		this.scheduleMeetingSeriesId = scheduleMeetingSeriesId;
	}

	public String getWebExTopic() {
		return webExTopic;
	}

	public void setWebExTopic(String webExTopic) {
		this.webExTopic = webExTopic;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDurationInSecond() {
		return durationInSecond;
	}

	public void setDurationInSecond(String durationInSecond) {
		this.durationInSecond = durationInSecond;
	}

	public String getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(String sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStreamUrl() {
		return streamUrl;
	}

	public void setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
	}

	public String getHostStreamUrl() {
		return hostStreamUrl;
	}

	public void setHostStreamUrl(String hostStreamUrl) {
		this.hostStreamUrl = hostStreamUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getHostWebExId() {
		return hostWebExId;
	}

	public void setHostWebExId(String hostWebExId) {
		this.hostWebExId = hostWebExId;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	@Override
	public String toString() {
		return "BatchRunRecording [idBatchRunRecording=" + idBatchRunRecording + ", recordingUrl=" + recordingUrl
				+ ", batchRunDetail=" + batchRunDetail + ", recordingId=" + recordingId + ", meetingSeriesId="
				+ meetingSeriesId + ", scheduleMeetingSeriesId=" + scheduleMeetingSeriesId + ", webExTopic="
				+ webExTopic + ", meetingId=" + meetingId + ", createTime=" + createTime + ", durationInSecond="
				+ durationInSecond + ", sizeInBytes=" + sizeInBytes + ", password=" + password + ", streamUrl="
				+ streamUrl + ", hostStreamUrl=" + hostStreamUrl + ", fileUrl=" + fileUrl + ", hostWebExId="
				+ hostWebExId + ", timeZoneId=" + timeZoneId + "]";
	}
	
	

}
