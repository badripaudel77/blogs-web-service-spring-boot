package com.amigos.spring.blog.dtos;

import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.models.Role;
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
public class CustomerUserDTO {

    private String name;
    private String email;
    private String password;
    private String intro;
    private Date createdDate;
    private Date deletedDate;
    private String profileImageURL;
    private List<RoleDTO> roles;
}
