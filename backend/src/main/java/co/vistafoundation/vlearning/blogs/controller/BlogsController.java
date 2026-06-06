package co.vistafoundation.vlearning.blogs.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.blogs.dto.BlogsDto;
import co.vistafoundation.vlearning.blogs.model.Quote;
import co.vistafoundation.vlearning.blogs.service.BlogsCategoryService;
import co.vistafoundation.vlearning.blogs.service.BlogsService;
import co.vistafoundation.vlearning.common.response.Document;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogsController {

	@Autowired
	BlogsService blogService;

	@Autowired
	BlogsCategoryService blogCatService;

	/**
	 * Searches blogs based on the given search query.
	 * 
	 * @author vk
	 * 
	 * @param searchQuery the search query to use
	 * @return a document containing a list of BlogCategoryWIthBlogsDTO objects
	 * @throws AppException if no blogs are found
	 * 
	 *                      Endpoint for searching blogs by query.
	 * 
	 * @param query the search query to use
	 * @return a ResponseEntity containing the search results in a Document object
	 */

	@GetMapping("/search-blogs")
	public ResponseEntity<?> searchBlogsByQuery(@RequestParam String query) {
		Document<?> responses = blogService.searchBlogs(query);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Uploads the blog thumbnail image to AWS S3 bucket.
	 * 
	 * @author vk
	 *
	 * 
	 * @param blogThumnail The multipart file containing the blog thumbnail image.
	 * @return A Document object containing the uploaded picture URL, status code
	 *         and message.
	 * 
	 *         REST endpoint to upload the blog thumbnail image.
	 *
	 * @param blogThumnail The multipart file containing the blog thumbnail image.
	 * @return A ResponseEntity object containing a Document object with the
	 *         uploaded picture URL, status code and message.
	 */
	@PostMapping("/upload-blog-thumbnail")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> uploadBlogThumnail(@RequestParam(name = "blogThumnail") MultipartFile blogThumnail) {
		Document<?> reponses = blogService.uploadBlogThumnail(blogThumnail);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Uploads a blog subject image to an Amazon S3 bucket and returns the URL of
	 * the uploaded image.
	 * 
	 * @author vk
	 * @param blogSubjectImage the blog subject image to upload
	 * @return a Document object containing the URL of the uploaded image, as well
	 *         as status code and message information
	 *
	 *         Endpoint for uploading a blog subject image.
	 * 
	 * @param blogSubjectImage the blog subject image to upload
	 * @return a ResponseEntity containing a Document object with information about
	 *         the uploaded image, including status code and message
	 */
	@PostMapping("/upload-blog-subjectimage")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> uploadBlogSubjectImage(
			@RequestParam(name = "blogSubjectImage") MultipartFile blogSubjectImage) {
		Document<?> reponses = blogService.uploadBlogSubjectImage(blogSubjectImage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * REST endpoint to handle the HTTP POST request to upload blog content image.
	 * 
	 * @author vk
	 * 
	 * @param blogContentImage the multipart file object representing the blog
	 *                         content image
	 * @return a {@link ResponseEntity} containing the response data as
	 *         {@link Document} object and HTTP status code
	 */

	@PostMapping("/upload-blog-contentimage")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> uploadBlogContentImage(
			@RequestParam(name = "blogContentImage") MultipartFile blogContentImage) {
		Document<?> reponses = blogService.uploadBlogContentImage(blogContentImage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Uploads a blog content image to an S3 bucket and returns the URL of the
	 * uploaded image.
	 * 
	 * @author vk
	 * 
	 * @param blogContentImage the MultipartFile containing the blog content image
	 *                         to be uploaded
	 * @return a Document containing the URL of the uploaded image if successful,
	 *         otherwise an error message and null data
	 * 
	 *         Adds a blog to the database with the specified details.
	 * 
	 * @param blogDto the DTO containing the details of the blog to be added
	 * @return a Document containing the added blog if successful, otherwise an
	 *         error message and null data
	 */
	@PostMapping("/add-blogs")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> addBlogs(@RequestBody @Valid BlogsDto blogDto) {
		Document<?> responses = blogService.addBlog(blogDto);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Gets all blogs from the database and returns them as DTOs, along with
	 * additional information such as the number of comments and the content of the
	 * blog (retrieved from an S3 bucket).
	 * 
	 * @author vk
	 * 
	 * @return a Document containing a list of BlogsNewDto objects if successful,
	 *         otherwise an error message and null data
	 * 
	 * 
	 *         Retrieves all blogs from the database.
	 * @return ResponseEntity containing a Document with a List of BlogsNewDto
	 *         objects if successful, kotlin Copy code otherwise an error message
	 *         and null data
	 */

	@GetMapping("/get-all-blogs")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> getAllBlogs() {
		Document<?> responses = blogService.getAllBlogs();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * 
	 * Retrieves all active blogs with their respective categories and comments.
	 * 
	 * @return A Document object containing a List of BlogCategoryWIthBlogsDTO
	 *         representing all active blogs.
	 * 
	 * @throws AppException if no blogs are found.
	 */

	@GetMapping("/get-all-active-blogs")
	public ResponseEntity<?> getAllActiveBlogs() {
		Document<?> responses = blogService.getAllActiveBlogs();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * 
	 * Updates an existing blog with the given information and returns a response
	 * entity containing the result of the update operation.
	 *
	 * @param blogDto The DTO object containing the updated information for the
	 *                blog.
	 * @return A `ResponseEntity` object containing a `Document` object representing
	 *         the result of the update operation. The `Document` contains a `data`
	 *         field that holds the updated `Blogs` object, a `message` field
	 *         indicating the result of the update operation, and a `statusCode`
	 *         field indicating the HTTP status code for the response.
	 */

	@PutMapping("/update-blog")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> updateBlog(@RequestBody @Valid BlogsDto blogDto) {
		Document<?> responses = blogService.updateBlog(blogDto);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Updates the hide flag of the blog with the given ID and returns a JSON
	 * response representing the result of the update operation.
	 *
	 * @param idBlog The ID of the blog to update.
	 * @param flag   The value to set for the hide flag of the blog.
	 * @return A `ResponseEntity` object representing the result of the update
	 *         operation. The response body contains a JSON object with a `data`
	 *         field that holds the updated `Blogs` object, a `message` field
	 *         indicating the result of the update operation, and a `statusCode`
	 *         field indicating the HTTP status code for the response.
	 */
	@PutMapping("/update-hide-flag")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> updateHideFlag(@RequestParam Long idBlog, @RequestParam Boolean flag) {

		Document<?> reponses = blogService.updateHideFlag(idBlog, flag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/*
	 * 
	 * blog posts in the specified category and page number.
	 *
	 * @param idBlogCategory The ID of the blog category to retrieve posts from.
	 * 
	 * @param pageNumber The page number of the results to retrieve. Defaults to 0
	 * if not specified.
	 * 
	 * @return A `ResponseEntity` object representing the result of the query
	 * operation. The response body contains a JSON object with a `data` field that
	 * holds a list of `BlogsDto` objects, a `message` field indicating the result
	 * of the query operation, and a `statusCode` field indicating the HTTP status
	 * code for the response.
	 */

	@GetMapping("/get-blogs-by-category")
	public ResponseEntity<?> getBlogsByCategory(@RequestParam Long idBlogCategory,
			@RequestParam(defaultValue = "0", required = false) int pageNumber) {
		Document<?> reponses = blogService.getBlogsByCategory(idBlogCategory, pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Returns a ResponseEntity with featured blogs.
	 * 
	 * @author haaris
	 * 
	 * @return ResponseEntity with Document containing list of featured blogs.
	 */

	@GetMapping("/get-featured-blogs")
	public ResponseEntity<?> getFeaturedBlogs() {
		Document<?> reponses = blogService.getFeaturedBlogs();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Returns a ResponseEntity with blog by its id.
	 * 
	 * @author vk
	 * 
	 * @param idBlog the id of the blog to retrieve
	 * @return ResponseEntity with Document containing the blog
	 */
	@GetMapping("/get-blog")
	public ResponseEntity<?> getBlogByIdBlog(@RequestParam Long idBlog) {
		Document<?> reponses = blogService.getBlogByIdBlog(idBlog);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Updates the number of clicks for a blog post with the specified ID.
	 * 
	 * @author Shruthi
	 * @param idBlog The ID of the blog post to update.
	 * @return A ResponseEntity containing a Document object with the updated blog
	 *         post data and a status code.
	 */

	@PutMapping("/update-click")
	public ResponseEntity<?> updateClick(@RequestParam Long idBlog) {
		Document<?> reponses = blogService.updateClicks(idBlog);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Retrieves the blog post with the specified slug URL.
	 * 
	 * @author Shruthi
	 * @param slugURL The slug URL of the blog post to retrieve.
	 * @return A ResponseEntity containing a Document object with the retrieved blog
	 *         post data and a status code.
	 */
	@GetMapping("/get-blog-by-slug-url")
	public ResponseEntity<?> getBlogBySlugURL(@RequestParam String slugURL) {
		Document<?> reponses = blogService.getBlogBySlugURL(slugURL);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Validates the provided slug URL to ensure it is unique and meets certain
	 * criteria.
	 * 
	 * @author Shruthi
	 * @param slugUrl The slug URL to validate.
	 * @return A ResponseEntity containing a Document object with a Boolean value
	 *         indicating whether the slug URL is valid and a status code.
	 */

	@GetMapping("/validate-slug-url")
	public ResponseEntity<Document<Boolean>> validateSlugURL(@RequestParam String slugUrl) {
		Document<Boolean> resp = blogService.validateSlugURL(slugUrl);
		return ResponseEntity.status(resp.getStatusCode()).body(resp);
	}
	
	@GetMapping("/get-all-quotes")
	public ResponseEntity<Document<List<Quote>>> getAllQuotes() {
		 Document<List<Quote>> resp = blogService.getAllQuotes();
		return ResponseEntity.status(resp.getStatusCode()).body(resp);
	}

}
