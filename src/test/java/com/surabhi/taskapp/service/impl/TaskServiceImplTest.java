package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.UserDetail;
import com.surabhi.taskapp.dto.SubTask;
import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.entity.SubTaskEntity;
import com.surabhi.taskapp.entity.TaskEntity;

import com.surabhi.taskapp.repository.TaskRepository;

import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.util.UserUtils;
import liquibase.pro.packaged.L;
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
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskServiceImplTest {
    @Mock
    TaskRepository taskRepository;
    @Mock
    UserUtils userUtils;


    TaskServiceImpl taskService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.taskService = new TaskServiceImpl(taskRepository, userUtils);
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
    void deleteError() {
        long id = 11;
        Mockito.doThrow(new RuntimeException())
                .when(taskRepository).deleteById(id);

        Response<?> response = taskService.delete(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getHttpStatus());

    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);

        Set<SubTaskEntity> subTaskEntities = new HashSet<>();
        SubTaskEntity subTaskEntity = SubTaskEntity.builder()
                .id(11L)
                .build();
        subTaskEntities.add(subTaskEntity);

        List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .name("aaaa")
                .description("aaaaa")
                .subTaskEntityList(subTaskEntities)
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

    @Test
    void getAllError() {
        Pageable pageable = PageRequest.of(0, 20);


        Mockito.when(taskRepository.findAll(pageable))
                .thenThrow(new RuntimeException(""));


        Response<PaginationResponse<List<Task>>> response1 = taskService.getAll(pageable);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getHttpStatus());
    }


    @Test
    void add() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.CREATED);

        Set<SubTask> subTasks = new HashSet<>();

        SubTask subTask = new SubTask();
        subTask.setName("aa");

        subTasks.add(subTask);


        Task task = Task.builder()
                .id(11L)
                .name("anu")
                .subTasks(subTasks)
                .userId(1)
                .build();
        List list = new ArrayList<>();
        UserDetail userDetail = new UserDetail("name", "password", "email", 1, list);
        Mockito.when(userUtils.getUser()).thenReturn(userDetail);

        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null);
        Response<?> response1 = taskService.add(task);
        assertEquals(HttpStatus.CREATED, response1.getHttpStatus());
    }

    @Test
    void addError() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.CREATED);

        Set<SubTask> subTasks = new HashSet<>();

        SubTask subTask = new SubTask();
        subTask.setName("aa");

        subTasks.add(subTask);


        Task task = Task.builder()
                .id(11L)
                .name("anu")
                .subTasks(subTasks)
                .build();

        Mockito.when(taskRepository.save(Mockito.any()))
                .thenReturn(new Exception(""));

        Response<?> response1 = taskService.add(task);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getHttpStatus());
    }


    @Test
    void getById() {
        Response<Task> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        TaskEntity taskEntity = TaskEntity.builder()
                .description("aaaa")
                .name("aaa")
                .id(11L)
                .build();
        long id = 11;

        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(taskEntity));

        Response<Task> response1 = taskService.getById(id);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
        assertEquals("aaa", response1.getResponse().getName());
        assertEquals("aaaa", response1.getResponse().getDescription());
        assertEquals(11, response1.getResponse().getId());
    }


    @Test
    void getByIdError() {
        Response<Task> response = new Response<>();
        long id = 11;

        Mockito.when(taskRepository.findById(id)).thenThrow(new RuntimeException(""));

        Response<Task> response1 = taskService.getById(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getHttpStatus());
        // assertEquals("aaa", response1.getResponse().getName());
        // assertEquals("aaaa", response1.getResponse().getDescription());
        //assertEquals(11, response1.getResponse().getId());
    }

    @Test
    void getByIdError1() {
        Response<Task> response = new Response<>();
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        TaskEntity taskEntity = TaskEntity.builder()
                .description("aaaa")
                .name("aaa")
                .id(11L)
                .build();
        long id = 11;

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.empty());

        Response<Task> response1 = taskService.getById(id);
        assertEquals(HttpStatus.NOT_FOUND, response1.getHttpStatus());
    }

//    @Test
//    void getNewById() {
//        Response response = new Response<>();
//        response.setHttpStatus(HttpStatus.OK);
//
//        List<Long> taskIds = new ArrayList<>();
//        taskIds.add(11L);
//
//        List<Long> userIds = new ArrayList<>();
//        userIds.add(1L);
//
//        List<UserEntity> userEntities = new ArrayList<>();
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("aaa");
//        userEntity.setId(1L);
//
//        userEntities.add(userEntity);
//
//        List<TaskEntity> taskEntities = new ArrayList<>();
//        TaskEntity taskEntity = TaskEntity.builder()
//                .id(11L)
//                .name("anu")
//                .build();
//        taskEntities.add(taskEntity);
//
//
//        List<TaskEntity> taskEntityList = new ArrayList<>();
//        taskEntityList.add(taskEntity);
//
//
//        List<Task> tasks = new ArrayList<>();
//        Task task = Task.builder()
//                .name("anu")
//                .id(11L)
//                .description("desc")
//                .userId(1L)
//                .build();
//        tasks.add(task);
//
//        Mockito.when(taskRepository.findByIdIn(taskIds))
//                .thenReturn(taskEntities);
//        Mockito.when(userRepository.findByIdIn(userIds))
//                .thenReturn(userEntities);
//        Mockito.when(taskRepository.saveAll(taskEntityList))
//                .thenReturn(null);
//
//        Response response1 = taskService.getNewById(tasks);
//
//        assertEquals(HttpStatus.OK, response1.getHttpStatus());
//    }

    @Test
    void getAllDetailsByUserId() {
        Response<List<Task>> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        Integer userId = 1;
        Set<SubTaskEntity> subTasks = new HashSet<>();
        SubTaskEntity subTask = SubTaskEntity
                .builder()
                .id(11l)
                .name("spring")
                .description("springDesc")
                .build();
        subTasks.add(subTask);

        List<TaskEntity> tasks = new ArrayList<>();
        TaskEntity task = TaskEntity.builder()
                .id(88l)
                .name("java")
                .description("desc")
                .subTaskEntityList(subTasks)
                .build();

        tasks.add(task);
        List list = new ArrayList<>();


        UserDetail userDetail = new UserDetail("name", "password", "email", 1, list);
        Mockito.when(userUtils.getUser()).thenReturn(userDetail);

        Mockito.when(taskRepository.findAllByUserId(userId)).thenReturn(tasks);
        Response<List<Task>> response1 = taskService.getAllByUserId();
        assertEquals(HttpStatus.OK, response1.getHttpStatus());
        assertEquals("java", response1.getResponse().get(0).getName());
        assertEquals("desc", response1.getResponse().get(0).getDescription());
        assertEquals(88l, response1.getResponse().get(0).getId());
        assertEquals("spring", ((SubTask) response1.getResponse().get(0).getSubTasks().toArray()[0]).getName());
        assertEquals("springDesc", ((SubTask) response1.getResponse().get(0).getSubTasks().toArray()[0]).getDescription());
        assertEquals(11l, ((SubTask) response1.getResponse().get(0).getSubTasks().toArray()[0]).getId());

    }

    @Test
    void getAllDetailsByUserIdError() {
        Response<List<Task>> response = new Response<>();
        Integer userId = 1;
        List list = new ArrayList<>();
        UserDetail userDetail = new UserDetail("name", "password", "email", 1, list);
        Mockito.when(userUtils.getUser()).thenThrow(new RuntimeException(" "));
        Mockito.when(taskRepository.findAllByUserId(userId)).thenThrow(new RuntimeException(" "));
        Response<List<Task>> response1 = taskService.getAllByUserId();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getHttpStatus());

    }


    @Test
    void updateError() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        // List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .description("aaa")
                .name("anu")
                .id(11L)
                .build();
        long id = 11;

        Task task = Task.builder()
                .name("aaa")
                .description("aaaa")
                .id(11L)
                .build();

        //  Mockito.doNothing().when(taskRepository.findById(id));
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(taskEntity));

        //  Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null).thenThrow(new HTTPException(500));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(new Exception(""));

        Response<?> response1 = taskService.update(id, task);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response1.getHttpStatus());
    }

    @Test
    void updateError1() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        // List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .description("aaa")
                .name("anu")
                .id(11L)
                .build();
        long id = 11;

        Task task = Task.builder()
                .name("aaa")
                .description("aaaa")
                .id(11L)
                .build();

        //  Mockito.doNothing().when(taskRepository.findById(id));
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(taskEntity));

        //  Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null).thenThrow(new HTTPException(500));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null);

        Response<?> response1 = taskService.update(id, task);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
    }

    @Test
    void update() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        // List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .description("aaa")
                .name("anu")
                .id(11L)
                .build();
        long id = 11;

        Task task = Task.builder()
                .name("aaa")
                .description("aaaa")
                .id(11L)
                .build();

        //  Mockito.doNothing().when(taskRepository.findById(id));
        Mockito.when(taskRepository.findById(id))
                .thenReturn(Optional.of(taskEntity));

        //  Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null).thenThrow(new HTTPException(500));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(null);

        Response<?> response1 = taskService.update(id, task);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
    }

    @Test
    void updateMany() {
        Response response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        Integer userId = 1;
        List<Long> taskIds = new ArrayList<>();
        taskIds.add(11L);
//        taskEntity.setUserId(userUtils.getUser().getUserId());
        List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity taskEntity = TaskEntity.builder()
                .id(11L)
                .name("anu")
                .build();
        taskEntities.add(taskEntity);

        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntityList.add(taskEntity);


        List<Task> tasks = new ArrayList<>();
        Task task = Task.builder()
                .name("anu")
                .id(11L)
                .description("desc")
                .build();
        tasks.add(task);
        tasks.add(task);
        List list = new ArrayList<>();


        UserDetail userDetail = new UserDetail("name", "password", "email", 1, list);
        Mockito.when(userUtils.getUser()).thenReturn(userDetail);

        Mockito.when(taskRepository.findByIdIn(taskIds)).thenReturn(taskEntities);
        Mockito.when(taskRepository.saveAll(taskEntityList)).thenReturn(null);
        Response response1 = taskService.updateMany(tasks);
        assertEquals(HttpStatus.OK, response1.getHttpStatus());
    }
}