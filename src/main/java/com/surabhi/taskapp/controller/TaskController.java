package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.TaskService;
import com.surabhi.taskapp.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = "/tasks")


public class TaskController {

    private final TaskService taskService;


    private final UserUtils userUtils;


    public TaskController(TaskService taskService,UserUtils userUtils) {
        this.taskService = taskService;
        this.userUtils=userUtils;
    }




    @GetMapping
    public ResponseEntity<List<Task>> getAllDetailsByUserId(){
        log.info("api = /info, method = GET, status = IN_PROGRESS");
        Response<List<Task>> response=taskService.getAllByUserId();
        log.info("api = /info, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }



    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        log.info("api = /tasks, method = GET, status = IN_PROGRESS");
        Response<Task> response = taskService.getById(id);
        log.info("api = /tasks, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        log.info("api = /tasks, method = POST, status = IN_PROGRESS");
        Response<?> response = taskService.add(task);
        log.info("api = /tasks, method = POST, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("api = /tasks, method = DELETE, status = IN_PROGRESS");
        Response<?> response = taskService.delete(id);
        log.info("api = /tasks, method = DELETE, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

}





