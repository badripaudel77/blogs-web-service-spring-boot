package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.utils.BlogsData;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

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
        Optional<Blog> blog = blogRepository.findById(blogId);
        if(blog.isEmpty()) {
            throw new ResourceNotFoundException("Blog with Id " + blogId + " not found.", 404);
        }
        return BlogDTOHelper.buildDTOFromBlog(blog.get());
    }

    @Override
    public BlogsData getAllBlogs(Integer page, Integer size, String sortField, String sortDirection) {
        //https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/
        if(size<=0) size = 5;
        if(page<0) page= 0;
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
        Optional<BlogCategory> blogCategory  = blogCategoryRepository.findById(blogCategoryId);
        if(!blogCategory.isPresent()) {
            throw new ResourceNotFoundException("The category " + blogCategoryId + " doesn't exist.", 404);
        }

        Optional<CustomerUser> customerUser  = customerUserRepository.findById(customerUserId);
        if(!customerUser.isPresent()) {
            throw new ResourceNotFoundException("This customer user " + customerUserId + " doesn't exist.", 404);
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
    public List<BlogDTO> searchBlogs(String searchTerm) {
        return null;
    }
}
