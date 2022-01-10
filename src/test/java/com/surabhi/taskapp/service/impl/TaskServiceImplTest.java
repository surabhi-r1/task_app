package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.entity.SubTaskEntity;
import com.surabhi.taskapp.entity.TaskEntity;
import com.surabhi.taskapp.repository.SubTaskRepository;
import com.surabhi.taskapp.repository.TaskRepository;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceImplTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    SubTaskRepository subTaskRepository;
    TaskServiceImpl taskService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.taskService = new TaskServiceImpl(taskRepository);
        System.out.println("running before each");
    }


    @Test
    void delete() {
        long id = 11;
        Mockito.doNothing().when(taskRepository).deleteById(id);
        Response<?> response = taskService.delete(id);
        assertEquals(HttpStatus.OK, response.getHttpStatus());

    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .name("aaaa")
                .description("aaaaa")
                .id(11L)

                .build();
        taskEntities.add(taskEntity);
        Page<TaskEntity> page1 = new PageImpl<>(taskEntities, pageable, 1L);
        Mockito.when(taskRepository.findAll(pageable)).thenReturn(page1);

        Response<PaginationResponse<List<Task>>> response1 = taskService.getAll(pageable);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
        assertEquals(1, response1.getResponse().getData().size());
        assertEquals("aaaa", response1.getResponse().getData().get(0).getName());
        assertEquals("aaaaa", response1.getResponse().getData().get(0).getDescription());
    }

    //
//        Pageable pageable = PageRequest.of(0, 20);
//        Response<PaginationResponse<List<Task>>> response = new Response<>();
//        response.setHttpStatus(HttpStatus.OK);


//        // mock outer class
//        Mockito.when(taskRepository.getAll(pageable));
//
//        //call actual method
//        Response<PaginationResponse<List<Task>>> taskService.getAll(pageable);
//
//
//        Response<PaginationResponse<List<Task>>> response = new Response<>();
//        response.setHttpStatus(HttpStatus.OK);
    // Page<TaskEntity> page=
    //PaginationResponse<List<Task>> paginationResponse = new PaginationResponse<>();

//        List<Task> tasks = new ArrayList<>();
//        Task task = Task.builder()
//                .name("aaa")
//                .description("aaaaa")
//                .build();
//        tasks.add(task);

    //  paginationResponse.setData(tasks);
    // paginationResponse.setSize(5);
    //  paginationResponse.setPage(1);
    //  response.setResponse(paginationResponse);

    // List<TaskEntity> taskEntities=TaskEntity.builder().build();


    @Test
    void add() {

        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        // List<Task> tasks= new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .id(11L)
                .name("anu")
                .build();
        Set<SubTaskEntity> subTaskEntitySet = new HashSet<>();
        SubTaskEntity subTaskEntity = SubTaskEntity.builder()
                .id(11L)
                .name("asha")
                .build();

        taskEntity.setSubTaskEntityList(subTaskEntitySet);

        Mockito.when(taskRepository.save(Mockito.any()))
                .thenReturn(null);


        Assertions.assertEquals(200, response.getHttpStatus());
    }

}