package com.amigos.spring.blog.dtos;

import com.amigos.spring.blog.utils.GlobalConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(GlobalConstants.REDIS_BLOGS_HASH) // we're getting response as list of BlogDTO
public class BlogCategoryDTO implements Serializable {
    private static final Long serialVersionUID = 0L;
    private String categoryName;
    private String categoryDescription;
    private Date createdDate;

}
