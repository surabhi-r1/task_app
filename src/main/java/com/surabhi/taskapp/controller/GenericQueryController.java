package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.impl.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/genericQuery")
@Slf4j

public class GenericQueryController {


    private final GenericService genericService;

    public GenericQueryController(GenericService genericService) {
        this.genericService = genericService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(@RequestParam("query")String queryName){
        Response<List<Task>> response=genericService.getAll(queryName);
        return ResponseEntity.status(response.getHttpStatus()).body(response.getResponse());
    }

}
