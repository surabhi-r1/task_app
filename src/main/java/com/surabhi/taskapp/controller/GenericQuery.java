package com.surabhi.taskapp.controller;


import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.mapper.UserMapper;
import com.surabhi.taskapp.repository.UserJdbcRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/genericQuery")
public class GenericQuery {


    private final UserJdbcRepository userJdbcRepository;

    public GenericQuery(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    @GetMapping
    public List<User> getAllUser(@RequestParam("query") String queryName) {
        return userJdbcRepository.executeQuery(null, queryName, new UserMapper());
    }
}
