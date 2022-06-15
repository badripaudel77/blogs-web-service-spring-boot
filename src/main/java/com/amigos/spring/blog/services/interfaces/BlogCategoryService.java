package com.amigos.spring.blog.services.interfaces;

import com.amigos.spring.blog.dtos.BlogCategoryDTO;
import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.models.CustomerUser;

import java.util.List;

public interface BlogCategoryService {

    List<BlogCategoryDTO> getAllBlogCategories();

    BlogCategoryDTO createNewBlogCategory(BlogCategory blogCategory);

    BlogCategoryDTO updateBlogCategory(BlogCategory blogCategory, Long blogCategoryId);

    BlogCategoryDTO getSingleBlogCategory(Long blogCategoryId);

    boolean deleteBlogCategory(Long blogCategoryId);
}
