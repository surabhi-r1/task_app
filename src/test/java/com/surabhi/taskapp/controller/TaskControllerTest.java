package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class TaskControllerTest {
    @Mock
    TaskServiceImpl taskService;

    TaskController taskController;


    @BeforeEach
    void multiply() {
        MockitoAnnotations.openMocks(this);
        this.taskController = new TaskController(taskService);
        System.out.println("running before each");
    }

    @Test
    void getTaskById() {
        Response<Task> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);

        Task task = Task.builder()
                .id(11)
                .description("aaa")
                .name("aa")
                .build();
        response.setResponse(task);
        long id = 11;
        Mockito.when(taskService.getById(id)).thenReturn(response);
        ResponseEntity<Task> responseEntity = taskController.getTaskById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void add() {
        Response response = new Response<>();
        response.setHttpStatus(CREATED);
        Task task = Task.builder()
                .description("aaa")
                .name("aa")
                .build();
        Mockito.when(taskService.add(task)).thenReturn(response);
        ResponseEntity<?> responseEntity = taskController.addTask(task);
        assertEquals(CREATED, responseEntity.getStatusCode());

    }

    @Test
    void delete() {
        Response response = new Response<>();
        response.setHttpStatus(OK);
        long id = 11;
        Mockito.when(taskService.delete(id)).thenReturn(response);
        ResponseEntity<?> responseEntity = taskController.deleteTask(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void update() {
        Response<Task> response = new Response<>();
        {
            response.setHttpStatus(OK);
            Task task = Task.builder()
                    .description("aaa")
                    .name("aa")
                    .build();
            long id = 11;
            Mockito.when(taskService.update(id, task)).thenReturn(response);
            ResponseEntity<Task> responseEntity = taskController.updateTask(task, id);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    /*
     * testing the bulk update api = /sov/table/v2 method = POST
     * given accountId, query, startDate, endDate, sort, aggregation, pageable
     * then returns ResponseEntity<PaginationResponse<Table>>
     * scenario SUCCESS
     */
    @Test
    void getAll() {
        // form response or request
        Pageable pageable = PageRequest.of(0, 20);

        Response<PaginationResponse<List<Task>>> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        PaginationResponse<List<Task>> paginationResponse = new PaginationResponse<>();
        List<Task> tasks = new ArrayList<>();
        Task task = Task.builder()
                .name("aaa")
                .description("aaaaa")
                .build();

        tasks.add(task);
        paginationResponse.setData(tasks);
        paginationResponse.setSize(5);
        paginationResponse.setPage(1);
        response.setResponse(paginationResponse);


        // mock outer class
        Mockito.when(taskService.getAll(pageable))
                .thenReturn(response);


        //call actual method
        ResponseEntity<PaginationResponse<List<Task>>> responseEntity = taskController.getAllTasks(pageable);

        //assert;
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getData().size());
        assertEquals("aaa", responseEntity.getBody().getData().get(0).getName());
        assertEquals("aaaaa", responseEntity.getBody().getData().get(0).getDescription());
    }


}