package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.BlogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BlogsData {
        List<BlogDTO> blogs;
        Integer totalPages;
        Integer currentPage;
        Long totalElements;
        boolean isLastPage;
}
