package co.vistafoundation.vlearning.blogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.blogs.model.BlogsCategory;

public interface BlogsCategoryRepository extends JpaRepository <BlogsCategory,Long>  {
	
	public List<BlogsCategory> findAllByCategoryNameContainingAndActiveFlag(String query, Boolean activeFlag);
	
	public BlogsCategory findByIdBlogCategory(Long idBlogCategory);
	
	public BlogsCategory findByIdBlogCategoryAndActiveFlag(Long idBlogCategory, Boolean activeFlag);
	
	public List<BlogsCategory> findAllByActiveFlag(Boolean flag);
	
	public List<BlogsCategory> findByIdBlogCategoryIn(List<Long> temp3);
	
	@Query(value="SELECT DISTINCT(bc.idBlogCategory) from BlogsCategory bc inner join Blogs b on bc.idBlogCategory=b.idBlogCategory where bc.activeFlag=:flag and b.hideFlag=false")
	public List<Long> findAllByActiveFlagFromBlog(Boolean flag);

}
