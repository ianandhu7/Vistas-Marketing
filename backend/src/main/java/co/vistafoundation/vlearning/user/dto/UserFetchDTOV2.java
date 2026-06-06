package co.vistafoundation.vlearning.user.dto;
import java.util.Date;

public class UserFetchDTOV2 {

	private int page;
	
	private int size;
	
	private String roleName;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private String firstName;
	
	private String email;
	
	private String mobileNumber;
	
	private Long idClassStandard =-1l;
	
	private Long idSyllabus =-1l;
	
	private Long idMedium =-1l;
	
	private Long idState =-1l;



	public UserFetchDTOV2(int page, int size, String roleName, Date dateFrom, Date dateTo, String firstName,
			String email, String mobileNumber, Long idClassStandard, Long idSyllabus, Long idMedium, Long idState) {
		super();
		this.page = page;
		this.size = size;
		this.roleName = roleName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.firstName = firstName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idMedium = idMedium;
		this.idState = idState;
	}

	public UserFetchDTOV2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Long getIdMedium() {
		return idMedium;
	}

	public void setIdMedium(Long idMedium) {
		this.idMedium = idMedium;
	}

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}
	
	
}