package com.amigos.spring.blog.utils;

import com.amigos.spring.blog.AmigosJavaBlogApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    private static Logger logger = LoggerFactory.getLogger(AmigosJavaBlogApplication.class);

    public static Logger getMyLogger() {
        return logger;
    }
}
