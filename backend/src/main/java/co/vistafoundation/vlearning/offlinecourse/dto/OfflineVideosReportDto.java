package co.vistafoundation.vlearning.offlinecourse.dto;

public class OfflineVideosReportDto {
  public OfflineVideosReportDto(String subName, Long count) {
		super();
		this.subName = subName;
		this.count = count;
	}
String subName;
  Long count;
public String getSubName() {
	return subName;
}
public void setSubName(String subName) {
	this.subName = subName;
}
public Long getCount() {
	return count;
}
public void setCount(Long count) {
	this.count = count;
}
}
