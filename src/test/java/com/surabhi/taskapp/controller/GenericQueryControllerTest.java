package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.impl.GenericTaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericQueryControllerTest {

    GenericQueryController genericQueryController;

    @Mock
    GenericTaskServiceImpl genericService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.genericQueryController = new GenericQueryController(genericService);
        System.out.println("running before each");
    }

    @Test
    void findAll() {
        Response<List<Task>> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);

        String queryName = "findAll";

        Mockito.when(genericService.getAll(queryName)).thenReturn(response);
        ResponseEntity<List<Task>> responseEntity = genericQueryController.getAllTask(queryName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}