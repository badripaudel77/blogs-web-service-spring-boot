package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.CommentDTO;
import com.amigos.spring.blog.models.Comment;

public class CommentDTOHelper {
        public static CommentDTO buildDTOFromComment(Comment comment) {
            CommentDTO commentDTO = new CommentDTO();

            commentDTO.setCcommentDescription(comment.getCommentDescription());
            commentDTO.setCommentedDate(comment.getCommentedDate());
            commentDTO.setCustomerUserId(comment.getCustomerUser().getId());
            commentDTO.setBlogId(comment.getBlog().getId());
            return commentDTO;
        }
}
