package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.dtos.BlogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@RedisHash(GlobalConstants.REDIS_BLOGS_HASH) // we're getting response as list of BlogDTO
public class BlogsData implements Serializable {
        private static final Long serialVersionUID = 0L;
        List<BlogDTO> blogs;
        Integer totalPages;
        Integer currentPage;
        Long totalElements;
        boolean isLastPage;
}
