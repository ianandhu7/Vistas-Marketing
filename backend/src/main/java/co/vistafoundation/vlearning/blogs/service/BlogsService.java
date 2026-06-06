package co.vistafoundation.vlearning.blogs.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;


import co.vistafoundation.vlearning.blogs.dto.BlogsDto;
import co.vistafoundation.vlearning.blogs.model.Quote;
import co.vistafoundation.vlearning.common.response.Document;

public interface BlogsService {

	@SuppressWarnings("rawtypes")
	public Document searchBlogs(String searchQuery);
	
	public String uploadBlogContent(String fileContent);
	
	@SuppressWarnings("rawtypes")
	public Document uploadBlogThumnail(MultipartFile blogThumnail);
	
	@SuppressWarnings("rawtypes")
	public Document uploadBlogSubjectImage(MultipartFile blogSubjectImage);
	
	@SuppressWarnings("rawtypes")
	public Document uploadBlogContentImage(MultipartFile blogContentImage);

	@SuppressWarnings("rawtypes")
	public Document addBlog(BlogsDto blogDto);
	
	@SuppressWarnings("rawtypes")
	public Document getAllBlogs();
	
	@SuppressWarnings("rawtypes")
	public Document getAllActiveBlogs();

	@SuppressWarnings("rawtypes")
	public Document updateBlog(@Valid BlogsDto blogDto);

	@SuppressWarnings("rawtypes")
	public Document updateHideFlag(Long idBlog, Boolean flag);

	public Document<?> getBlogsByCategory(Long idBlogCategory,int pageNumber);
	
	@SuppressWarnings("rawtypes")
	public Document getFeaturedBlogs();
	
	@SuppressWarnings("rawtypes")
	public Document getBlogByIdBlog(Long idBlog);
	
	@SuppressWarnings("rawtypes")
	public Document updateClicks(Long idBlog);
	
	public Document<?> getBlogBySlugURL(String slugURL);
	
	public Document<Boolean> validateSlugURL(String slugURL);
	
	public  Document<List<Quote>> getAllQuotes();

}
