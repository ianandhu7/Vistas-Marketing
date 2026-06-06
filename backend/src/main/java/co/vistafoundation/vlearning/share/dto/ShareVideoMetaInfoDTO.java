package co.vistafoundation.vlearning.share.dto;

public class ShareVideoMetaInfoDTO {

	private String title;

	private String subTitlle;

	private String desc;

	private String thumbnail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitlle() {
		return subTitlle;
	}

	public void setSubTitlle(String subTitlle) {
		this.subTitlle = subTitlle;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ShareVideoMetaInfoDTO(String title, String subTitlle, String desc, String thumbnail) {

		super();
		this.title = title;
		this.subTitlle = subTitlle;
		this.desc = desc;
		this.thumbnail = thumbnail;
	}

	public ShareVideoMetaInfoDTO() {
		super();
	}

}
