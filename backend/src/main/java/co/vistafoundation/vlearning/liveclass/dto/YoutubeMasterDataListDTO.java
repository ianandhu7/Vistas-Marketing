/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.dto;

import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class YoutubeMasterDataListDTO {

	private Long idYoutubeMaster;
	private String youtubeUserId;
	private Teacher teacher;

	/**
	 * @param idYoutubeMaster
	 * @param youtubeUserId
	 * @param teacher
	 */
	public YoutubeMasterDataListDTO(Long idYoutubeMaster, String youtubeUserId, Teacher teacher) {
		super();
		this.idYoutubeMaster = idYoutubeMaster;
		this.youtubeUserId = youtubeUserId;
		this.teacher = teacher;
	}

	/**
	 * 
	 */
	public YoutubeMasterDataListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idYoutubeMaster
	 */
	public Long getIdYoutubeMaster() {
		return idYoutubeMaster;
	}

	/**
	 * @param idYoutubeMaster the idYoutubeMaster to set
	 */
	public void setIdYoutubeMaster(Long idYoutubeMaster) {
		this.idYoutubeMaster = idYoutubeMaster;
	}

	/**
	 * @return the youtubeUserId
	 */
	public String getYoutubeUserId() {
		return youtubeUserId;
	}

	/**
	 * @param youtubeUserId the youtubeUserId to set
	 */
	public void setYoutubeUserId(String youtubeUserId) {
		this.youtubeUserId = youtubeUserId;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
