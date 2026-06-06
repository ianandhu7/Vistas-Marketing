package co.vistafoundation.vlearning.offlinecourse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/** Author Meghana**/


//deprecated 27-NOV-2020
@Table(name = "VIDEO_LANGUAGE_FIELD_SELECTOR")
public class VideoLanguageFieldSelector extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@Column(name = "idVIDEO_LANGUAGE_FIELD_SELECTOR")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idVideoLanguageFeildSelector;

	@Column(name = "VIDEO_LANGUAGE_CD",length =45)
	private String videoLanguageCd;

	@Column(name = "FIELD_NAME" , length =45)
	private String fieldName;

	@Column(name = "VIDEO_RESOLUTION" , length =45)
	private String videoResolution;

	public Long getIdVideoLanguageFeildSelector() {
		return idVideoLanguageFeildSelector;
	}

	public void setIdVideoLanguageFeildSelector(Long idVideoLanguageFeildSelector) {
		this.idVideoLanguageFeildSelector = idVideoLanguageFeildSelector;
	}

	public String getVideoLanguageCd() {
		return videoLanguageCd;
	}

	public void setVideoLanguageCd(String videoLanguageCd) {
		this.videoLanguageCd = videoLanguageCd;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getVideoResolution() {
		return videoResolution;
	}

	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	public VideoLanguageFieldSelector(String videoLanguageCd, String fieldName, String videoResolution) {
		super();
		this.videoLanguageCd = videoLanguageCd;
		this.fieldName = fieldName;
		this.videoResolution = videoResolution;
	}

	
	public VideoLanguageFieldSelector() {
	}
	
}
