package co.vistafoundation.vlearning.batch.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.batch.model.Batch;

/**
 * @author NaveenKumar
 * 
 **/
public class CreateBatchRequestDTO {

	@NotNull
	private Long idProductLine;

	@NotNull
	private Long idTeacher;

	@NotNull
	private Long idClassStandard;

	@NotNull
	private Long idSubject;

	private Long idDayofWeekCode;

	@NotNull
	private Batch batch;
	
	@NotNull
	private Long idSyllabus;
	
	@NotNull
	private Long idState;
	
	@NotNull
	private String category;
	
	private Long idLevelExtraCurricular;
	
	@NotNull
	private List<BatchScheduleDTO> scheduleList;
	
	
	private List<Long> idSpecialOffers = new ArrayList<Long>();
	

	private Long idBatchGroup;
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public Long getIdProductLine() {
		return idProductLine;
	}

	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Long getIdDayofWeekCode() {
		return idDayofWeekCode;
	}

	public void setIdDayofWeekCode(Long idDayofWeekCode) {
		this.idDayofWeekCode = idDayofWeekCode;
	}

	public CreateBatchRequestDTO() {
		super();
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	
	

	/**
	 * @return the scheduleList
	 */
	public List<BatchScheduleDTO> getScheduleList() {
		return scheduleList;
	}

	/**
	 * @param scheduleList the scheduleList to set
	 */
	public void setScheduleList(List<BatchScheduleDTO> scheduleList) {
		this.scheduleList = scheduleList;
	}


	/**
	 * @return the idLevelExtraCurricular
	 */
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}

	/**
	 * @param idLevelExtraCurricular the idLevelExtraCurricular to set
	 */
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
	}
	
	

	/**
	 * @param idTeacher
	 * @param idClassStandard
	 * @param idSubject
	 * @param idDayofWeekCode
	 * @param batch
	 * @param idSyllabus
	 * @param idState
	 * @param category
	 * @param idLevelExtraCurricular
	 * @param scheduleList
	 */
	public CreateBatchRequestDTO(@NotNull Long idTeacher, @NotNull Long idClassStandard, @NotNull Long idSubject,
			@NotNull Long idDayofWeekCode, @NotNull Batch batch, @NotNull Long idSyllabus, @NotNull Long idState,
			@NotNull String category, Long idLevelExtraCurricular, @NotNull List<BatchScheduleDTO> scheduleList) {
		super();
		this.idTeacher = idTeacher;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idDayofWeekCode = idDayofWeekCode;
		this.batch = batch;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.category = category;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.scheduleList = scheduleList;
	}

	/**
	 * @return the idSpecialOffers
	 */
	public List<Long> getIdSpecialOffers() {
		return idSpecialOffers;
	}

	/**
	 * @param idSpecialOffers the idSpecialOffers to set
	 */
	public void setIdSpecialOffers(List<Long> idSpecialOffers) {
		this.idSpecialOffers = idSpecialOffers;
	}

	/**
	 * @return the idBatchGroup
	 */
	public Long getIdBatchGroup() {
		return idBatchGroup;
	}

	/**
	 * @param idBatchGroup the idBatchGroup to set
	 */
	public void setIdBatchGroup(Long idBatchGroup) {
		this.idBatchGroup = idBatchGroup;
	}

	

}
