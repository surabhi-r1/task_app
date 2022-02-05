package com.surabhi.taskapp.repository.querydetails;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:queries/task1.queries.properties")
@Getter

public class TaskQueries1 {
    private final String findById;

    public TaskQueries1(@Value("${FIND_ALL_TASK_BY_ID}") String findById) {

        this.findById = findById;
    }
}
