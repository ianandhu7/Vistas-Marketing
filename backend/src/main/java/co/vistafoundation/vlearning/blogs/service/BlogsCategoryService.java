package co.vistafoundation.vlearning.blogs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.blogs.dto.BlogsCategoryDto;
import co.vistafoundation.vlearning.blogs.model.BlogsCategory;
import co.vistafoundation.vlearning.common.response.Document;

public interface BlogsCategoryService {
	
	@SuppressWarnings("rawtypes")
	public Document addBlogCategory(BlogsCategoryDto blogsCatdto);
	
	@SuppressWarnings("rawtypes")
	public Document getAllBlogCategory();
	
	@SuppressWarnings("rawtypes")
	public Document uploadBlogCategoryThumnail(MultipartFile blogCatThumnail);

	@SuppressWarnings("rawtypes")
	public Document updateBlogCategory(BlogsCategoryDto blogsCatdto);

	@SuppressWarnings("rawtypes")
	public Document updateActiveFlag(Long idBlogCategory, Boolean flag);

	@SuppressWarnings("rawtypes")
	public Document getAllActiveBlogCategories();

	Document<List<BlogsCategory>> getAllActiveBlogCategory();

	Document<BlogsCategory> getBlogCategory(Long idBlogCategory);

	

}
