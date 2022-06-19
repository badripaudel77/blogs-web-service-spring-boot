package com.amigos.spring.blog.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment_description")
    @NotNull(message = "comment cannot be null.")
    @NotEmpty(message = "comment cannot be empty.")
    @Size(min = 3, max = 100, message = "comment must be between 3 & 100 characters long.")
    private String commentDescription;

    @Column(name = "commented_date")
    private Date commentedDate = new Date();

    // comment is related to which blog
    //@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @ManyToOne
    // @JoinColumn(name = "blog_id")
    private Blog blog;

    // who posted this comment
    // we don't need comments while
    @ManyToOne
    //@JoinColumn(name = "customer_user_id")
    private CustomerUser customerUser;

}
