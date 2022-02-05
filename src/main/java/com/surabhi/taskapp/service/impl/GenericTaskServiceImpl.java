package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.entity.TaskEntity;
import com.surabhi.taskapp.entity.UserEntity;
import com.surabhi.taskapp.mapper.TaskMapper;
import com.surabhi.taskapp.repository.TaskJdbcRepository;
import com.surabhi.taskapp.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GenericTaskServiceImpl implements GenericService {
    @Autowired
    private final TaskJdbcRepository taskJdbcRepository;


    public GenericTaskServiceImpl(TaskJdbcRepository taskJdbcRepository) {
        this.taskJdbcRepository = taskJdbcRepository;
    }

    public Response<List<Task>> getAll(String queryName) {
        Response<List<Task>> response = new Response<>();
        try {

            log.info("operation = getAll, status = IN_PROGRESS, message = get all");
            List<Task> tasks = taskJdbcRepository.executeQuery(null, queryName, new TaskMapper());
            log.info("operation = getAll, status = SUCCESS, message = get all");
            response.setHttpStatus(HttpStatus.OK);
            response.setResponse(tasks);
            return response;

        } catch (Exception e) {
            log.error("operation = getAll, status = ERROR, msg = error in getAll", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return response;
    }
}
