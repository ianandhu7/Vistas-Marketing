package co.vistafoundation.vlearning.firebase.config.dto;


public class Version {
    private String versionNumber;
    private long updateTime;
    private UpdateUser updateUser;
    private String updateOrigin;
    private String updateType;
    private String  rollbackSource;
    private boolean legacy; 
    private String description;
    
    // Getters and Setters
    public String getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
     
    /**
	 * @return the updateTime
	 */
	public long getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public UpdateUser getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(UpdateUser updateUser) {
        this.updateUser = updateUser;
    }
    public String getUpdateOrigin() {
        return updateOrigin;
    }
    public void setUpdateOrigin(String updateOrigin) {
        this.updateOrigin = updateOrigin;
    }
    public String getUpdateType() {
        return updateType;
    }
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
	/**
	 * @return the rollbackSource
	 */
	public String getRollbackSource() {
		return rollbackSource;
	}
	/**
	 * @param rollbackSource the rollbackSource to set
	 */
	public void setRollbackSource(String rollbackSource) {
		this.rollbackSource = rollbackSource;
	}
	
	/**
	 * @return the legacy
	 */
	public boolean getLegacy() {
		return legacy;
	}
	/**
	 * @param legacy the legacy to set
	 */
	public void setLegacy(boolean legacy) {
		this.legacy = legacy;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
    
	 
	
    
}
