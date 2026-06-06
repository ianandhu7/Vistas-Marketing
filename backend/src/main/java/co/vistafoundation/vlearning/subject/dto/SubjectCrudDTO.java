package co.vistafoundation.vlearning.subject.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectCrudDTO {

	   
	    private Long idSubject;

	    @NotEmpty
	    @NotNull
	    @Size(max = 45)
	    private String subjectName;

	    @Size(max = 500)
	    private String videoURL;

	    @NotEmpty
	    @NotNull
	    @Size(max = 100)
	    private String header;

	    @NotEmpty
	    @NotNull
	    @Size(max = 1000)
	    private String description;

	    @NotEmpty
	    @NotNull
	    @Size(max = 100)
	    private String color;

		public SubjectCrudDTO(Long idSubject, @NotEmpty @NotNull @Size(max = 45) String subjectName,
				@Size(max = 500) String videoURL, @NotEmpty @NotNull @Size(max = 100) String header,
				@NotEmpty @NotNull @Size(max = 1000) String description,
				@NotEmpty @NotNull @Size(max = 100) String color) {
			super();
			this.idSubject = idSubject;
			this.subjectName = subjectName;
			this.videoURL = videoURL;
			this.header = header;
			this.description = description;
			this.color = color;
		}

		public SubjectCrudDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Long getIdSubject() {
			return idSubject;
		}

		public void setIdSubject(Long idSubject) {
			this.idSubject = idSubject;
		}

		public String getSubjectName() {
			return subjectName;
		}

		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}

		public String getVideoURL() {
			return videoURL;
		}

		public void setVideoURL(String videoURL) {
			this.videoURL = videoURL;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		
	}
