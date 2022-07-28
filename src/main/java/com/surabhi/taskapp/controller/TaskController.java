package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllDetailsByUserId() {
        log.info("api = /task, method = GET, status = IN_PROGRESS");
        Response<List<Task>> response = taskService.getAllByUserId();
        log.info("api = /task, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }


    @PutMapping
    public ResponseEntity<?> updateMany(@RequestBody List<Task> task){
        log.info("api=/task,method=PUT,status=IN PROGRESS");
        Response<?> response=taskService.updateMany(task);
        log.info("api=/task,method=PUT,status=SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        log.info("api = /task, method = GET, status = IN_PROGRESS");
        Response<Task> response = taskService.getById(id);
        log.info("api = /task, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        log.info("api = /task, method = POST, status = IN_PROGRESS");
        Response<?> response = taskService.add(task);
        log.info("api = /task, method = POST, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable Long id) {
        log.info("api = /tasks, method = PUT, status = IN_PROGRESS");
        Response<?> response = taskService.update(id, task);
        log.info("api = /tasks, method = PUT, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        log.info("api = /tasks, method = DELETE, status = IN_PROGRESS");
        Response<?> response = taskService.delete(id);
        log.info("api = /tasks, method = DELETE, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }
    @PostMapping("/addMany")
    public ResponseEntity<Void> addManyTask(@RequestBody List<Task> tasks) {
       // User user = userUtil.getLoginUser();
      //  log.info("api = /task/addMany, method = POST, result = IN_PROGRESS, userId = {}", user.getUserId());
        Response<?> serviceResponse = taskService.addManyTask(tasks);
     //   log.info("api = /task/addMany, method = POST, result = SUCCESS, userId = {}", user.getUserId());
        return ResponseEntity.status(serviceResponse.getHttpStatus()).build();
    }

}





