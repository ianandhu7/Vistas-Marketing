package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "BATCH", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "idTEACHER", "FROM_TIME", "TO_TIME", "idDAY_OF_WEEK_CODE" }) })

@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class Batch extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBATCH", nullable = false)
	private Long idBatch;

	@Column(name = "idPRODUCT", nullable = false)
	private Long idProduct;

	@Column(name = "idTEACHER", nullable = false)
	private Long idTeacher;

	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "FROM_TIME")
	private LocalTime batchFromTime;

	@Column(name = "BATCH_NAME", length = 100)
	private String batchName;

	@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "TO_TIME")
	private LocalTime batchToTime;

	@Transient
	private String fromTime;

	@Transient
	private String toTime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idDAY_OF_WEEK_CODE", referencedColumnName = "idDAY_OF_WEEK_CODE")
	private DayOfWeekCode dayOfWeekCode;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;

	@Column(name = "ID_SPECIAL_OFFER")
	private Long idSpecialOffer;

	@Column(name = "BATCH_START_DATE",  nullable = false)
	private LocalDate batchStartDate;

	@Column(name = "CURRENT_VACANCY")
	private int currentVacancy;

	@Column(name = "CURRENT_OCCUPANCY")
	private int currentOccupancy;

	@Column(name = "idWEBEX_POOL")
	private Long idWebexPool;
	


	@Column(name = "DEMO_VIDEO_URL", length = 2083)
	private String demoVideoUrl;

	@Version
	@Column(name = "VERSION", columnDefinition = "integer DEFAULT 0", nullable = false)
	private long version = 0L;

	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name = "BATCH_END_DATE")
	private LocalDate batchEndDate;
	
	@Column(name = "BATCH_DEACTIVATE_DATE")
	private LocalDate batchDeactivateDate;
	
	@Column(name = "SPECIAL_OFFER_FLAG")
	private Boolean specialOfferFlag;
	
	@Column(name = "idBATCH_GROUP")
	private Long idBatchGroup;

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public int getCurrentVacancy() {
		return currentVacancy;
	}

	public void setCurrentVacancy(int currentVacancy) {
		this.currentVacancy = currentVacancy;
	}

	public int getCurrentOccupancy() {
		return currentOccupancy;
	}

	public void setCurrentOccupancy(int currentOccupancy) {
		this.currentOccupancy = currentOccupancy;
	}

	public Long getIdWebexPool() {
		return idWebexPool;
	}

	public void setIdWebexPool(Long idWebexPool) {
		this.idWebexPool = idWebexPool;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getDemoVideoUrl() {
		return demoVideoUrl;
	}

	public void setDemoVideoUrl(String demoVideoUrl) {
		this.demoVideoUrl = demoVideoUrl;
	}

	public Batch() {

	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the batchFromTime
	 */
	public LocalTime getBatchFromTime() {
		return batchFromTime;
	}

	/**
	 * @param batchFromTime the batchFromTime to set
	 */
	public void setBatchFromTime(LocalTime batchFromTime) {
		this.batchFromTime = batchFromTime;
	}

	/**
	 * @return the batchToTime
	 */
	public LocalTime getBatchToTime() {
		return batchToTime;
	}

	/**
	 * @param batchToTime the batchToTime to set
	 */
	public void setBatchToTime(LocalTime batchToTime) {
		this.batchToTime = batchToTime;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the idSpecialOffer
	 */
	public Long getIdSpecialOffer() {
		return idSpecialOffer;
	}

	/**
	 * @param idSpecialOffer the idSpecialOffer to set
	 */
	public void setIdSpecialOffer(Long idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
	}

	/**
	 * @return the batchStartDate
	 */
	public LocalDate getBatchStartDate() {
		return batchStartDate;
	}

	/**
	 * @param batchStartDate the batchStartDate to set
	 */
	public void setBatchStartDate(LocalDate batchStartDate) {
		this.batchStartDate = batchStartDate;
	}

	/**
	 * @return the dayOfWeekCode
	 */
	public DayOfWeekCode getDayOfWeekCode() {
		return dayOfWeekCode;
	}

	/**
	 * @param dayOfWeekCode the dayOfWeekCode to set
	 */
	public void setDayOfWeekCode(DayOfWeekCode dayOfWeekCode) {
		this.dayOfWeekCode = dayOfWeekCode;
	}
	
	

	/**
	 * @return the batchEndDate
	 */
	public LocalDate getBatchEndDate() {
		return batchEndDate;
	}

	/**
	 * @param batchEndDate the batchEndDate to set
	 */
	public void setBatchEndDate(LocalDate batchEndDate) {
		this.batchEndDate = batchEndDate;
	}

	/**
	 * @return the batchDeactivateDate
	 */
	public LocalDate getBatchDeactivateDate() {
		return batchDeactivateDate;
	}

	/**
	 * @param batchDeactivateDate the batchDeactivateDate to set
	 */
	public void setBatchDeactivateDate(LocalDate batchDeactivateDate) {
		this.batchDeactivateDate = batchDeactivateDate;
	}

	/**
	 * @param idBatch
	 * @param idProduct
	 * @param idTeacher
	 * @param batchFromTime
	 * @param batchName
	 * @param batchToTime
	 * @param fromTime
	 * @param toTime
	 * @param dayOfWeekCode
	 * @param activeFlag
	 * @param idSpecialOffer
	 * @param batchStartDate
	 * @param currentVacancy
	 * @param currentOccupancy
	 * @param idWebexPool
	 * @param demoVideoUrl
	 * @param version
	 * @param paymentStatus
	 */
	public Batch(Long idBatch, Long idProduct, Long idTeacher, LocalTime batchFromTime, String batchName,
			LocalTime batchToTime, String fromTime, String toTime, DayOfWeekCode dayOfWeekCode, Boolean activeFlag,
			Long idSpecialOffer, LocalDate batchStartDate, int currentVacancy, int currentOccupancy, Long idWebexPool,
			String demoVideoUrl, long version, String paymentStatus) {
		super();
		this.idBatch = idBatch;
		this.idProduct = idProduct;
		this.idTeacher = idTeacher;
		this.batchFromTime = batchFromTime;
		this.batchName = batchName;
		this.batchToTime = batchToTime;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.dayOfWeekCode = dayOfWeekCode;
		this.activeFlag = activeFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.batchStartDate = batchStartDate;
		this.currentVacancy = currentVacancy;
		this.currentOccupancy = currentOccupancy;
		this.idWebexPool = idWebexPool;
		this.demoVideoUrl = demoVideoUrl;
		this.version = version;
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the specialOfferFlag
	 */
	public Boolean getSpecialOfferFlag() {
		return specialOfferFlag;
	}

	/**
	 * @param specialOfferFlag the specialOfferFlag to set
	 */
	public void setSpecialOfferFlag(Boolean specialOfferFlag) {
		this.specialOfferFlag = specialOfferFlag;
	}

	/**
	 * @return the idBatchGroup
	 */
	public Long getIdBatchGroup() {
		return idBatchGroup;
	}

	/**
	 * @param idBatchGroup the idBatchGroup to set
	 */
	public void setIdBatchGroup(Long idBatchGroup) {
		this.idBatchGroup = idBatchGroup;
	}
	

}
