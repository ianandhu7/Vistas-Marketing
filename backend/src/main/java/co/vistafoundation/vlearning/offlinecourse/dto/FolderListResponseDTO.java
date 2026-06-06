package co.vistafoundation.vlearning.offlinecourse.dto;

import java.util.List;

public class FolderListResponseDTO {
    private List<FolderDTO> folderList;
    private CurrentFolderDTO current;
    private ParentFolderDTO parent;

    // Default constructor
    public FolderListResponseDTO() {}

    // Parameterized constructor
    public FolderListResponseDTO(List<FolderDTO> folderList, CurrentFolderDTO current, ParentFolderDTO parent) {
        this.folderList = folderList;
        this.current = current;
        this.parent = parent;
    }

    // Getters and Setters
    public List<FolderDTO> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<FolderDTO> folderList) {
        this.folderList = folderList;
    }

    public CurrentFolderDTO getCurrent() {
        return current;
    }

    public void setCurrent(CurrentFolderDTO current) {
        this.current = current;
    }

    public ParentFolderDTO getParent() {
        return parent;
    }

    public void setParent(ParentFolderDTO parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "FolderListResponseDTO{" +
                "folderList=" + folderList +
                ", current=" + current +
                ", parent=" + parent +
                '}';
    }
}

