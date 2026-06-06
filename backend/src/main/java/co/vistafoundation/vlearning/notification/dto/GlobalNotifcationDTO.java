/**
 * 
 */
package co.vistafoundation.vlearning.notification.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author NaveenKumar
 *
 */
public class GlobalNotifcationDTO {

	
	@NotBlank
	@NotEmpty
	@NotNull
	private String heading;

	@NotBlank
	@NotEmpty
	@NotNull
	private String message;
	
	
	private String user;
	
	private String imageURL;
	

	private Boolean emailFlag = false;

	private Boolean firebaseFlag = false;

	private Boolean inAppFlag = false;

	private List<Long> userSurIds  = new ArrayList<>() ;

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Boolean getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(Boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public Boolean getFirebaseFlag() {
		return firebaseFlag;
	}

	public void setFirebaseFlag(Boolean firebaseFlag) {
		this.firebaseFlag = firebaseFlag;
	}

	public Boolean getInAppFlag() {
		return inAppFlag;
	}

	public void setInAppFlag(Boolean inAppFlag) {
		this.inAppFlag = inAppFlag;
	}

	
	public List<Long> getUserSurIds() {
		return userSurIds;
	}

	public void setUserSurIds(List<Long> userSurIds) {
		this.userSurIds = userSurIds;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	

	public GlobalNotifcationDTO(@NotBlank @NotEmpty @NotNull String heading,
			@NotBlank @NotEmpty @NotNull String message, String user, String imageURL, Boolean emailFlag,
			Boolean firebaseFlag, Boolean inAppFlag, List<Long> userSurIds) {
		super();
		this.heading = heading;
		this.message = message;
		this.user = user;
		this.imageURL = imageURL;
		this.emailFlag = emailFlag;
		this.firebaseFlag = firebaseFlag;
		this.inAppFlag = inAppFlag;
		this.userSurIds = userSurIds;
	}

	public GlobalNotifcationDTO(@NotBlank @NotEmpty @NotNull String heading,
			@NotBlank @NotEmpty @NotNull String message,String user, Boolean emailFlag,
			Boolean firebaseFlag, Boolean inAppFlag, List<Long> userSurIds) {
		super();
		this.heading = heading;
		this.message = message;
		this.user = user;
		this.emailFlag = emailFlag;
		this.firebaseFlag = firebaseFlag;
		this.inAppFlag = inAppFlag;
		this.userSurIds = userSurIds;
	}

	public GlobalNotifcationDTO() {
		// TODO Auto-generated constructor stub
	}

}
