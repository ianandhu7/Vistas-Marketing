package co.vistafoundation.vlearning.firebase.config.dto;

import java.util.List;


public class PageDto {

	
	private List<String> pageList;
	private int size;

	
	/**
	 * @return the pageList
	 */
	public List<String> getPageList() {
		return pageList;
	}
	/**
	 * @param pageList the pageList to set
	 */
	public void setPageList(List<String> pageList) {
		this.pageList = pageList;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
