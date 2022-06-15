package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.BlogCategoryDTO;
import com.amigos.spring.blog.dtos.CustomerUserDTO;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.models.CustomerUser;

public class BlogCategoryDTOHelper {

    public static BlogCategoryDTO buildDTOFromBlogCategory(BlogCategory blogCategory) {
        BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();

        blogCategoryDTO.setCategoryName(blogCategory.getCategoryName());
        blogCategoryDTO.setCategoryDescription(blogCategory.getCategoryDescription());
        blogCategoryDTO.setCreatedDate(blogCategory.getCreatedDate());
        return blogCategoryDTO;
    }
}
