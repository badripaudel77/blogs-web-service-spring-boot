package com.amigos.spring.blog.repositories;

import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.models.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // REF : https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

    // Just works like in grails
    List<Blog> findByCustomerUser(Optional<CustomerUser> customerUser);

    List<Blog> findByBlogCategory(Optional<BlogCategory> blogCategory);

    /*
    - Write way complex query here to make search results more realistic.
     */
    @Query("select b from Blog b where lower(b.blogTitle) like :searchTerm or lower(b.blogDescription) like :searchTerm or lower(b.blogCategory.categoryName) like :searchTerm")
    List<Blog> searchBlogsBySearchTerm(@Param("searchTerm") String query);
}
