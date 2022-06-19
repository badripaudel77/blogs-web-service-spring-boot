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
public class CommentDTO {
    private String ccommentDescription;
    private Date commentedDate;
    private Long blogId;
    private Long customerUserId;
}
