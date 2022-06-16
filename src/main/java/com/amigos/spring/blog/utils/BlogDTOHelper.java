package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.BlogCategoryDTO;
import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.models.BlogCategory;

public class BlogDTOHelper {

    public static BlogDTO buildDTOFromBlog(Blog blog) {
        BlogDTO blogDTO = new BlogDTO();

        blogDTO.setBlogTitle(blog.getBlogTitle());
        blogDTO.setBlogDescription(blog.getBlogDescription());
        blogDTO.setBlogImageURL(blog.getBlogImageURL());
        blogDTO.setCreatedDate(blog.getCreatedDate());
        blogDTO.setCustomerUser(CustomerUserDTOHelper.buildDTOFromCustomerUser(blog.getCustomerUser()));
        blogDTO.setBlogCategory(BlogCategoryDTOHelper.buildDTOFromBlogCategory(blog.getBlogCategory()));
        return blogDTO;
    }
}
