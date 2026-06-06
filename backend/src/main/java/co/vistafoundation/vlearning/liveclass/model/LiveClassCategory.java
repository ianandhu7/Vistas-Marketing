/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "LIVE_CLASS_CATEGORY")
public class LiveClassCategory extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idLIVE_CLASS_CATEGORY")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLiveClassCategory;
	
	@Column(name = "LIVE_CLASS_CATEGORY_CD")
	private String liveClassCategoryCd;
	
	@Column(name = "LIVE_CLASS_CATEGORY_NAME")
	private String liveClassCategoryName;

	/**
	 * 
	 */
	public LiveClassCategory() {
		super();
	}

	/**
	 * @param liveClassCategoryCd
	 * @param liveClassCategoryName
	 */
	public LiveClassCategory(String liveClassCategoryCd, String liveClassCategoryName) {
		super();
		this.liveClassCategoryCd = liveClassCategoryCd;
		this.liveClassCategoryName = liveClassCategoryName;
	}

	/**
	 * @return the idLiveClassCategory
	 */
	public Long getIdLiveClassCategory() {
		return idLiveClassCategory;
	}

	/**
	 * @param idLiveClassCategory the idLiveClassCategory to set
	 */
	public void setIdLiveClassCategory(Long idLiveClassCategory) {
		this.idLiveClassCategory = idLiveClassCategory;
	}

	/**
	 * @return the liveClassCategoryCd
	 */
	public String getLiveClassCategoryCd() {
		return liveClassCategoryCd;
	}

	/**
	 * @param liveClassCategoryCd the liveClassCategoryCd to set
	 */
	public void setLiveClassCategoryCd(String liveClassCategoryCd) {
		this.liveClassCategoryCd = liveClassCategoryCd;
	}

	/**
	 * @return the liveClassCategoryName
	 */
	public String getLiveClassCategoryName() {
		return liveClassCategoryName;
	}

	/**
	 * @param liveClassCategoryName the liveClassCategoryName to set
	 */
	public void setLiveClassCategoryName(String liveClassCategoryName) {
		this.liveClassCategoryName = liveClassCategoryName;
	}
	
}
