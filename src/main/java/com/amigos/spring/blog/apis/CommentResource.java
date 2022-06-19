package com.amigos.spring.blog.apis;

import com.amigos.spring.blog.dtos.CommentDTO;
import com.amigos.spring.blog.models.Comment;
import com.amigos.spring.blog.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestControllerAdvice
@RequestMapping("/api/v1/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{blogId}/create/{customerUserId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("blogId") Long blogId,
                                                    @RequestBody @Valid Comment comment,
                                                    @PathVariable("customerUserId") Long customerUserId) {

        CommentDTO commentDTO = commentService.addComment(blogId, comment, customerUserId);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{blogId}/delete/{userId}/{commentId}")
    public ResponseEntity<Map> removeComment(@PathVariable("blogId") Long blogId,
                                             @PathVariable("userId") Long userId,
                                             @PathVariable("commentId") Long commentId) {
        Boolean isCommentDeleted = commentService.deleteComment(blogId, userId, commentId);
        return new ResponseEntity<>(Map.of("isCommentDeleted", isCommentDeleted), HttpStatus.OK);
    }

}
