/**
 * 
 */
package co.vistafoundation.vlearning.blogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.blogs.model.BlogComment;

/**
 * @author vk
 *
 */
public interface BlogCommentRepository extends JpaRepository <BlogComment,Long> {
	
	public List<BlogComment> findByIdBlogOrderByIdBlogCommentDesc(Long idBlog);
	
	public List<BlogComment> findByIdBlog(Long idBlog);
	
	public BlogComment findByIdBlogComment(Long idBlogComment);

	public BlogComment findByIdBlogCommentAndUserSurId(Long idBlogComment, Long userSurId);

}
