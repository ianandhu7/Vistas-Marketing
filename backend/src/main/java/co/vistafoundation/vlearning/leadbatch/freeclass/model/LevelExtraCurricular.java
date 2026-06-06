/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ahmed
 *
 */
@Entity
@Table(name = "LEVEL_EXTRA_CURRICULAR")
public class LevelExtraCurricular {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_LEVEL_EXTRA_CURRICULAR")
	private Long idLevelExtraCurricular;
	@Column(name = "LEVEL")
	private String level;

	/**
	 * @param idLevelExtraCurricular
	 * @param level
	 */
	public LevelExtraCurricular(Long idLevelExtraCurricular, String level) {
		super();
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.level = level;
	}

	/**
	 * 
	 */
	public LevelExtraCurricular() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

}
