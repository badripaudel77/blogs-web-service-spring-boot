package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.dtos.CommentDTO;
import com.amigos.spring.blog.models.Blog;

import java.util.ArrayList;
import java.util.List;

public class BlogDTOHelper {

    public static BlogDTO buildDTOFromBlog(Blog blog) {
        BlogDTO blogDTO = new BlogDTO();

        blogDTO.setBlogTitle(blog.getBlogTitle());
        blogDTO.setBlogDescription(blog.getBlogDescription());
        blogDTO.setBlogImageURL(blog.getBlogImageURL());
        blogDTO.setCreatedDate(blog.getCreatedDate());
        blogDTO.setCustomerUser(CustomerUserDTOHelper.buildDTOFromCustomerUser(blog.getCustomerUser()));
        blogDTO.setBlogCategory(BlogCategoryDTOHelper.buildDTOFromBlogCategory(blog.getBlogCategory()));
        List<CommentDTO> commentDTOList = new ArrayList<>();
        blog.getComments().forEach((comment) -> {
            CommentDTO commentDTO = CommentDTOHelper.buildDTOFromComment(comment);
            commentDTOList.add(commentDTO);
        });
        blogDTO.setComments(commentDTOList);
        return blogDTO;
    }
}
