package co.vistafoundation.vlearning.videocipher.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author NaveenKumar
 *
 */
public class VideoCipherMeta {

	private String title;

	private String description;

	private int duration;

	private float aspectRatio;

	private Set<String> hostnames = new HashSet<String>();

	private String embedUrl;

	private Set<String> tech = new HashSet<String>();

	private VideoCipherDASH dash;

	private VideoCipherCBCS cbcs;

	private List<VideoPoster> posters = new ArrayList<VideoPoster>();

	private List<Object> captions = new ArrayList<Object>();
	
	private List<Object> chapters = new ArrayList<Object>();
	
	private List<Object> ctas = new ArrayList<Object>();
	
	private Boolean vdocipherBranding;
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the aspectRatio
	 */
	public float getAspectRatio() {
		return aspectRatio;
	}

	/**
	 * @param aspectRatio the aspectRatio to set
	 */
	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	/**
	 * @return the hostnames
	 */
	public Set<String> getHostnames() {
		return hostnames;
	}

	/**
	 * @param hostnames the hostnames to set
	 */
	public void setHostnames(Set<String> hostnames) {
		this.hostnames = hostnames;
	}

	/**
	 * @return the embedUrl
	 */
	public String getEmbedUrl() {
		return embedUrl;
	}

	/**
	 * @param embedUrl the embedUrl to set
	 */
	public void setEmbedUrl(String embedUrl) {
		this.embedUrl = embedUrl;
	}

	/**
	 * @return the tech
	 */
	public Set<String> getTech() {
		return tech;
	}

	/**
	 * @param tech the tech to set
	 */
	public void setTech(Set<String> tech) {
		this.tech = tech;
	}

	/**
	 * @return the dash
	 */
	public VideoCipherDASH getDash() {
		return dash;
	}

	/**
	 * @param dash the dash to set
	 */
	public void setDash(VideoCipherDASH dash) {
		this.dash = dash;
	}

	/**
	 * @return the cbcs
	 */
	public VideoCipherCBCS getCbcs() {
		return cbcs;
	}

	/**
	 * @param cbcs the cbcs to set
	 */
	public void setCbcs(VideoCipherCBCS cbcs) {
		this.cbcs = cbcs;
	}

	/**
	 * @return the posters
	 */
	public List<VideoPoster> getPosters() {
		return posters;
	}

	/**
	 * @param posters the posters to set
	 */
	public void setPosters(List<VideoPoster> posters) {
		this.posters = posters;
	}

	/**
	 * @return the captions
	 */
	public List<Object> getCaptions() {
		return captions;
	}

	/**
	 * @param captions the captions to set
	 */
	public void setCaptions(List<Object> captions) {
		this.captions = captions;
	}
	
	

	/**
	 * @return the chapters
	 */
	public List<Object> getChapters() {
		return chapters;
	}

	/**
	 * @param chapters the chapters to set
	 */
	public void setChapters(List<Object> chapters) {
		this.chapters = chapters;
	}

	/**
	 * @return the ctas
	 */
	public List<Object> getCtas() {
		return ctas;
	}

	/**
	 * @param ctas the ctas to set
	 */
	public void setCtas(List<Object> ctas) {
		this.ctas = ctas;
	}

	/**
	 * @param title
	 * @param description
	 * @param duration
	 * @param aspectRatio
	 * @param hostnames
	 * @param embedUrl
	 * @param tech
	 * @param dash
	 * @param cbcs
	 * @param posters
	 * @param captions
	 */
	public VideoCipherMeta(String title, String description, int duration, float aspectRatio, Set<String> hostnames,
			String embedUrl, Set<String> tech, VideoCipherDASH dash, VideoCipherCBCS cbcs, List<VideoPoster> posters,
			List<Object> captions) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.aspectRatio = aspectRatio;
		this.hostnames = hostnames;
		this.embedUrl = embedUrl;
		this.tech = tech;
		this.dash = dash;
		this.cbcs = cbcs;
		this.posters = posters;
		this.captions = captions;
	}
	
	

	/**
	 * @param title
	 * @param description
	 * @param duration
	 * @param aspectRatio
	 * @param hostnames
	 * @param embedUrl
	 * @param tech
	 * @param dash
	 * @param cbcs
	 * @param posters
	 * @param captions
	 * @param chapters
	 * @param ctas
	 */
	public VideoCipherMeta(String title, String description, int duration, float aspectRatio, Set<String> hostnames,
			String embedUrl, Set<String> tech, VideoCipherDASH dash, VideoCipherCBCS cbcs, List<VideoPoster> posters,
			List<Object> captions, List<Object> chapters, List<Object> ctas) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.aspectRatio = aspectRatio;
		this.hostnames = hostnames;
		this.embedUrl = embedUrl;
		this.tech = tech;
		this.dash = dash;
		this.cbcs = cbcs;
		this.posters = posters;
		this.captions = captions;
		this.chapters = chapters;
		this.ctas = ctas;
	}

	/**
	 * 
	 */
	public VideoCipherMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the vdocipherBranding
	 */
	public Boolean getVdocipherBranding() {
		return vdocipherBranding;
	}

	/**
	 * @param vdocipherBranding the vdocipherBranding to set
	 */
	public void setVdocipherBranding(Boolean vdocipherBranding) {
		this.vdocipherBranding = vdocipherBranding;
	}
	
	
	

}
