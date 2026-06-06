package co.vistafoundation.vlearning.videocipher.dto;

/**
 * 
 * @author NaveenKumar
 *
 */
public class VideoCipherOTP {

	private String otp;

	private String playbackInfo;

	private String s3Path;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPlaybackInfo() {
		return playbackInfo;
	}

	public void setPlaybackInfo(String playbackInfo) {
		this.playbackInfo = playbackInfo;
	}

	public String getS3Path() {
		return s3Path;
	}

	public void setS3Path(String s3Path) {
		this.s3Path = s3Path;
	}

}
