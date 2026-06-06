package co.vistafoundation.vlearning.specialoffer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Mohan Kumar 
 * 
 **/
@Entity
@Table(name = "COUPON_USER_MAP")
public class CouponUserMap  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677165496902039327L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_COUPON_USER_MAP")
	private Long idCouponUserMap;

	@Column(name = "ID_COUPON")
	private Long idCoupon;

	@Column(name = "ID_VL_USER")
	private Long idVlUser;
	
	

	public CouponUserMap() {
		
	}

	public CouponUserMap(Long idCouponUserMap, Long idCoupon, Long idVlUser) {
		super();
		this.idCouponUserMap = idCouponUserMap;
		this.idCoupon = idCoupon;
		this.idVlUser = idVlUser;
	}
	

	public Long getIdCouponUserMap() {
		return idCouponUserMap;
	}

	public void setIdCouponUserMap(Long idCouponUserMap) {
		this.idCouponUserMap = idCouponUserMap;
	}

	public Long getIdCoupon() {
		return idCoupon;
	}

	public void setIdCoupon(Long idCoupon) {
		this.idCoupon = idCoupon;
	}

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}
	
	
}
