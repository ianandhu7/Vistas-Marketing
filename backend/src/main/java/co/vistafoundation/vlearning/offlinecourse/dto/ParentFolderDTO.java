package co.vistafoundation.vlearning.offlinecourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentFolderDTO {
    private String id;
    private String name;
    private String parent;
    private int user;
    private String  createdAt;
    private String updatedAt;
    private String folderPath;
    @JsonProperty("size_in_bytes")
    private long sizeInBytes;
    
    // Default constructor
    public ParentFolderDTO() {}

    // Parameterized constructor
    public ParentFolderDTO(String id, String name, String parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    
    
    
    /**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}

	
	
	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

	/**
	 * @return the folderPath
	 */
	public String getFolderPath() {
		return folderPath;
	}

	/**
	 * @param folderPath the folderPath to set
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * @return the sizeInBytes
	 */
	public long getSizeInBytes() {
		return sizeInBytes;
	}

	/**
	 * @param sizeInBytes the sizeInBytes to set
	 */
	public void setSizeInBytes(long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	@Override
    public String toString() {
        return "ParentFolderDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                '}';
    }
}

