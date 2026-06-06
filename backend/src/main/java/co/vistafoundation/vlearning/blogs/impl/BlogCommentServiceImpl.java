/**
 * 
 */
package co.vistafoundation.vlearning.blogs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.blogs.dto.BlogCommentDTO;
import co.vistafoundation.vlearning.blogs.dto.BlogCommentEditDTO;
import co.vistafoundation.vlearning.blogs.model.BlogComment;
import co.vistafoundation.vlearning.blogs.repository.BlogCommentRepository;
import co.vistafoundation.vlearning.blogs.service.BlogCommentService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;

/**
 * @author vk
 *
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {

	@Autowired
	BlogCommentRepository blogCommentRepository;

	@Autowired
	UserRepository userRepository;

	@Override

	public Document<?> addBlogComment(BlogCommentDTO blogCommentDTO) {
		Document<BlogComment> result = new Document<>();
		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!blogCommentDTO.getUserSurId().equals(loggedInUser.getUserSurId())) {
				result.setData(null);
				result.setMessage("User dosent have access to add this content.");
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				return result;

			}

			BlogComment blogsComment = new BlogComment();
			blogsComment.setIdBlog(blogCommentDTO.getIdBlog());
			blogsComment.setMessage(blogCommentDTO.getMessage());
			blogsComment.setUserName(loggedInUser.getFirstName());
			blogsComment.setUserSurId(blogCommentDTO.getUserSurId());
			blogsComment = blogCommentRepository.save(blogsComment);
			result.setData(blogsComment);
			result.setStatusCode(200);
			result.setMessage("Blog comment saved successfuly!");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Something went wrong while saving comment!");
		}
		return result;
	}

	@Override
	public Document<?> editBlogComment(BlogCommentEditDTO blogCommentEditDTO) {
		Document<BlogComment> document = new Document<>();

		try {
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!blogCommentEditDTO.getUserSurId().equals(loggedInUser.getUserSurId())) {
				document.setData(null);
				document.setMessage("User dosent have access to edit this content.");
				document.setStatusCode(HttpStatus.FORBIDDEN.value());
				return document;

			}

			BlogComment blogComment = blogCommentRepository.findByIdBlogComment(blogCommentEditDTO.getIdBlogComment());

			if (blogComment != null) {
				blogComment.setMessage(blogCommentEditDTO.getMessage());
				blogComment.setCreatedAt(blogComment.getCreatedAt());
				blogComment.setCreatedBy(blogComment.getCreatedBy());
				blogComment = blogCommentRepository.save(blogComment);

				if (blogComment == null) {
					document.setData(null);
					document.setMessage("Error while blog comment update");
					document.setStatusCode(500);
				}
				document.setData(blogComment);
				document.setMessage("Comment updated successfuly");
				document.setStatusCode(HttpStatus.OK.value());
			} else {
				document.setData(null);
				document.setMessage("No Blog comment found");
				document.setStatusCode(404);
			}
			
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getCause().getLocalizedMessage());
			document.setStatusCode(500);
		}

		return document;
	}

	@Override
	public Document<?> deleteBlogComment(Long idBlogComment) {
		Document<Boolean> document = new Document<>();

		try {
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			BlogComment blogComment = blogCommentRepository.findByIdBlogCommentAndUserSurId(idBlogComment,
					loggedInUser.getUserSurId());

			if (blogComment == null) {
				document.setData(null);
				document.setMessage("No blog comment found!");
				document.setStatusCode(404);
				return document;
			}
			blogCommentRepository.delete(blogComment);
			document.setData(true);
			document.setMessage("Successfuly deleted blog comment");
			document.setStatusCode(200);
			return document;
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getCause().getLocalizedMessage());
			document.setStatusCode(500);
			return document;

		}

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBlogCommentByIdBlog(Long idBlog) {
		Document result = new Document();
		try {
			List<BlogComment> blogCommentlist = blogCommentRepository.findByIdBlogOrderByIdBlogCommentDesc(idBlog);
			if (!blogCommentlist.isEmpty()) {
				result.setData(blogCommentlist);
				result.setMessage("Successfuly fetching blog comments");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No blog comments found");
				result.setStatusCode(404);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

}
