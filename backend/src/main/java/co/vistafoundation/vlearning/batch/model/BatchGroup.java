/**
 * 
 */
package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NAVEEN
 *
 */
@Entity
@Table(name = "BATCH_GROUP")
public class BatchGroup extends UserDateAudit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idBATCH_GROUP", nullable = false)
	private Long idBatchGroup;
	
	
	@Column(name = "BATCH_GROUP_NAME",unique=true)
	private String batchGroupName;


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


	/**
	 * @return the batchGroupName
	 */
	public String getBatchGroupName() {
		return batchGroupName;
	}


	/**
	 * @param batchGroupName the batchGroupName to set
	 */
	public void setBatchGroupName(String batchGroupName) {
		this.batchGroupName = batchGroupName;
	}


	/**
	 * @param batchGroupName
	 */
	public BatchGroup(String batchGroupName) {
		super();
		this.batchGroupName = batchGroupName;
	}


	/**
	 * 
	 */
	public BatchGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
