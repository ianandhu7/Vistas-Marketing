package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {
	
	

	private static final long serialVersionUID = -3759055812371565507L;
	private String cardType;
	private String color;
	private String heading;
	private String subHeading;
	private String text;
	private String formattedText;
	private String imgUrl;
	private List<List<Object>>listData;
	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}
	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	/**
	 * @return the subHeading
	 */
	public String getSubHeading() {
		return subHeading;
	}
	/**
	 * @param subHeading the subHeading to set
	 */
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the formattedText
	 */
	public String getFormattedText() {
		return formattedText;
	}
	/**
	 * @param formattedText the formattedText to set
	 */
	public void setFormattedText(String formattedText) {
		this.formattedText = formattedText;
	}
	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * @return the listData
	 */
	public List<List<Object>> getListData() {
		return listData;
	}
	/**
	 * @param listData the listData to set
	 */
	public void setListData(List<List<Object>> listData) {
		this.listData = listData;
	}



}
