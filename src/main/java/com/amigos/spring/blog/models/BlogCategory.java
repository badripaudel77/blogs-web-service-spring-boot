package com.amigos.spring.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "blog_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name")
    @NotEmpty(message = "Name of the category can't be empty")
    @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters.")
    private String categoryName;

    @Column(name = "category_description")
    @NotEmpty(message = "Please provide what this category is about")
    private String categoryDescription;

    @Column(name = "category_created_data")
    private Date createdDate = new Date();
}
