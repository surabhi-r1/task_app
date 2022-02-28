package com.surabhi.taskapp.repository.querydetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queries/task.queries.properties")
@Getter

public class TaskQueries {

    private final String findAll;

    public TaskQueries(@Value("${FIND_ALL_TASK}") String findAll) {
        this.findAll = findAll;
    }


}
