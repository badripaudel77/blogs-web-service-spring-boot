package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.exceptions.ResourceAlreadyExistsException;
import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.exceptions.UnauthorizedException;
import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.repositories.BlogCategoryRepository;
import com.amigos.spring.blog.repositories.BlogRepository;
import com.amigos.spring.blog.repositories.CustomerUserRepository;
import com.amigos.spring.blog.services.interfaces.BlogService;
import com.amigos.spring.blog.utils.BlogDTOHelper;
import com.amigos.spring.blog.utils.BlogsData;
import com.amigos.spring.blog.utils.GlobalConstants;
import com.amigos.spring.blog.utils.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO : implement redis cache later and optimize performance assuming we have large no of data.
@Service
public class BlogServiceImpl implements BlogService {

    Logger logger = MyLogger.getMyLogger();
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CustomerUserRepository customerUserRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Override
    public List<BlogDTO> getAllBlogsByUser(Long customerUserId) {
        // CustomerUser customerUser = customerUserRepository.findById(customerUserId).orElseThrow(() -> new ResourceNotFoundException("The user with Id " + customerUserId + " doesn't exist.", 404));
        Optional<CustomerUser> customerUser  = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("The user with Id " + customerUserId + " doesn't exist.", 404);
        }
        List<Blog> blogsByCustomerUser = blogRepository.findByCustomerUser(customerUser);
        if(blogsByCustomerUser.size() ==0 ){
            throw new ResourceNotFoundException("User :" + customerUserId + " hasn't created blogs yet.", 404);
        }
        List<BlogDTO> blogDTOListByUser = new ArrayList<>();
        blogsByCustomerUser.forEach((blog) -> {
            BlogDTO blogDTO = BlogDTOHelper.buildDTOFromBlog(blog);
            blogDTOListByUser.add(blogDTO);
        });
        return blogDTOListByUser;
    }

    @Override
    public List<BlogDTO> getAllByBlogCategory(Long blogCategoryId) {
        Optional<BlogCategory> blogCategory  = blogCategoryRepository.findById(blogCategoryId);
        if(!blogCategory.isPresent()) {
            throw new ResourceNotFoundException("The category " + blogCategoryId + " doesn't exist.", 404);
        }
        List<Blog> blogsByBlogCategory = blogRepository.findByBlogCategory(blogCategory);
        if(blogsByBlogCategory.size() == 0) {
            throw new ResourceNotFoundException("Blogs with category" + blogCategory.get().getCategoryName() + " not found.", 404);
        }
        List<BlogDTO> blogDTOList = new ArrayList<>();
        blogsByBlogCategory.forEach((blog) -> {
            BlogDTO blogDTO = BlogDTOHelper.buildDTOFromBlog(blog);
            blogDTOList.add(blogDTO);
        });
        return blogDTOList;
    }

    @Override
    public BlogDTO getBlogById(Long blogId) {
        logger.info("Retrieving all the blogs with Id : " + blogId);
        Optional<Blog> blog = blogRepository.findById(blogId);
        if(blog.isEmpty()) {
            throw new ResourceNotFoundException("Blog with Id " + blogId + " not found.", 404);
        }
        return BlogDTOHelper.buildDTOFromBlog(blog.get());
    }

    @Override
    public BlogsData getAllBlogs(Integer page, Integer size, String sortField, String sortDirection) {
        logger.info("Retrieving all the blogs at page & size : " + page + " " + size);
        //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/
        if(size<=0) size = Integer.parseInt(GlobalConstants.DEFAULT_PAGE_SIZE);
        if(page<0) page= Integer.parseInt(GlobalConstants.DEFAULT_STARTING_PAGE);
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.valueOf(sortDirection), sortField);

        Page<Blog> pageableBlogs = blogRepository.findAll(pageable);
        List<Blog> blogList = pageableBlogs.getContent();

        if(blogList.size() == 0) {
            throw new ResourceNotFoundException("No Blogs found in the server.", 404);
        }
        BlogsData blogsData = new BlogsData();
        List<BlogDTO> blogDTOList = new ArrayList<>();
        blogList.forEach((blog) -> {
            BlogDTO blogDTO = BlogDTOHelper.buildDTOFromBlog(blog);
            blogDTOList.add(blogDTO);
        });

        blogsData.setBlogs(blogDTOList);
        blogsData.setLastPage(pageableBlogs.isLast());
        blogsData.setTotalPages(pageableBlogs.getTotalPages());
        blogsData.setTotalElements(pageableBlogs.getTotalElements());
        blogsData.setCurrentPage(pageableBlogs.getNumber());
        return blogsData;
    }

    @Override
    public BlogDTO createBlog(Blog blog, Long customerUserId, Long blogCategoryId) {
        logger.info("Creating blog " + blog.getBlogTitle());

        Optional<BlogCategory> blogCategory  = blogCategoryRepository.findById(blogCategoryId);
        if(!blogCategory.isPresent()) {
            throw new ResourceNotFoundException("The category " + blogCategoryId + " doesn't exist.", 404);
        }

        Optional<CustomerUser> customerUser  = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("This customer user " + customerUserId + " doesn't exist.", 404);
        }
        if(blog.getBlogImageURL() == null || blog.getBlogImageURL().length() <3) {
            blog.setBlogImageURL(GlobalConstants.DEFAULT_FEATURED_IMAGE_URL);
        }
        blog.setCustomerUser(customerUser.get());
        blog.setBlogCategory(blogCategory.get());

        Blog createdBlog = blogRepository.save(blog);
        return BlogDTOHelper.buildDTOFromBlog(createdBlog);
    }

    @Override
    public BlogDTO updateBlog(Blog blog, Long customerUserId, Long blogId) {
        Optional<Blog> existingBlog  = blogRepository.findById(blogId);
        if(!existingBlog.isPresent()) {
            throw new ResourceNotFoundException("The blog " + blogId + " doesn't exist.", 404);
        }
        Optional<CustomerUser> customerUser  = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("This customer user " + customerUserId + " doesn't exist.", 404);
        }
        if(existingBlog.get().getCustomerUser().getId() != customerUserId) {
            throw new UnauthorizedException("you're not allowed to edit this blog." , 403);
        }
        blog.setCustomerUser(customerUser.get());
        blog.setBlogCategory(existingBlog.get().getBlogCategory());

        Blog updatedBlog = blogRepository.save(blog);
        return BlogDTOHelper.buildDTOFromBlog(updatedBlog);
    }

    @Override
    public Boolean deleteBlog(Long customerUserId, Long blogId) {
        logger.info("Deleting the blog with Id : " + blogId);
        Optional<CustomerUser> customerUser  = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("This customer user " + customerUserId + " doesn't exist.", 404);
        }

        Optional<Blog> existingBlog  = blogRepository.findById(blogId);
        if(!existingBlog.isPresent()) {
            throw new ResourceNotFoundException("This blog with ID  " + blogId + " doesn't exist.", 404);
        }
        if(existingBlog.get().getCustomerUser().getId() != customerUserId) {
            throw new UnauthorizedException("you're not allowed to delete this blog." , 403);
        }

        blogRepository.delete(existingBlog.get());
        return true;
    }

    @Override
    public List<BlogDTO> searchBlogsBySearchTerm(String searchTerm) {
        System.out.println("Searching blogs with query : " + searchTerm);
        List<Blog> blogList = blogRepository.searchBlogsBySearchTerm("%" + searchTerm.toLowerCase() + "%");
        if(blogList.size() == 0) {
            logger.info("No results found for search term : " + searchTerm);
            throw new ResourceNotFoundException("No Blogs found in the server.", 404);
        }
        List<BlogDTO> blogDTOList = new ArrayList<>();
        blogList.forEach((blog) -> {
            BlogDTO blogDTO = BlogDTOHelper.buildDTOFromBlog(blog);
            blogDTOList.add(blogDTO);
        });
        logger.debug(blogDTOList.size() + "results found.");
        return blogDTOList;
    }

    @Override
    public BlogDTO uploadFeaturedImageForBlog(Long blogId, MultipartFile file) {
        String filename;
        Optional<Blog> blog = blogRepository.findById(blogId);
        if(!blog.isPresent()) {
            throw new ResourceNotFoundException("No Blogs found in the server with ID " + blogId, 404);
        }

        try {
            File destinationFile = new File(GlobalConstants.BLOG_FEATURED_IMAGE_UPLOAD_DIR);
            filename = GlobalConstants.BLOG_FEAUTRED_IMAGE + blog.get().getId() + "_" +
                    "_" + file.getSize() + "_" + file.getName() + "_" + file.getOriginalFilename() ;
            Path path = Paths.get(destinationFile.getAbsolutePath() + File.separator + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException exception) {
            logger.info("Something went wrong while uploading featured image : " + exception.getMessage());
            throw new ResourceAlreadyExistsException("Something went wrong while uploading featured image : " + exception.getMessage(), 500);
        }
        blog.get().setBlogImageURL(filename);
        Blog savedBlog = blogRepository.save(blog.get());
        logger.info("Image " + file.getOriginalFilename() + " uploaded successfully.");
        return BlogDTOHelper.buildDTOFromBlog(savedBlog);
    }

    // REF : https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    @Override
    public String getImageURLByImageName(String imageName) {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("uploads/blog_featured_images/")
                .path(imageName)
                .toUriString();
        return fileDownloadUri;
    }
}
