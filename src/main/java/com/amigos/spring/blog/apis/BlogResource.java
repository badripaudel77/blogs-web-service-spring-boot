package com.amigos.spring.blog.apis;

import com.amigos.spring.blog.dtos.BlogDTO;
import com.amigos.spring.blog.models.Blog;
import com.amigos.spring.blog.services.interfaces.BlogService;
import com.amigos.spring.blog.utils.BlogsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogResource {

    @Autowired
    private BlogService blogService;

    //https://www.baeldung.com/spring-request-param
    @GetMapping("")
    public BlogsData getAllBlogs(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                 @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
                                 @RequestParam(name= "sort", defaultValue = "createdDate") String sortField,
                                 @RequestParam(name= "direction", defaultValue = "DESC") String sortDirection
                                     ) {
        return blogService.getAllBlogs(page, size, sortField, sortDirection);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable("blogId") Long blogId) {
        BlogDTO blogsById = blogService.getBlogById(blogId);
        return new ResponseEntity<>(blogsById, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/{blogId}")
    public ResponseEntity<Map> deleteBlogById(@PathVariable("userId") Long userId, @PathVariable("blogId") Long blogId) {
        Boolean isDeleted = blogService.deleteBlog(userId, blogId);
        return new ResponseEntity<>(Map.of("isBlogDeleted", isDeleted), HttpStatus.OK);
    }
    @GetMapping("/categories/{blogCategoryId}")
    public ResponseEntity<List<BlogDTO>> getAllBlogsByBlogCategoryId(@PathVariable("blogCategoryId") Long blogCategoryId) {
        List<BlogDTO> allByBlogCategory = blogService.getAllByBlogCategory(blogCategoryId);
        return new ResponseEntity<>(allByBlogCategory, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogDTO>> getAllBlogsByUserId(@PathVariable("userId") Long customerUserId) {
        List<BlogDTO> allBlogsByUser = blogService.getAllBlogsByUser(customerUserId);
        return new ResponseEntity<>(allBlogsByUser, HttpStatus.OK);
    }

    @PostMapping("/create/{userId}/{categoryId}")
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody Blog blog, @PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {
        BlogDTO createdBlogDTO = blogService.createBlog(blog, userId, categoryId);
        return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}/{blogId}")
    public ResponseEntity<BlogDTO> updateBlog(@Valid @RequestBody Blog blog, @PathVariable("userId") Long userId, @PathVariable("blogId") Long blogId) {
        BlogDTO blogDTO = blogService.updateBlog(blog, userId, blogId);
        return new ResponseEntity<>(blogDTO, HttpStatus.CREATED);
    }
}