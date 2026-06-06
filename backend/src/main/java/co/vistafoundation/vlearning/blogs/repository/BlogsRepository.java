package co.vistafoundation.vlearning.blogs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.blogs.model.Blogs;

public interface BlogsRepository extends JpaRepository <Blogs,Long> {
	
	public List<Blogs> findAllByPublisherNameContainingOrBlogTitleContainingOrTagsContainingAndPublishDateBeforeAndHideFlag(String query,String query1,String query2, LocalDate currentDate,Boolean isHidden );
	
	public List<Blogs> findAllByIdBlogCategory(long idBlogCategory);
	
	public List<Blogs> findAllByIdBlogCategoryAndHideFlag(long idBlogCategory, Boolean hideFlag);

	public List<Blogs> findByIdBlogCategoryIn(List<Long> temp3);
	
	public List<Blogs> findAllByHideFlag(Boolean flag);
	
	public Blogs findByIdBlog(Long idBlog);
	
	@Query("SELECT b FROM Blogs b, BlogsCategory bc \r\n" + 
			"WHERE (b.idBlogCategory=bc.idBlogCategory) \r\n" + 
			"AND b.hideFlag=:hideFlag \r\n" +
			"AND bc.activeFlag=TRUE \r\n" +
			"AND b.idBlog=:idBlog \r\n")
	public Blogs findByIdBlogAndHideFlag(Long idBlog, Boolean hideFlag);
	
	@Query("SELECT b FROM Blogs b, BlogsCategory bc \r\n" + 
			"WHERE (b.idBlogCategory=bc.idBlogCategory) \r\n" + 
			"AND b.hideFlag=FALSE \r\n" +
			"AND bc.activeFlag=TRUE \r\n")
	public List<Blogs> findAllByOrderByNumClickDesc();

	
	@Query("SELECT b FROM Blogs b, BlogsCategory bc \r\n" + 
			"WHERE (b.idBlogCategory=bc.idBlogCategory) \r\n" + 
			"AND b.hideFlag=:hideFlag \r\n" +
			"AND bc.activeFlag=TRUE \r\n" +
			"AND b.slugURL=:slugURL \r\n")
	public Blogs findByslugURLAndHideFlag(String slugURL, Boolean hideFlag);

	public Blogs findByslugURL(String lowerCase);

	public Page<Blogs> findAllByIdBlogCategoryAndHideFlag(Long idBlogCategory, Boolean flag, Pageable paging);

}
