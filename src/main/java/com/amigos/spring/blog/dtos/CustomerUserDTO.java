package com.amigos.spring.blog.dtos;

import com.amigos.spring.blog.models.CustomerUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserDTO {

    private String name;
    private String email;
    private String password;
    private String intro;
    private Date createdDate;
    private Date deletedDate;
    private String profileImageURL;
}
