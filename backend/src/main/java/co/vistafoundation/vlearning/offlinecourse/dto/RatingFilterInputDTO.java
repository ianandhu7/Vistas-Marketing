package co.vistafoundation.vlearning.offlinecourse.dto;
import java.util.Date;

public class RatingFilterInputDTO {

    private Long userSurId;
    private Long idClassStandard;
    private Date dateFrom;
    private Date dateTo;
    private Integer ratings;
    private Long idSubject;
    
	private int page;
	
	private int size;

    /**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public RatingFilterInputDTO() {
        // Default constructor
    }

    public RatingFilterInputDTO(Long userSurId, Long idClassStandard, Date dateFrom, Date dateTo, Integer ratings, Long idSubject) {
        this.userSurId = userSurId;
        this.idClassStandard = idClassStandard;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.ratings = ratings;
        this.idSubject = idSubject;
    }

    public Long getUserSurId() {
        return userSurId;
    }

    public void setUserSurId(Long userSurId) {
        this.userSurId = userSurId;
    }

    public Long getIdClassStandard() {
        return idClassStandard;
    }

    public void setIdClassStandard(Long idClassStandard) {
        this.idClassStandard = idClassStandard;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public Long getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }

	@Override
	public String toString() {
		return "RatingFilterInputDTO [userSurId=" + userSurId + ", idClassStandard=" + idClassStandard + ", dateFrom="
				+ dateFrom + ", dateTo=" + dateTo + ", ratings=" + ratings + ", idSubject=" + idSubject + ", page="
				+ page + ", size=" + size + "]";
	}
      
}
