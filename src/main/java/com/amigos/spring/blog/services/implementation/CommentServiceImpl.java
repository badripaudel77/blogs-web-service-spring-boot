package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.CommentDTO;
import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.exceptions.UnauthorizedException;
import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.models.Comment;
import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.repositories.BlogRepository;
import com.amigos.spring.blog.repositories.CommentRepository;
import com.amigos.spring.blog.repositories.CustomerUserRepository;
import com.amigos.spring.blog.services.interfaces.CommentService;
import com.amigos.spring.blog.utils.CommentDTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CustomerUserRepository customerUserRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public CommentDTO addComment( Long blogId, Comment comment, Long userId) {
        Blog blogById = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog doesn't exist.", 404));
        CustomerUser customerUser = customerUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist.", 404));
        comment.setCustomerUser(customerUser);
        comment.setBlog(blogById);
        Comment savedComment = commentRepository.save(comment);
        return CommentDTOHelper.buildDTOFromComment(savedComment);
    }

    @Override
    public Boolean deleteComment(Long blogId, Long userId, Long commentId) {
        blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog doesn't exist.", 404));
        customerUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Customer User doesn't exist with Id "+ userId, 404));

        Optional<Comment> comment = commentRepository.findById(commentId);
        if(!comment.isPresent()) {
            throw new  ResourceNotFoundException("Comment with comment ID :" + commentId + " doesn't exist.", 404);
        }
        if((comment.get().getBlog().getId() != blogId) || (comment.get().getCustomerUser().getId() != userId)) {
            throw new UnauthorizedException("You're not allowed to delete this comment.",403);
        }
        this.commentRepository.delete(comment.get());
        return true;
    }
}
