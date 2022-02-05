package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")


public class TaskController {
    @Autowired
    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

//    http://localhost:8080/swagger-ui.html

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<PaginationResponse<List<Task>>> getAllTasks(@PageableDefault(size = 5) Pageable pageable) {
        log.info("api = /tasks, method = GET, status = IN_PROGRESS");
        Response<PaginationResponse<List<Task>>> response = taskService.getAll(pageable);
        log.info("api = /tasks, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PutMapping
    public ResponseEntity<?> getNewTaskById( @RequestBody List<Task> task) {
        log.info("api = /tasks, method = GET, status = IN_PROGRESS");
        Response<?> response = taskService.getNewById(task);
        log.info("api = /tasks, method = GET, status = SUCCESS");
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

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable Long id) {
//        log.info("api = /tasks, method = PUT, status = IN_PROGRESS");
//        Response<?> response = taskService.update(id, task);
//        log.info("api = /tasks, method = PUT, status = SUCCESS");
//        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("api = /tasks, method = DELETE, status = IN_PROGRESS");
        Response<?> response = taskService.delete(id);
        log.info("api = /tasks, method = DELETE, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

}





