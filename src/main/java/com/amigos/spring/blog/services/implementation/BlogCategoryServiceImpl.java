package com.amigos.spring.blog.services.implementation;

import com.amigos.spring.blog.dtos.BlogCategoryDTO;
import com.amigos.spring.blog.exceptions.ResourceAlreadyExistsException;
import com.amigos.spring.blog.exceptions.ResourceNotFoundException;
import com.amigos.spring.blog.models.BlogCategory;
import com.amigos.spring.blog.repositories.BlogCategoryRepository;
import com.amigos.spring.blog.services.interfaces.BlogCategoryService;
import com.amigos.spring.blog.utils.BlogCategoryDTOHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Override
    public List<BlogCategoryDTO> getAllBlogCategories() {
        List<BlogCategory> allBlogCategories = blogCategoryRepository.findAll();
        if(allBlogCategories.size() == 0)
            throw new ResourceNotFoundException("No Categories Found", 404);
        List<BlogCategoryDTO> blogCategoryDTOList  = new ArrayList<>();

        allBlogCategories.forEach((blogCategory -> {
            BlogCategoryDTO blogCategoryDTO = BlogCategoryDTOHelper.buildDTOFromBlogCategory(blogCategory);
            blogCategoryDTOList.add(blogCategoryDTO);
        }));
        return blogCategoryDTOList;
    }

    @Override
    public BlogCategoryDTO createNewBlogCategory(BlogCategory blogCategory) {
        Optional<BlogCategory> existingBlogCategory = blogCategoryRepository.findByCategoryName(blogCategory.getCategoryName());
        if(existingBlogCategory.isPresent())
            throw  new ResourceAlreadyExistsException("Category with name "+ blogCategory.getCategoryName() + " already exists.", 500);

        BlogCategory category = blogCategoryRepository.save(blogCategory);
        BlogCategoryDTO blogCategoryDTO = BlogCategoryDTOHelper.buildDTOFromBlogCategory(category);
        return blogCategoryDTO;
    }

    @Override
    public BlogCategoryDTO updateBlogCategory(BlogCategory blogCategory, Long blogCategoryId) {
        BlogCategory category = blogCategoryRepository.findById(blogCategoryId).orElseThrow(() -> new ResourceNotFoundException("Category with ID "+ blogCategoryId + " not found.", 404));

        category.setCategoryName(blogCategory.getCategoryName());
        category.setCategoryDescription(blogCategory.getCategoryDescription());

        BlogCategory savedBlogCategory = blogCategoryRepository.save(category);
        return BlogCategoryDTOHelper.buildDTOFromBlogCategory(savedBlogCategory);
    }

    @Override
    public BlogCategoryDTO getSingleBlogCategory(Long blogCategoryId) {
        BlogCategory blogCategory = blogCategoryRepository.findById(blogCategoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id "+ blogCategoryId + " not found.", 404));
        return BlogCategoryDTOHelper.buildDTOFromBlogCategory(blogCategory);
    }

    @Override
    public boolean deleteBlogCategory(Long blogCategoryId) {
        BlogCategory blogCategory = blogCategoryRepository.findById(blogCategoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id "+ blogCategoryId + " not found.", 404));
        blogCategoryRepository.delete(blogCategory);
        return true;
    }
}
