package com.amigos.spring.blog.utils;

public class GlobalConstants {
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final String DEFAULT_STARTING_PAGE = "0";
    public static final String DEFAULT_SORTING_FIELD = "createdDate";
    public static final String DEFAULT_SORTING_DIRECTION = "DESC";
    public static final String DEFAULT_PROFILE_IMAGE_URL = "default_user_profile.jpg";
    public static final String DEFAULT_FEATURED_IMAGE_URL = "default_featured_image.jpeg";
    public static final String BLOG_FEATURED_IMAGE_UPLOAD_DIR = "uploads/blog_featured_images/";
    public static final String CUSTOMER_USER_PROFILE_IMAGE_UPLOAD_DIR = "uploads/profile_images/";

    public static final String DEFAULT_USER_ROLE_NAME= "ROLE_USER";
    public static final String ADMIN_ROLE_NAME= "ROLE_ADMIN";
    public static final String SUPER_ADMIN_ROLE_NAME= "ROLE_SUPER_ADMIN";

    public static final Long DEFAULT_USER_ROLE_ID = 500L;
    public static final Long DEFAULT_AMDMIN_ROLE_ID = 501L;
    public static final Long DEFAULT_SUPER_AMDMIN_ROLE_ID = 502L;

    public static final String REDIS_BLOGS_HASH="BLOGS_REDIS_CACHE";

    public static final String CUSTOMER_USER_PROFILE_IMAGE_PREFIX = "app_customer_user_profile_image_";
    public static final String BLOG_FEAUTRED_IMAGE = "app_blog_featured_image_";
}
