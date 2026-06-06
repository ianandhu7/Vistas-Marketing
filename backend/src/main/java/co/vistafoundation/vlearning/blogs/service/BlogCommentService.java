/**
 * 
 */
package co.vistafoundation.vlearning.blogs.service;

import co.vistafoundation.vlearning.blogs.dto.BlogCommentDTO;
import co.vistafoundation.vlearning.blogs.dto.BlogCommentEditDTO;
import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author vk
 *
 */
public interface BlogCommentService {
	
	@SuppressWarnings("rawtypes")
	public Document addBlogComment(BlogCommentDTO blogCommentDTO);
	
	@SuppressWarnings("rawtypes")
	public Document editBlogComment(BlogCommentEditDTO blogCommentEditDTO);
	
	@SuppressWarnings("rawtypes")
	public Document deleteBlogComment(Long idBlogComment);
	
	@SuppressWarnings("rawtypes")
	public Document getBlogCommentByIdBlog(Long idBlog);

}
