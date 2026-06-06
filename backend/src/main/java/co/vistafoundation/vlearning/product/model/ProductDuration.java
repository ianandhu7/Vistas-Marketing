/**
 * 
 */
package co.vistafoundation.vlearning.product.model;

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
 */
@Entity
@Table(name = "PRODUCT_DURATION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ProductDuration extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT_DURATION", nullable = false)
	private Long idProductDuration;

	@Column(name = "DURATION", nullable = false,unique = true)
	private Integer duration;

	@Column(name = "DURATION_NAME", length = 50, nullable = false)
	private String durationName;

	@Column(name = "DURATION_CODE", length = 50, nullable = false)
	private String durationCode;

	public ProductDuration() {
	}

	/**
	 * @return the idProductDuration
	 */
	public Long getIdProductDuration() {
		return idProductDuration;
	}

	/**
	 * @param idProductDuration the idProductDuration to set
	 */
	public void setIdProductDuration(Long idProductDuration) {
		this.idProductDuration = idProductDuration;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the durationName
	 */
	public String getDurationName() {
		return durationName;
	}

	/**
	 * @paramdurationName the durationName to set
	 */
	public void setDurationName(String durationName) {
		this.durationName = durationName;
	}

	/**
	 * @return the durationCode
	 */
	public String getDurationCode() {
		return durationCode;
	}

	public void setDurationCode(String durationCode) {
		this.durationCode = durationCode;
	}

	public ProductDuration(Integer duration, String durationName, String durationCode) {
		super();
		this.duration = duration;
		this.durationName = durationName;
		this.durationCode = durationCode;
	}

}
