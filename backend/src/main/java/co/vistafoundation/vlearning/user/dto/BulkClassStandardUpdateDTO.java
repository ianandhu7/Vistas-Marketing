package co.vistafoundation.vlearning.user.dto;

/**
 * @author Abdul Elahi 
 */
public class BulkClassStandardUpdateDTO {
    
	private Long userSurId;
    private String message;
    private Boolean isValid;
    private String phone;
    private Long idClassStandard;
    private String firstName;
    private String schoolName;
    private String remarks;
    private Long idSyllabus;
    private Long idStudentMedium;
    private Long idLanguage;
    private Long idState;
    private String allowEdit;
    private String activeStatus;
    private String password;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getIdClassStandard() {
        return idClassStandard;
    }

    public void setIdClassStandard(Long idClassStandard) {
        this.idClassStandard = idClassStandard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    
    /**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}
	

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idStudentMedium
	 */
	public Long getIdStudentMedium() {
		return idStudentMedium;
	}

	/**
	 * @param idStudentMedium the idStudentMedium to set
	 */
	public void setIdStudentMedium(Long idStudentMedium) {
		this.idStudentMedium = idStudentMedium;
	}

	/**
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}
	
	

	/**
	 * @return the allowEdit
	 */
	public String getAllowEdit() {
		return allowEdit;
	}

	/**
	 * @param allowEdit the allowEdit to set
	 */
	public void setAllowEdit(String allowEdit) {
		this.allowEdit = allowEdit;
	}

	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param message
	 * @param isValid
	 * @param phone
	 * @param idClassStandard
	 * @param firstName
	 * @param schoolName
	 */
	public BulkClassStandardUpdateDTO(String message, Boolean isValid, String phone, Long idClassStandard,
			String firstName, String schoolName) {
		super();
		this.message = message;
		this.isValid = isValid;
		this.phone = phone;
		this.idClassStandard = idClassStandard;
		this.firstName = firstName;
		this.schoolName = schoolName;
	}

	/**
	 * 
	 */
	public BulkClassStandardUpdateDTO() {
		super();
	}
	

	/**
	 * @param userSurId
	 * @param message
	 * @param isValid
	 * @param phone
	 * @param idClassStandard
	 * @param firstName
	 * @param schoolName
	 * @param remarks
	 * @param idSyllabus
	 * @param idStudentMedium
	 * @param idLanguage
	 * @param idState
	 */
	public BulkClassStandardUpdateDTO(Long userSurId, String message, Boolean isValid, String phone,
			Long idClassStandard, String firstName, String schoolName, String remarks, Long idSyllabus,
			Long idStudentMedium, Long idLanguage, Long idState) {
		super();
		this.userSurId = userSurId;
		this.message = message;
		this.isValid = isValid;
		this.phone = phone;
		this.idClassStandard = idClassStandard;
		this.firstName = firstName;
		this.schoolName = schoolName;
		this.remarks = remarks;
		this.idSyllabus = idSyllabus;
		this.idStudentMedium = idStudentMedium;
		this.idLanguage = idLanguage;
		this.idState = idState;
	}

	@Override
	public String toString() {
		return "BulkClassStandardUpdateDTO [message=" + message + ", isValid=" + isValid + ", phone=" + phone
				+ ", idClassStandard=" + idClassStandard + ", firstName=" + firstName + ", schoolName=" + schoolName
				+ "]";
	}    
}
