/**
 * 
 */
package co.vistafoundation.vlearning.blogs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.blogs.dto.BlogCommentDTO;
import co.vistafoundation.vlearning.blogs.dto.BlogCommentEditDTO;
import co.vistafoundation.vlearning.blogs.service.BlogCommentService;
import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/blog-comments")
public class BlogCommentController {

	@Autowired
	BlogCommentService blogCommentService;

	@PostMapping("/add-blogs-comment")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> addBlogsCategory(@RequestBody @Valid BlogCommentDTO blogCommentDTO) {
		Document<?> responses = blogCommentService.addBlogComment(blogCommentDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PostMapping("/edit-blogs-comment")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> editBlogsCategory(@RequestBody @Valid BlogCommentEditDTO blogCommentEditDTO) {
		Document<?> responses = blogCommentService.editBlogComment(blogCommentEditDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PostMapping("/delete-blogs-comment")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> deleteBlogsCategory(@RequestParam @Valid Long idBlogComment) {
		Document<?> responses = blogCommentService.deleteBlogComment(idBlogComment);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping("/listby-blog")
	public ResponseEntity<?> addBlogsCategory(@RequestParam Long idBlog) {
		Document<?> responses = blogCommentService.getBlogCommentByIdBlog(idBlog);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

}
