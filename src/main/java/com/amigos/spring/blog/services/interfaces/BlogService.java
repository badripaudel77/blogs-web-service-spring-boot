package com.amigos.spring.blog.services.interfaces;

import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.utils.BlogsData;

import java.util.List;

public interface BlogService {

    List<BlogDTO> getAllBlogsByUser(Long customerUserId);

    List<BlogDTO> getAllByBlogCategory(Long blogCategoryId);

    BlogDTO getBlogById(Long blogId);

    BlogsData getAllBlogs(Integer page, Integer size, String sortField, String sortDirection); // maybe useful for admin users

    BlogDTO createBlog(Blog blog, Long customerUserId, Long blogCategoryId);

    BlogDTO updateBlog(Blog blog, Long customerUserId, Long blogCategoryId);

    Boolean deleteBlog(Long blogId, Long customerUserId);

    List<BlogDTO> searchBlogsBySearchTerm(String searchTerm);

}
