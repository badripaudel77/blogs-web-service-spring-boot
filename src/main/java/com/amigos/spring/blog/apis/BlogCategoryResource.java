package com.amigos.spring.blog.apis;

import com.amigos.spring.blog.dtos.BlogCategoryDTO;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.services.interfaces.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blogs/categories")
public class BlogCategoryResource {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @GetMapping("/list")
    public ResponseEntity<List<BlogCategoryDTO>> getAllBlogCategories() {
        List<BlogCategoryDTO> blogCategoryDTOList = blogCategoryService.getAllBlogCategories();
        return ResponseEntity.ok(blogCategoryDTOList);
    }

    @GetMapping("/get/{blogCategoryId}")
    public ResponseEntity<BlogCategoryDTO> getBlogCategoryDetails(@PathVariable("blogCategoryId") Long blogCategoryId) {
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.getSingleBlogCategory(blogCategoryId);
        return ResponseEntity.ok(blogCategoryDTO);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BlogCategoryDTO> createBlogCategory(@Valid @RequestBody BlogCategory blogCategory) {
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.createNewBlogCategory(blogCategory);
        return new ResponseEntity<>(blogCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{blogCategoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BlogCategoryDTO> updateBlogCategory(@Valid @RequestBody BlogCategory blogCategory, @PathVariable("blogCategoryId") Long blogCategoryId) {
        BlogCategoryDTO blogCategoryDTO = blogCategoryService.updateBlogCategory(blogCategory, blogCategoryId);
        return new ResponseEntity<>(blogCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{blogCategoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity delete(@PathVariable("blogCategoryId") Long blogCategoryId) {
        boolean isBlogCategoryDeleted = blogCategoryService.deleteBlogCategory(blogCategoryId);
        return new ResponseEntity(Map.of("isBlogCategoryDeleted", isBlogCategoryDeleted), HttpStatus.OK);
    }
}
