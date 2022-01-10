package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<List<User>>> getAllInfo(@PageableDefault(size=5) Pageable pageable) {
        log.info("api = /info, method = GET, status = IN_PROGRESS");
        Response<PaginationResponse<List<User>>> response = userService.getAll(pageable);
        log.info("api = /info, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        log.info("api = /info, method = POST, status = IN_PROGRESS");
        Response response = userService.add(user);
        log.info("api = /info, method = POST, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateInfo(@RequestBody User user, @PathVariable Long id) {
        log.info("api = /info, method = PUT, status = IN_PROGRESS");
        Response<User> response = userService.update(id, user);
        log.info("api = /info, method = PUT, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getInfoById(@PathVariable Long id) {
        log.info("api = /info/id, method = GET, status = IN_PROGRESS");
        Response<User> response = userService.getById(id);
        log.info("api = /info/id, method = GET, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteInfo(@PathVariable Long id) {
        log.info("api = /info/id, method = DELETE, status = IN_PROGRESS");
        Response<?> response = userService.delete(id);
        log.info("api = /info/id, method = DELETE, status = SUCCESS");
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }


}

