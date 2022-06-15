package com.amigos.spring.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogCategoryDTO {

    private String categoryName;
    private String categoryDescription;
    private Date createdDate;

}
