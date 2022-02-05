package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.mapper.TaskMapper;
import com.surabhi.taskapp.repository.TaskJdbcRepository;
import com.surabhi.taskapp.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericTaskServiceImplTest {
    @Mock
    TaskJdbcRepository taskJdbcRepository;
    GenericTaskServiceImpl genericTaskService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.genericTaskService = new GenericTaskServiceImpl(taskJdbcRepository);
        System.out.println("running before each");
    }

    @Test
    void findAll(){
        Response<List<Task>> response=new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        List<Task> tasks=new ArrayList<>();
        String queryName="findAll";
        Mockito.when(taskJdbcRepository.executeQuery(null, queryName, new TaskMapper())).thenReturn(tasks);
        Response<List<Task>> response1=genericTaskService.getAll(queryName);
        assertEquals(HttpStatus.OK,response1.getHttpStatus());

    }


}