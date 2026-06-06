package co.vistafoundation.vlearning.blogs.impl;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.IOUtils;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.blogs.dto.BlogCategoryWIthBlogsDTO;
import co.vistafoundation.vlearning.blogs.dto.BlogsDto;
import co.vistafoundation.vlearning.blogs.dto.BlogsNewDto;
import co.vistafoundation.vlearning.blogs.model.BlogComment;
import co.vistafoundation.vlearning.blogs.model.Blogs;
import co.vistafoundation.vlearning.blogs.model.BlogsCategory;
import co.vistafoundation.vlearning.blogs.model.Quote;
import co.vistafoundation.vlearning.blogs.repository.BlogCommentRepository;
import co.vistafoundation.vlearning.blogs.repository.BlogsCategoryRepository;
import co.vistafoundation.vlearning.blogs.repository.BlogsRepository;
import co.vistafoundation.vlearning.blogs.repository.QuoteRepository;
import co.vistafoundation.vlearning.blogs.service.BlogsService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;

@Service
public class BlogsServiceImp implements BlogsService {

	@Autowired
	BlogsRepository blogsRepo;

	@Autowired
	BlogsCategoryRepository blogsCatRepo;

	@Autowired
	ImageFileUploadService imageUploadService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	BlogCommentRepository blogCommentRepository;
	
	@Autowired
	QuoteRepository quoteRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(BlogsServiceImp.class);

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document<List<BlogCategoryWIthBlogsDTO>> searchBlogs(String searchQuery) {
		Document<List<BlogCategoryWIthBlogsDTO>> result = new Document<>();
		try {
			List<Blogs> finalResult = new ArrayList<>();
			List<BlogCategoryWIthBlogsDTO> allBlogs = new ArrayList();

			List<BlogsCategory> blogCategoryList = blogsCatRepo
					.findAllByCategoryNameContainingAndActiveFlag(searchQuery, Boolean.TRUE);

			if (!blogCategoryList.isEmpty()) {
				HashSet<Long> headers = new HashSet<>();
				blogCategoryList.removeIf(e -> !headers.add(e.getIdBlogCategory()));
				List<Long> temp3 = new ArrayList<>(headers);

				List<Blogs> blogsListOfCatId = blogsRepo.findByIdBlogCategoryIn(temp3);

				finalResult.addAll(blogsListOfCatId);
			}

			List<Blogs> blogsList = blogsRepo
					.findAllByPublisherNameContainingOrBlogTitleContainingOrTagsContainingAndPublishDateBeforeAndHideFlag(
							searchQuery, searchQuery, searchQuery, LocalDate.now(), false);

			if (!blogsList.isEmpty()) {
				finalResult.addAll(blogsList);
			}
			if (!finalResult.isEmpty()) {
				List<Blogs> searchBlogResult = finalResult.stream().distinct().collect(Collectors.toList());

				HashSet<Long> headers = new HashSet<>();
				searchBlogResult.removeIf(e -> !headers.add(e.getIdBlogCategory()));
				List<Long> temp3 = new ArrayList<>(headers);
				List<BlogsCategory> blogCategoriesOfsearch = blogsCatRepo.findByIdBlogCategoryIn(temp3);

				if (!blogCategoriesOfsearch.isEmpty()) {

					for (BlogsCategory blogsCategory : blogCategoriesOfsearch) {
						BlogCategoryWIthBlogsDTO blogCategoryWIthBlogsDTO = new BlogCategoryWIthBlogsDTO();

						List<BlogsNewDto> blogList = new ArrayList();
						for (Blogs searchedBlog : searchBlogResult) {
							if (searchedBlog.getIdBlogCategory() == blogsCategory.getIdBlogCategory()) {
								List<BlogComment> blogComm = blogCommentRepository
										.findByIdBlog(searchedBlog.getIdBlog());
								BlogsNewDto dto = new BlogsNewDto();
								dto.setBlogS3Link(searchedBlog.getBlogS3Link());
								dto.setBlogTitle(searchedBlog.getBlogTitle());
								dto.setHideFlag(searchedBlog.getHideFlag());
								dto.setIdBlog(searchedBlog.getIdBlog());
								dto.setIdBlogCategory(searchedBlog.getIdBlogCategory());
								dto.setNoOfComments(blogComm.size());
								dto.setNumClick(searchedBlog.getNumClick());
								dto.setPublishDate(searchedBlog.getPublishDate());
								dto.setPublisherName(searchedBlog.getPublisherName());
								dto.setSubjectImageLink(searchedBlog.getSubjectImageLink());
								dto.setTags(searchedBlog.getTags());
								dto.setThumnailImageLink(searchedBlog.getThumnailImageLink());
								dto.setUserSurId(searchedBlog.getUserSurId());
								blogList.add(dto);
							}
						}
						if (!blogList.isEmpty()) {
							blogCategoryWIthBlogsDTO.setBlogList(blogList);
							blogCategoryWIthBlogsDTO.setActiveFlag(blogsCategory.getActiveFlag());
							blogCategoryWIthBlogsDTO.setCategoryImageLink(blogsCategory.getCategoryImageLink());
							blogCategoryWIthBlogsDTO.setCategoryName(blogsCategory.getCategoryName());
							blogCategoryWIthBlogsDTO.setIdBlogCategory(blogsCategory.getIdBlogCategory());
							allBlogs.add(blogCategoryWIthBlogsDTO);
						}

					}
				}
				if (allBlogs.isEmpty())
					throw new AppException("No Blogs found");
				else {
					result.setData(allBlogs);
					result.setStatusCode(200);
					result.setMessage("Successfull");
				}
			} else {
				throw new AppException("No Blogs found");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(500);
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public String uploadBlogContent(String fileContent) {
		try {
			String uniqueFile = "blogContent" + "_" + Long.toString(System.currentTimeMillis());
			File file = fileUploadService.convertStringDataToFile(fileContent, uniqueFile + ".html");
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blogs-rtf-content",
					uniqueFile + "." + extension);
			
			boolean isDeletedFile = file.delete();
			logger.info("Blog file deleted from the system : "+isDeletedFile );
			return uploadedPictureUrl;
		} catch (Exception exp) {
			return null;
		}
	}
	
	public String updateBlogContent(String fileContent,String fileName) {
		try {
			
			File file = fileUploadService.convertStringDataToFile(fileContent,fileName);			

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blogs-rtf-content",
					fileName);
			boolean isDeletedFile = file.delete();
			logger.info("Blog file deleted from the system : "+isDeletedFile );
			return uploadedPictureUrl;
		} catch (Exception exp) {
			return null;
		}
	}
	

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document uploadBlogThumnail(MultipartFile blogThumnail) {
		Document result = new Document();
		try {
			String uniqueFile = "blogThumbnailImage" + "_" + Long.toString(System.currentTimeMillis());
			File file = imageUploadService.convertMultiPartFileToFile(blogThumnail);
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blog-thumbnails",
					uniqueFile + "." + extension);
			
			boolean isDeletedFile = file.delete();
			logger.info("Blog thumbnail file deleted from the system : "+isDeletedFile );
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
	public Document uploadBlogSubjectImage(MultipartFile blogSubjectImage) {
		Document result = new Document();
		try {
			String uniqueFile = "blogSubjectImage" + "_" + Long.toString(System.currentTimeMillis());
			File file = imageUploadService.convertMultiPartFileToFile(blogSubjectImage);
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blog-subject",
					uniqueFile + "." + extension);
			
			boolean isDeletedFile = file.delete();
			logger.info("Blog subject image file deleted from the system : "+isDeletedFile );
			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Subject Image Uploaded Successfully");
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
	public Document uploadBlogContentImage(MultipartFile blogContentImage) {
		Document result = new Document();
		try {
			String uniqueFile = "blogContentImage" + "_" + Long.toString(System.currentTimeMillis());
			File file = imageUploadService.convertMultiPartFileToFile(blogContentImage);
			String extension = "";

			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
				extension = file.getName().substring(i + 1);
			}

			String uploadedPictureUrl = imageUploadService.uploadFileToS3Bucket(file, "blogs/blog-content-image",
					uniqueFile + "." + extension);
			boolean isDeletedFile = file.delete();
			logger.info("Blog content image file deleted from the system : "+isDeletedFile );
			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Content Image Uploaded Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<Blogs> addBlog(BlogsDto blogDto) {
		Document<Blogs> result = new Document<>();
		try {
			BlogsCategory blogCategory = blogsCatRepo.findByIdBlogCategory(blogDto.getIdBlogCategory());
			if (blogCategory != null) {
				UserPrincipal userPrincipal = null;
				
				if(blogDto.getSlugURL() == null) 
				{
					throw new AppException("Slug URL Cannot be null.");
				}
				
				if(blogDto.getSlugURL().trim().isEmpty() ) 
				{
					throw new AppException("Slug URL Cannot be empty.");
				}

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					userPrincipal = (UserPrincipal) authentication.getPrincipal();

				}

				if (userPrincipal == null) {
					result.setData(null);
					result.setStatusCode(500);
					result.setMessage("Invalid User");
					return result;
				}

				Blogs blogs = new Blogs();
				blogs.setBlogTitle(blogDto.getBlogTitle());
				blogs.setHideFlag(false);
				blogs.setIdBlogCategory(blogDto.getIdBlogCategory());
				blogs.setNumClick(0);
				blogs.setPublishDate(blogDto.getPublishDate());
				blogs.setPublisherName(blogDto.getPublisherName());
				blogs.setSubjectImageLink(blogDto.getSubjectImageLink());
				blogs.setThumnailImageLink(blogDto.getThumnailImageLink());
				blogs.setTags(blogDto.getTags());
				blogs.setUserSurId(userPrincipal.getUserSurId());
				blogs.setMetaTitle(blogDto.getMetaTitle());
				blogs.setMetaDescritption(blogDto.getMetaDescritption());
				blogs.setSlugURL(blogDto.getSlugURL().trim().toLowerCase());

				if (blogDto.getBlogS3Link() != null) {
					String link = this.uploadBlogContent(blogDto.getBlogS3Link());
					if (link != null) {
						blogs.setBlogS3Link(link);
					}
				} else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage("Error while uploading blog content");
					return result;
				}

				Blogs savedBlog = blogsRepo.save(blogs);
				result.setData(savedBlog);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			} else {
				result.setData(null);
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				result.setMessage("No Blog category is found");
				return result;
			}
		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Slug URL.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}
	}

	@Override
	public Document<List<BlogsNewDto>> getAllBlogs() {
		Document<List<BlogsNewDto>> result = new Document<>();

		List<Blogs> allBlogs = blogsRepo.findAll();
		try {
			if (allBlogs.isEmpty())
				throw new AppException("No Blogs found");
			else {
				List<BlogsNewDto> newBlogList = new ArrayList<BlogsNewDto>();
				for (Blogs blog : allBlogs) {
					
					String fileContents;
					
					try {
						fileContents  = IOUtils.toString(new URL(blog.getBlogS3Link()).openStream());
						fileContents = fileContents.replaceAll("(\\r|\\n)", "");
					}
					catch(Exception e) 
					{
						fileContents = e.getLocalizedMessage();
					}
					
					blog.setBlogS3Link(fileContents);

					List<BlogComment> blogComm = blogCommentRepository.findByIdBlog(blog.getIdBlog());

					BlogsNewDto dto = new BlogsNewDto();
					dto.setBlogS3Link(blog.getBlogS3Link());
					dto.setBlogTitle(blog.getBlogTitle());
					dto.setHideFlag(blog.getHideFlag());
					dto.setIdBlog(blog.getIdBlog());
					dto.setIdBlogCategory(blog.getIdBlogCategory());
					dto.setNoOfComments(blogComm.size());
					dto.setNumClick(blog.getNumClick());
					dto.setPublishDate(blog.getPublishDate());
					dto.setPublisherName(blog.getPublisherName());
					dto.setSubjectImageLink(blog.getSubjectImageLink());
					dto.setTags(blog.getTags());
					dto.setThumnailImageLink(blog.getThumnailImageLink());
					dto.setUserSurId(blog.getUserSurId());
					dto.setMetaTitle(blog.getMetaTitle());
					dto.setMetaDescritption(blog.getMetaDescritption());
					dto.setSlugURL(blog.getSlugURL());

					newBlogList.add(dto);
				}
				result.setData(newBlogList);
				result.setStatusCode(200);
				result.setMessage("Successfull");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(500);
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document<List<BlogCategoryWIthBlogsDTO>> getAllActiveBlogs() {
		Document result = new Document<>();
		List<BlogCategoryWIthBlogsDTO> allBlogs = new ArrayList();

		List<BlogsCategory> blogCatlist = blogsCatRepo.findAllByActiveFlag(true);
		try {
			if (!blogCatlist.isEmpty()) {
				for (BlogsCategory blogsCategory : blogCatlist) {
					BlogCategoryWIthBlogsDTO blogCategoryWIthBlogsDTO = new BlogCategoryWIthBlogsDTO();
					List<Blogs> blogList = blogsRepo
							.findAllByIdBlogCategoryAndHideFlag(blogsCategory.getIdBlogCategory(), Boolean.FALSE);
					List<BlogsNewDto> newBlogList = new ArrayList<BlogsNewDto>();
					for (Blogs blog : blogList) {
						String fileContents = IOUtils.toString(new URL(blog.getBlogS3Link()).openStream());
						fileContents = fileContents.replaceAll("(\\r|\\n)", "");
						blog.setBlogS3Link(fileContents);

						List<BlogComment> blogComm = blogCommentRepository.findByIdBlog(blog.getIdBlog());

						BlogsNewDto dto = new BlogsNewDto();
						dto.setBlogS3Link(blog.getBlogS3Link());
						dto.setBlogTitle(blog.getBlogTitle());
						dto.setHideFlag(blog.getHideFlag());
						dto.setIdBlog(blog.getIdBlog());
						dto.setIdBlogCategory(blog.getIdBlogCategory());
						dto.setNoOfComments(blogComm.size());
						dto.setNumClick(blog.getNumClick());
						dto.setPublishDate(blog.getPublishDate());
						dto.setPublisherName(blog.getPublisherName());
						dto.setSubjectImageLink(blog.getSubjectImageLink());
						dto.setTags(blog.getTags());
						dto.setThumnailImageLink(blog.getThumnailImageLink());
						dto.setUserSurId(blog.getUserSurId());
						dto.setMetaTitle(blog.getMetaTitle());
						dto.setMetaDescritption(blog.getMetaDescritption());
						dto.setSlugURL(blog.getSlugURL());
						newBlogList.add(dto);
					}
					if (!blogList.isEmpty()) {
						blogCategoryWIthBlogsDTO.setBlogList(newBlogList);
						blogCategoryWIthBlogsDTO.setActiveFlag(blogsCategory.getActiveFlag());
						blogCategoryWIthBlogsDTO.setCategoryImageLink(blogsCategory.getCategoryImageLink());
						blogCategoryWIthBlogsDTO.setCategoryName(blogsCategory.getCategoryName());
						blogCategoryWIthBlogsDTO.setIdBlogCategory(blogsCategory.getIdBlogCategory());
						allBlogs.add(blogCategoryWIthBlogsDTO);
					}
				}
			}
			if (allBlogs.isEmpty())
				throw new AppException("No Blogs found");
			else {
				result.setData(allBlogs);
				result.setStatusCode(200);
				result.setMessage("Successfull");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(500);
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateBlog(@Valid BlogsDto blogDto) {
		Document result = new Document();
		Blogs existingBlog = blogsRepo.findByIdBlog(blogDto.getIdBlog());
		try {
			if (existingBlog != null) {

				Blogs newBlog = new Blogs(blogDto.getIdBlog(), blogDto.getIdBlogCategory(), blogDto.getUserSurId(),
						blogDto.getBlogTitle(), blogDto.getSubjectImageLink(), blogDto.getThumnailImageLink(),
						blogDto.getPublishDate(), blogDto.getBlogS3Link(), blogDto.getHideFlag(), blogDto.getNumClick(),
						blogDto.getPublisherName(), blogDto.getTags());
				newBlog.setCreatedAt(existingBlog.getCreatedAt());
				newBlog.setCreatedBy(existingBlog.getCreatedBy());
				newBlog.setMetaTitle(blogDto.getMetaTitle());
				newBlog.setMetaDescritption(blogDto.getMetaDescritption());
				newBlog.setSlugURL(blogDto.getSlugURL().trim().toLowerCase());

				if (blogDto.getBlogS3Link() != null) {
					
					
					String filePath[] = existingBlog.getBlogS3Link().split("/");
					String fileName = filePath[filePath.length - 1];
					String link = this.updateBlogContent(blogDto.getBlogS3Link(),fileName);
					// update the s3 link 
					newBlog.setBlogS3Link(link);
			
					
				} else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage("Error while uploading blog content");
					return result;
				}
				Blogs updatedBlog = blogsRepo.save(newBlog);
				result.setData(updatedBlog);
				result.setMessage("Successfully updated");
				result.setStatusCode(200);
			} else {
				throw new AppException("No Blog category found");
			}
		} catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Slug URL.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}
		}
		return result;
	}

	@Override
	public Document<Blogs> updateHideFlag(Long idBlog, Boolean flag) {
		Document<Blogs> result = new Document<>();
		Blogs existingBlog = blogsRepo.findByIdBlog(idBlog);
		try {

			if (existingBlog == null)
				throw new AppException("No Blog category found");

			existingBlog.setHideFlag(flag);

			Blogs updatedBlog = blogsRepo.save(existingBlog);

			result.setData(updatedBlog);
			result.setMessage("Blog Successfully updated.");
			result.setStatusCode(200);

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<?> getBlogsByCategory(Long idBlogCategory,int pageNumber) {
		   
		Document<Page<Blogs>> result = new Document<>();
		try {
			
			BlogsCategory bc = blogsCatRepo.findByIdBlogCategoryAndActiveFlag(idBlogCategory, Boolean.TRUE);
			
			if (bc == null) 
				throw new NullPointerException("Invalid Blog Category.");
				
			
            Pageable paging = PageRequest.of(pageNumber, 6);
           
			
			Page<Blogs> blogList = blogsRepo.findAllByIdBlogCategoryAndHideFlag(idBlogCategory, Boolean.FALSE,paging);
			
			
			if (blogList.getContent().isEmpty() && pageNumber > 0) {
				result.setMessage("No More Blogs Available.");
			}
			else if (blogList.getContent().isEmpty()) {

				throw new NullPointerException("No Blogs Available for this category.");
			}
			else {
				result.setData(blogList);
				result.setMessage("Request Successfull");
				result.setStatusCode(200);
				
			}
		
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
			return result;
		}
		
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getFeaturedBlogs() {
		Document result = new Document();
		List<Blogs> finalBlogList = new ArrayList();
		try {
			List<Blogs> blogList = blogsRepo.findAllByOrderByNumClickDesc();
			if (!blogList.isEmpty()) {
				if (blogList.size() > 3) {
					finalBlogList = blogList.stream().sorted(Comparator.comparing(Blogs::getNumClick).reversed())
							.limit(5).collect(Collectors.toList());
				} else {
					finalBlogList.addAll(blogList);
				}
				result.setData(finalBlogList);
				result.setMessage("Succesfull result");
				result.setStatusCode(200);
			} else {
				throw new AppException("No results available");
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
	public Document getBlogByIdBlog(Long idBlog) {
		Document result = new Document();
		try {
			Blogs blog = blogsRepo.findByIdBlogAndHideFlag(idBlog, Boolean.FALSE);

			if (blog != null) {
				String fileContents = IOUtils.toString(new URL(blog.getBlogS3Link()).openStream());
				fileContents = fileContents.replaceAll("(\\r|\\n)", "");
				blog.setBlogS3Link(fileContents);
				result.setData(blog);
				result.setMessage("Blog fetching successfully");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("Blog information not found");
				result.setStatusCode(500);
			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<?> updateClicks(Long idBlog) {
		Document<Blogs> result = new Document<>();
		try {
			Blogs existingBlog = blogsRepo.findByIdBlog(idBlog);

			if (existingBlog == null)
				throw new NullPointerException("Invalid Blog data not found.");

			int noOfClick = existingBlog.getNumClick();
			int updateNoOfClick = noOfClick + 1;

			existingBlog.setNumClick(updateNoOfClick);
			Blogs updatedBlog = blogsRepo.save(existingBlog);
			result.setData(updatedBlog);
			result.setMessage("Successfully updated");
			result.setStatusCode(200);

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<?> getBlogBySlugURL(String slugURL) {

		Document<Blogs> result = new Document<>();
		try {

			Blogs blog = blogsRepo.findByslugURLAndHideFlag(slugURL.trim().toLowerCase(), Boolean.FALSE);

			if (blog != null) {
				String fileContents = IOUtils.toString(new URL(blog.getBlogS3Link()).openStream());
				fileContents = fileContents.replaceAll("(\\r|\\n)", "");
				blog.setBlogS3Link(fileContents);
				result.setData(blog);
				result.setMessage("Blog fetching successfully");
				result.setStatusCode(200);
			} else {
				result.setData(null);
				result.setMessage("Blog information not found");
				result.setStatusCode(500);
			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<Boolean> validateSlugURL(String slugUrl) {
		Document<Boolean> result = new Document<>();
		try {

			Blogs blog = blogsRepo.findByslugURL(slugUrl.trim().toLowerCase());

			if (blog == null) {
				result.setData(false);
				result.setMessage("slug url not found");
				result.setStatusCode(200);
			} else {
				result.setData(true);
				result.setMessage("slug url found");
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
	public Document<List<Quote>> getAllQuotes(){
		Document<List<Quote>> result = new Document<>();
		try {
			
			List<Quote> quoteList =quoteRepository.findAll();
			
			if(quoteList==null) {
				throw new AppException("No quotes available");
			}
			
			result.setData(quoteList);
			result.setMessage("Resquest Successfull");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}
	
	
}
