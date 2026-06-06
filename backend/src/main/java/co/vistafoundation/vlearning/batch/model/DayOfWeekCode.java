package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "DAY_OF_WEEK_CODE")
@JsonIgnoreProperties({"updatedBy", "createdBy","createdAt","updatedAt"})
public class DayOfWeekCode extends UserDateAudit implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDAY_OF_WEEK_CODE", nullable = false)
	private Long idDayofWeekCode;
	

	@Column(name = "DAY_OF_WEEK_CD", length = 45)
	private String dayOfWeekCd;


	public Long getIdDayofWeekCode() {
		return idDayofWeekCode;
	}


	public void setIdDayofWeekCode(Long idDayofWeekCode) {
		this.idDayofWeekCode = idDayofWeekCode;
	}


	public String getDayOfWeekCd() {
		return dayOfWeekCd;
	}


	public void setDayOfWeekCd(String dayOfWeekCd) {
		this.dayOfWeekCd = dayOfWeekCd;
	}


	public DayOfWeekCode(String dayOfWeekCd) {
		super();
		this.dayOfWeekCd = dayOfWeekCd;
	}


	public DayOfWeekCode() {
		super();
		
	}
	
	
	

}
