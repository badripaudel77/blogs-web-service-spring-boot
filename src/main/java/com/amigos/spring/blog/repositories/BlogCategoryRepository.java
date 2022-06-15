package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {

    Optional<BlogCategory> findByCategoryName(String categoryName);
}
