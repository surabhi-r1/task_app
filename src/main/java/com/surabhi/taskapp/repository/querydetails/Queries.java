package com.surabhi.taskapp.repository.querydetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queries/queries.properties")
@Getter
public class Queries {

    private final String findAll;

    public Queries(@Value("${FIND_ALL_USER}") String findAll) {
        this.findAll = findAll;
    }
}
