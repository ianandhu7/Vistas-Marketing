package co.vistafoundation.vlearning.discover.videos.dto;

import java.util.List;

import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;

public class DiscoverVideoSearchDTO {

	private int searchCount;
	private List<DiscoverVideo> searchList;
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public List<DiscoverVideo> getSearchList() {
		return searchList;
	}
	public void setSearchList(List<DiscoverVideo> searchList) {
		this.searchList = searchList;
	} 
}
