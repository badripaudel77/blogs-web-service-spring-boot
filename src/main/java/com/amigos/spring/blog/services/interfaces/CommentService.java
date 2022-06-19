package com.amigos.spring.blog.services.interfaces;

import com.amigos.spring.blog.dtos.CommentDTO;
import com.amigos.spring.blog.models.Comment;

public interface CommentService {
    CommentDTO addComment(Long blogId, Comment comment, Long userId);
    Boolean deleteComment(Long blogId, Long userid, Long commentId);
}
