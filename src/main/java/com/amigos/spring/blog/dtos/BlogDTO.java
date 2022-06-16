package com.amigos.spring.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private String blogTitle;
    private String blogDescription;
    private String blogImageURL;
    private Date createdDate;
    private CustomerUserDTO customerUser;
    private BlogCategoryDTO blogCategory;
}

