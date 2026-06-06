package co.vistafoundation.vlearning.videocipher.dto;

/**
 * 
 * @author NaveenKumar
 *
 */
public class VideoPoster {

	private String url;

	private int height;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public VideoPoster(String url, int height) {
		super();
		this.url = url;
		this.height = height;
	}

	public VideoPoster() {
		super();
		// TODO Auto-generated constructor stub
	}

}
