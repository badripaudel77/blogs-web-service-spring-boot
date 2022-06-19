package com.amigos.spring.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "blog_title")
    @NotEmpty(message = "Blog Title can't be empty")
    @Size(min = 10, max = 100, message = "Title should be at least 10 not exceeding 100 characters.")
    private String blogTitle;

    @Column(name = "blog_description")
    @Size(min = 10, max = 1000, message = "Content should be at least 10 not exceeding 1000 characters.")
    private String blogDescription;

    @Column(name = "blog_img_url")
    private String blogImageURL;

    @Column(name = "blog_created_date")
    private Date createdDate = new Date();


    // Blog is created by user
    @ManyToOne
    // @JoinColumn(name = "customer_user_id") give name if want to change, otherwise spring will create
    private CustomerUser customerUser;

    // Blog has category
    @ManyToOne
    // @JoinColumn(name = "blog_category_id")
    private BlogCategory blogCategory;

    // blog has list of comments
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

}
