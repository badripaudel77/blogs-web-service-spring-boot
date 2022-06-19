package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
