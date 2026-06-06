package co.vistafoundation.vlearning.blogs.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.blogs.dto.BlogsCategoryDto;
import co.vistafoundation.vlearning.blogs.model.BlogsCategory;
import co.vistafoundation.vlearning.blogs.repository.BlogsCategoryRepository;
import co.vistafoundation.vlearning.blogs.repository.BlogsRepository;
import co.vistafoundation.vlearning.blogs.service.BlogsCategoryService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;

@Service
public class BlogsCategoryServiceImpl implements BlogsCategoryService {

	@Autowired
	BlogsCategoryRepository blogsCatRepo;

	@Autowired
	ImageFileUploadService imageUploadService;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Autowired
	BlogsRepository blogsRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(BlogsCategoryServiceImpl.class);

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document<BlogsCategory> addBlogCategory(BlogsCategoryDto blogsCatDto) {

		Document result = new Document();

		BlogsCategory blogsCat = new BlogsCategory();
		blogsCat.setCategoryName(blogsCatDto.getCategoryName());
		blogsCat.setCategoryImageLink(blogsCatDto.getCategoryImageLink());
		if (blogsCatDto.getActiveFlag() != null)
			blogsCat.setActiveFlag(blogsCatDto.getActiveFlag());
		else
			blogsCat.setActiveFlag(true);
		BlogsCategory blogsCategory = blogsCatRepo.save(blogsCat);

		result.setData(blogsCategory);
		result.setStatusCode(200);
		result.setMessage("Success");
		return result;
	}

	@Override
	public Document<List<BlogsCategory>> getAllBlogCategory() {
		Document<List<BlogsCategory>> result = new Document<>();
		try {
			List<BlogsCategory> blogCatlist = blogsCatRepo.findAll();
			if (!blogCatlist.isEmpty()) {
				result.setData(blogCatlist);
				result.setMessage("Successfull");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No Blog Categories found");
				result.setStatusCode(404);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document uploadBlogCategoryThumnail(MultipartFile blogCatThumnail) {
		Document result = new Document();
		try {
			String uniqueFile = "blogCatImage" + "_" + Long.toString(System.currentTimeMillis());
			File file = imageUploadService.convertMultiPartFileToFile(blogCatThumnail);
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blog-category-thumbnails",
					uniqueFile + "." + extension);
			boolean isDeletedFile = file.delete();
			logger.info("Logs file deleted from the system : "+isDeletedFile );
			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Thumbnail Uploaded Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateBlogCategory(BlogsCategoryDto blogsCatdto) {
		Document result = new Document();
		BlogsCategory existingBlogCat = blogsCatRepo.findByIdBlogCategory(blogsCatdto.getIdBlogCategory());
		try {
			if (existingBlogCat != null) {
				if (!existingBlogCat.getCategoryImageLink().equalsIgnoreCase(blogsCatdto.getCategoryImageLink())) {
					String filePath[] = existingBlogCat.getCategoryImageLink().split("/");
					String fileName = filePath[filePath.length - 1];
					imageUploadService.deleteFileFromS3("blogs/blog-category-thumbnails/" + fileName);
				}
				existingBlogCat.setActiveFlag(blogsCatdto.getActiveFlag());
				existingBlogCat.setCategoryImageLink(blogsCatdto.getCategoryImageLink());
				existingBlogCat.setCategoryName(blogsCatdto.getCategoryName());
				existingBlogCat.setIdBlogCategory(existingBlogCat.getIdBlogCategory());
				existingBlogCat.setCreatedAt(existingBlogCat.getCreatedAt());
				existingBlogCat.setCreatedBy(existingBlogCat.getCreatedBy());
				BlogsCategory updatedBlogCat = blogsCatRepo.save(existingBlogCat);
				result.setData(updatedBlogCat);
				result.setMessage("Successfully updated");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No Blog category found");
				result.setStatusCode(404);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateActiveFlag(Long idBlogCategory, Boolean flag) {
		Document result = new Document();
		BlogsCategory existingBlogCat = blogsCatRepo.findByIdBlogCategory(idBlogCategory);
		try {
			if (existingBlogCat != null) {
				BlogsCategory newCategory = new BlogsCategory(idBlogCategory, existingBlogCat.getCategoryName(),
						existingBlogCat.getCategoryImageLink(), flag);
				newCategory.setCreatedAt(existingBlogCat.getCreatedAt());
				newCategory.setCreatedBy(existingBlogCat.getCreatedBy());
				BlogsCategory updatedBlogCat = blogsCatRepo.save(newCategory);
				result.setData(updatedBlogCat);
				result.setMessage("Successfully updated");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No Blog category found");
				result.setStatusCode(404);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public Document getAllActiveBlogCategories() {
		Document<List<BlogsCategory>> result = new Document<>();
		try {
			List<BlogsCategory> blogCatlist = blogsCatRepo.findAllByActiveFlag(true);

			if (!blogCatlist.isEmpty()) {
				result.setData(blogCatlist);
				result.setMessage("Successfull");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No Blog Categories found");
				result.setStatusCode(404);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<List<BlogsCategory>> getAllActiveBlogCategory() {
		Document<List<BlogsCategory>> result = new Document<>();
		try {

			List<Long> bloglist = blogsCatRepo.findAllByActiveFlagFromBlog(Boolean.TRUE);

			if (bloglist.isEmpty())
				throw new AppException("No Blogs found for fetching category.");

			List<BlogsCategory> blogCatlist = blogsCatRepo.findByIdBlogCategoryIn(bloglist);

			if (!blogCatlist.isEmpty()) {
				result.setData(blogCatlist);
				result.setMessage("Successfull");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("No Blog Categories found");
				result.setStatusCode(200);
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	
	@Override
	public Document<BlogsCategory> getBlogCategory(Long idBlogCategory) {
		Document<BlogsCategory> result = new Document<>();
		try {

			BlogsCategory bc = blogsCatRepo.findByIdBlogCategoryAndActiveFlag(idBlogCategory,true);

			if (bc == null)
				throw new AppException("No Active BlogCategory found.");
			
				result.setData(bc);
				result.setMessage("Successfull");
				result.setStatusCode(200);
			
		} 
		catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}
}
