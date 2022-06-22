package com.amigos.spring.blog.dtos;

import com.amigos.spring.blog.models.CustomerUser;
import com.amigos.spring.blog.models.Role;
import com.amigos.spring.blog.utils.GlobalConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(GlobalConstants.REDIS_BLOGS_HASH) // we're getting response as list of BlogDTO
public class CustomerUserDTO implements Serializable {
    private static final Long serialVersionUID = 0L;
    private String name;
    private String email;
    private String password;
    private String intro;
    private Date createdDate;
    private Date deletedDate;
    private String profileImageURL;
    private List<RoleDTO> roles;
}
