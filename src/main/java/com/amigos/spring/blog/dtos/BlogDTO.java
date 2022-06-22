package com.amigos.spring.blog.dtos;

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
public class BlogDTO implements Serializable {
    private static final Long serialVersionUID = 0L;

    private String blogTitle;
    private String blogDescription;
    private String blogImageURL;
    private Date createdDate;
    private CustomerUserDTO customerUser;
    private BlogCategoryDTO blogCategory;
    private List<CommentDTO> comments;
}

