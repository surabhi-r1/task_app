package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.SubTask;
import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.impl.TaskServiceImpl;
import com.surabhi.taskapp.util.UserUtils;
import liquibase.pro.packaged.R;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class TaskControllerTest {
    @Mock
    TaskServiceImpl taskService;
    @Mock
    UserUtils userUtils;

    TaskController taskController;


    @BeforeEach
    void multiply() {
        MockitoAnnotations.openMocks(this);
        this.taskController = new TaskController(taskService);
        System.out.println("running before each");
    }

    @Test
    void getTaskById() {
        Response response = Response.builder()
                .httpStatus(OK)
                .build();

        Task task = Task.builder()
                .id(11)
                .description("aaaa")
                .name("aaa")
                .build();
        response.setResponse(task);
        long id = 11;
        Mockito.when(taskService.getById(id))
                .thenReturn(response);

        ResponseEntity<Task> responseEntity = taskController.getTaskById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("aaa", responseEntity.getBody().getName());
        assertEquals("aaaa", responseEntity.getBody().getDescription());
        assertEquals(11, responseEntity.getBody().getId());
    }

    @Test
    void add() {
        Response response = new Response<>();
        response.setHttpStatus(CREATED);
        Task task = Task.builder()
                .description("aaa")
                .name("aa")
                .build();

        Mockito.when(taskService.add(task))
                .thenReturn(response);

        ResponseEntity<?> responseEntity = taskController.addTask(task);

        assertEquals(CREATED, responseEntity.getStatusCode());

    }

    @Test
    void delete() {
        Response response = new Response<>();
        response.setHttpStatus(OK);
        long id = 11;

        Mockito.when(taskService.delete(id))
                .thenReturn(response);

        ResponseEntity<?> responseEntity = taskController.deleteTask(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void update() {
        Response response = new Response<>();
        response.setHttpStatus(OK);
        Task task = Task.builder()
                .description("aaa")
                .name("aa")
                .build();
        long id = 11;

        Mockito.when(taskService.update(id, task))
                .thenReturn(response);

        ResponseEntity<?> responseEntity = taskController.updateTask(task, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAllDetailsByUserId() {
        Response<List<Task>> response = new Response<>();
        Set<SubTask> subTasks = new HashSet<>();
        SubTask subTask = SubTask
                .builder()
                .id(11l)
                .name("aa")
                .description("aaaaa")
                .build();
        subTasks.add(subTask);

        List<Task> tasks = new ArrayList<>();
        Task task = Task.builder()
                .id(88)
                .name("aaa")
                .description("aaaaa")
                .subTasks(subTasks)
                .build();

        task.setSubTasks(subTasks);
        tasks.add(task);

        response.setHttpStatus(HttpStatus.OK);
        response.setResponse(tasks);

        Mockito.when(taskService.getAllByUserId()).thenReturn(response);
        ResponseEntity<List<Task>> responseEntity = taskController.getAllDetailsByUserId();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("aaa", responseEntity.getBody().get(0).getName());
    }

    @Test
    void updateMany() {
        Response response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        List<Task> task=new ArrayList<>();
        Task task1=Task.builder()
                .name("java")
                .description("desc")
                .build();
        task.add(task1);
        Mockito.when(taskService.updateMany(task)).thenReturn(response);
        ResponseEntity responseEntity=taskController.updateMany(task);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}