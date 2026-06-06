package co.vistafoundation.vlearning.offlinecourse.dto;

public class FolderDTO {
    private String id;
    private String name;
    private String parent;
    private int videosCount;
    private int foldersCount;

    // Default constructor
    public FolderDTO() {}

    // Parameterized constructor
    public FolderDTO(String id, String name, String parent, int videosCount, int foldersCount) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.videosCount = videosCount;
        this.foldersCount = foldersCount;
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

    public int getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(int videosCount) {
        this.videosCount = videosCount;
    }

    public int getFoldersCount() {
        return foldersCount;
    }

    public void setFoldersCount(int foldersCount) {
        this.foldersCount = foldersCount;
    }

    @Override
    public String toString() {
        return "FolderDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", videosCount=" + videosCount +
                ", foldersCount=" + foldersCount +
                '}';
    }
}

