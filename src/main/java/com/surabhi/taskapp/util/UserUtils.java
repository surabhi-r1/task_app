package com.surabhi.taskapp.util;

import com.surabhi.taskapp.UserDetail;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
@Service
public class UserUtils {
    private final ServletContext servletContext;

    public UserUtils(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public UserDetail getUser(){
        return (UserDetail) servletContext.getAttribute("user");
    }
}
