package co.vistafoundation.vlearning.blogs.controller;

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

import co.vistafoundation.vlearning.blogs.dto.BlogsCategoryDto;
import co.vistafoundation.vlearning.blogs.service.BlogsCategoryService;
import co.vistafoundation.vlearning.common.response.Document;

@RestController
@RequestMapping("/api/v1/blog-category")
public class BlogsCategoryController {

	@Autowired
	BlogsCategoryService blogCatService;

	@PostMapping("/add-blogs-cat")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> addBlogsCategory(@RequestBody @Valid BlogsCategoryDto blogsCatDto) {
		Document<?> responses = blogCatService.addBlogCategory(blogsCatDto);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PostMapping("/upload-blog-cat-thumbnail")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> uploadBlogCategoryThumnail(
			@RequestParam(name = "categoryThumbnail") MultipartFile categoryThumbnail) {
		Document<?> reponses = blogCatService.uploadBlogCategoryThumnail(categoryThumbnail);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PutMapping("/update-blog-cat")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> updateBlogCat(@RequestBody @Valid BlogsCategoryDto blogsCatDto) {

		Document<?> reponses = blogCatService.updateBlogCategory(blogsCatDto);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/get-all-blogs-cat")
	public ResponseEntity<?> getAllBlogsCategory() {
		Document<?> responses = blogCatService.getAllBlogCategory();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PutMapping("/update-active-flag")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BLOGGER')")
	public ResponseEntity<?> updateActiveFlag(@RequestParam Long idBlogCategory, @RequestParam Boolean flag) {
		Document<?> reponses = blogCatService.updateActiveFlag(idBlogCategory, flag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/get-all-active-blog-cat")
	public ResponseEntity<?> getAllActiveBlogCategories() {
		Document<?> reponses = blogCatService.getAllActiveBlogCategories();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@GetMapping("/get-all-blogs-cat-public")
	public ResponseEntity<?> getAllActieBlogsCategory() {
		Document<?> responses = blogCatService.getAllActiveBlogCategory();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getBlogCategory(@RequestParam Long idBlogCategory ) {
		Document<?> responses = blogCatService.getBlogCategory(idBlogCategory);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

}
