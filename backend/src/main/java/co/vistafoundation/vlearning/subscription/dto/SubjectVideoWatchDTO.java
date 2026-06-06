package co.vistafoundation.vlearning.subscription.dto;

public class SubjectVideoWatchDTO {

	
	    private String subjectName;
	    private Long totalWatchCount;
	    private Long totalWatchHours;

	    // Constructor
	    public SubjectVideoWatchDTO(String subjectName, Long totalWatchCount, Long totalWatchHours) {
	        this.subjectName = subjectName;
	        this.totalWatchCount = totalWatchCount;
	        this.totalWatchHours = totalWatchHours;
	    }

	    // Getters and Setters
	    public String getSubjectName() {
	        return subjectName;
	    }

	    public void setSubjectName(String subjectName) {
	        this.subjectName = subjectName;
	    }

	    public Long getTotalWatchCount() {
	        return totalWatchCount;
	    }

	    public void setTotalWatchCount(Long totalWatchCount) {
	        this.totalWatchCount = totalWatchCount;
	    }

	    public String getTotalWatchHours() {
	    
	        long hours = totalWatchHours / 3600;
	        long minutes = (totalWatchHours % 3600) / 60;
	     
	        return hours + "h " + minutes + "m ";
	    }

	    public void setTotalWatchHours(Long totalWatchHours) {
	        this.totalWatchHours = totalWatchHours;
	    }

	  

}
