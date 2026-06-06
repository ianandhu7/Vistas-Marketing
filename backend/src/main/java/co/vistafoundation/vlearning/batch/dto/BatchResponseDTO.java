/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.specialoffer.dto.SpecialOfferDTO;
import co.vistafoundation.vlearning.subject.model.Subject;

/**
 * @author Naveen Kumar
 *
 */
public class BatchResponseDTO {

	private Long idBatch;

	private ClassStandard classStandard;

	private Subject subject;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private Product product;

	private String teacherName;

	private String teacherExpLevel;

	private int teacherRating;

	private int currentVacancy;

	private int currentOccupancy;

	private String batchName;

	private String meetingDescription;

	private String demoVideoUrl;

	private LocalDate startingDate;

	private LocalDate endDate;

	private Set<SpecialOfferDTO> SpecialOfferDTO;

	private List<BatchScheduleDTO> batchSchedules;
	
	private Long idBatchGroup;
	
	private String batchGroupName;

	/**
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the classStandard
	 */
	public ClassStandard getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(ClassStandard classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return the teacherExpLevel
	 */
	public String getTeacherExpLevel() {
		return teacherExpLevel;
	}

	/**
	 * @param teacherExpLevel the teacherExpLevel to set
	 */
	public void setTeacherExpLevel(String teacherExpLevel) {
		this.teacherExpLevel = teacherExpLevel;
	}

	/**
	 * @return the teacherRating
	 */
	public int getTeacherRating() {
		return teacherRating;
	}

	/**
	 * @param teacherRating the teacherRating to set
	 */
	public void setTeacherRating(int teacherRating) {
		this.teacherRating = teacherRating;
	}

	/**
	 * @return the currentVacancy
	 */
	public int getCurrentVacancy() {
		return currentVacancy;
	}

	/**
	 * @param currentVacancy the currentVacancy to set
	 */
	public void setCurrentVacancy(int currentVacancy) {
		this.currentVacancy = currentVacancy;
	}

	/**
	 * @return the currentOccupancy
	 */
	public int getCurrentOccupancy() {
		return currentOccupancy;
	}

	/**
	 * @param currentOccupancy the currentOccupancy to set
	 */
	public void setCurrentOccupancy(int currentOccupancy) {
		this.currentOccupancy = currentOccupancy;
	}

	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * @return the meetingDescription
	 */
	public String getMeetingDescription() {
		return meetingDescription;
	}

	/**
	 * @param meetingDescription the meetingDescription to set
	 */
	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}

	/**
	 * @return the demoVideoUrl
	 */
	public String getDemoVideoUrl() {
		return demoVideoUrl;
	}

	/**
	 * @param demoVideoUrl the demoVideoUrl to set
	 */
	public void setDemoVideoUrl(String demoVideoUrl) {
		this.demoVideoUrl = demoVideoUrl;
	}

	/**
	 * @return the startingDate
	 */
	public LocalDate getStartingDate() {
		return startingDate;
	}

	/**
	 * @param startingDate the startingDate to set
	 */
	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	

	

	public Set<SpecialOfferDTO> getSpecialOfferDTO() {
		return SpecialOfferDTO;
	}

	public void setSpecialOfferDTO(Set<SpecialOfferDTO> specialOfferDTO) {
		SpecialOfferDTO = specialOfferDTO;
	}

	/**
	 * @return the batchSchedules
	 */
	public List<BatchScheduleDTO> getBatchSchedules() {
		return batchSchedules;
	}

	/**
	 * @param batchSchedules the batchSchedules to set
	 */
	public void setBatchSchedules(List<BatchScheduleDTO> batchSchedules) {
		this.batchSchedules = batchSchedules;
	}
	
	/**
	 * @return the idBatchGroup
	 */
	public Long getIdBatchGroup() {
		return idBatchGroup;
	}

	/**
	 * @return the batchGroupName
	 */
	public String getBatchGroupName() {
		return batchGroupName;
	}

	/**
	 * @param idBatchGroup the idBatchGroup to set
	 */
	public void setIdBatchGroup(Long idBatchGroup) {
		this.idBatchGroup = idBatchGroup;
	}

	/**
	 * @param batchGroupName the batchGroupName to set
	 */
	public void setBatchGroupName(String batchGroupName) {
		this.batchGroupName = batchGroupName;
	}

	public BatchResponseDTO() {
		// TODO Auto-generated constructor stub
	}

}
