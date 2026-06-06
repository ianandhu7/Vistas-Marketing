/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STATE")
public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTATE")
	private Long idState;

	@Column(name = "STATE")
	private String state;
	
	@Column(name = "DISPLAY_ORDER")
	private Long displayOrder;

	

	/**
	 * 
	 */
	public State() {
		super();
	}

	/**
	 * @param state
	 */
	public State(String state) {
		super();
		this.state = state;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	public Long getDisplay_order() {
		return displayOrder;
	}

	public void setDisplay_order(Long display_order) {
		this.displayOrder = display_order;
	}

}
