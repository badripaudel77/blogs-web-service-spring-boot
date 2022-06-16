package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Just works like in grails
    List<Blog> findByCustomerUser(Optional<CustomerUser> customerUser);

    List<Blog> findByBlogCategory(Optional<BlogCategory> blogCategory);
}
