package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * 
 * @author Naveen Kumar
 *
 */

@Entity
@Table(name = "LOCATION")
public class Location  extends UserDateAudit implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLOCATION", nullable = false)
	private Long idLocation;
	
	@Column(name = "CITY_NAME", nullable =false ,length=200)
	private String cityName;
	
	@Column(name = "GEO_LOCATION", nullable =false ,length=100)
	private String geoLocation;

	/**
	 * @return the idLocation
	 */
	public Long getIdLocation() {
		return idLocation;
	}

	/**
	 * @param idLocation the idLocation to set
	 */
	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	/**
	 * @return the name
	 */
	
	/**
	 * @return the geoLocation
	 */
	public String getGeoLocation() {
		return geoLocation;
	}

	/**
	 * @param geoLocation the geoLocation to set
	 */
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * @param name
	 * @param geoLocation
	 */
	public Location(String name, String geoLocation) {
		super();
		this.cityName = name;
		this.geoLocation = geoLocation;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 
	 */
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
  
	


}
