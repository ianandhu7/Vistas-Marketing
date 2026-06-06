package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author NaveenKumar, vk
 * 
 **/

@Entity
@Table(name = "TEACHER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class Teacher extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTEACHER", nullable = false)
	private Long idTeacher;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
			"classStandard" })
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId")
	private User user;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;

	@Column(name = "idWEBEX_POOL")
	private Long idWebexPool;

	@Column(name = "TEACHER_DESC", length = 500)
	private String teacherDesc;

	@Column(name = "EXP_LEVEL", length = 45)
	private String expLevel;

	@Column(name = "EMAIL_ID", length = 45)
	private String emailId;

	@Column(name = "TEACHER_NAME", length = 100, nullable = false)
	private String teacherName;

	@Column(name = "PRIMARY_SUBJECT", length = 50, nullable = false)
	private String primarySubject;

	@Column(name = "TEACHER_IMAGE", length = 500)
	private String teacherImage;

	@Min(0)
	@Max(5)
	@Column(name = "RATING")
	private int rating;

	@Column(name = "TEACHER_ADDRESS", columnDefinition = "varchar(500) default 'not provided'")
	private String teacherAddress;

	@Column(name = "TEACHER_JOINED_DATE")
	@Temporal(TemporalType.DATE)
	private Date joinedDate;

	@Column(name = "HOMEPAGE_FLAG")
	private Boolean displayInHomepageFlag;

	@Column(name = "TEACHER_HEADER", length = 500)
	private String teacherHeader;
	
	@Column(name = "INTRO_VIDEO", length = 500)
	private String introVideo;
	
	@Column(name = "CATEGORY", length = 500)
	private String category;
	
	
	@Column(name = "SEARCH_KEY", length = 2000)
	private String searchKey;
	
	@Column(name = "SCHEDULE_REMARKS", length = 500)
	private String scheduleRemarks;
	
	@Column(name = "CONTRACT_END_DATE")
	@Temporal(TemporalType.DATE)
	private Date contractEndDate;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="idTEACHER",referencedColumnName="idTEACHER")
	private List<TeacherAvailability> teacherAvailability;
	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIntroVideo() {
		return introVideo;
	}

	public void setIntroVideo(String introVideo) {
		this.introVideo = introVideo;
	}

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	public String getTeacherHeader() {
		return teacherHeader;
	}

	public void setTeacherHeader(String teacherHeader) {
		this.teacherHeader = teacherHeader;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @return the idWebexPool
	 */
	public Long getIdWebexPool() {
		return idWebexPool;
	}

	/**
	 * @param idWebexPool the idWebexPool to set
	 */
	public void setIdWebexPool(Long idWebexPool) {
		this.idWebexPool = idWebexPool;
	}

	/**
	 * @return the teacherDesc
	 */
	public String getTeacherDesc() {
		return teacherDesc;
	}

	/**
	 * @param teacherDesc the teacherDesc to set
	 */
	public void setTeacherDesc(String teacherDesc) {
		this.teacherDesc = teacherDesc;
	}

	/**
	 * @return the expLevel
	 */
	public String getExpLevel() {
		return expLevel;
	}

	/**
	 * @param expLevel the expLevel to set
	 */
	public void setExpLevel(String expLevel) {
		this.expLevel = expLevel;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return the primarySubject
	 */
	public String getPrimarySubject() {
		return primarySubject;
	}

	/**
	 * @param primarySubject the primarySubject to set
	 */
	public void setPrimarySubject(String primarySubject) {
		this.primarySubject = primarySubject;
	}

	/**
	 * @return the teacherImage
	 */
	public String getTeacherImage() {
		return teacherImage;
	}

	/**
	 * @param teacherImage the teacherImage to set
	 */
	public void setTeacherImage(String teacherImage) {
		this.teacherImage = teacherImage;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the teacherAddress
	 */
	public String getTeacherAddress() {
		return teacherAddress;
	}

	/**
	 * @param teacherAddress the teacherAddress to set
	 */
	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}

	/**
	 * @return the joinedDate
	 */
	public Date getJoinedDate() {
		return joinedDate;
	}

	/**
	 * @param joinedDate the joinedDate to set
	 */
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	/**
	 * @return the displayInHomepageFlag
	 */
	public Boolean getDisplayInHomepageFlag() {
		return displayInHomepageFlag;
	}

	/**
	 * @param displayInHomepageFlag the displayInHomepageFlag to set
	 */
	public void setDisplayInHomepageFlag(Boolean displayInHomepageFlag) {
		this.displayInHomepageFlag = displayInHomepageFlag;
	}
	

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	/**
	 * @return the seheduleRemarks
	 */
	public String getScheduleRemarks() {
		return scheduleRemarks;
	}

	/**
	 * @param seheduleRemarks the seheduleRemarks to set
	 */
	public void setScheduleRemarks(String scheduleRemarks) {
		this.scheduleRemarks = scheduleRemarks;
	}

	/**
	 * @return the contractEndDate
	 */
	public Date getContractEndDate() {
		return contractEndDate;
	}

	/**
	 * @param contractEndDate the contractEndDate to set
	 */
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**
	 * @return the teacherAvailability
	 */
	public List<TeacherAvailability> getTeacherAvailability() {
		return teacherAvailability;
	}

	/**
	 * @param teacherAvailability the teacherAvailability to set
	 */
	public void setTeacherAvailability(List<TeacherAvailability> teacherAvailability) {
		this.teacherAvailability = teacherAvailability;
	}
	
	/**
	 * @param user
	 * @param activeFlag
	 * @param idWebexPool
	 * @param teacherDesc
	 * @param expLevel
	 * @param emailId
	 * @param teacherName
	 * @param primarySubject
	 * @param teacherImage
	 * @param rating
	 * @param teacherAddress
	 * @param joinedDate
	 * @param displayInHomepageFlag
	 * @param teacherHeader
	 * @param scheduleRemarks
	 * @param contractEndDate
	 */
	

	/**
	 * 
	 */
	public Teacher() {
		super();

	}

	public Teacher(User user, Boolean activeFlag, Long idWebexPool, String teacherDesc, String expLevel, String emailId,
			String teacherName, String primarySubject, String teacherImage, @Min(0) @Max(5) int rating,
			String teacherAddress, Date joinedDate, Boolean displayInHomepageFlag, String teacherHeader,
			String introVideo, String category, String scheduleRemarks, Date contractEndDate, List<TeacherAvailability> teacherAvailability) {
		super();
		this.user = user;
		this.activeFlag = activeFlag;
		this.idWebexPool = idWebexPool;
		this.teacherDesc = teacherDesc;
		this.expLevel = expLevel;
		this.emailId = emailId;
		this.teacherName = teacherName;
		this.primarySubject = primarySubject;
		this.teacherImage = teacherImage;
		this.rating = rating;
		this.teacherAddress = teacherAddress;
		this.joinedDate = joinedDate;
		this.displayInHomepageFlag = displayInHomepageFlag;
		this.teacherHeader = teacherHeader;
		this.introVideo = introVideo;
		this.category = category;
		this.scheduleRemarks = scheduleRemarks;
		this.contractEndDate = contractEndDate;
		this.teacherAvailability = teacherAvailability;
	}	
}
