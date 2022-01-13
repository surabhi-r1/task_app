package com.surabhi.taskapp.controller;

import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.impl.UserServiceImpl;
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

class UserControllerTest {
    @Mock
    UserServiceImpl userService;
    UserController userController;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.userController = new UserController(userService);
        System.out.println("running before each");
    }


    /*
     * testing the getAll api = /user/id method = PUT
     * given id
     * then returns response
     * scenario SUCCESS
     */

    @Test
    void getById() {
        Response<User> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        User user = User.builder()
                .age(18)
                .gender("male")
                .name("aaa")
                .regNo(101)
                .build();
        response.setResponse(user);
        long id = 11;

        Mockito.when(userService.getById(id))
                .thenReturn(response);

        ResponseEntity<User> responseEntity = userController.getInfoById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("aaa", responseEntity.getBody().getName());
        assertEquals(101, responseEntity.getBody().getRegNo());
        assertEquals("male", responseEntity.getBody().getGender());
        assertEquals(18, responseEntity.getBody().getAge());

    }

    /*
     * testing the getAll api = /user method = PUT
     * given user
     * then returns response
     * scenario SUCCESS
     */

    @Test
    void add() {
        Response response = new Response<>();
        response.setHttpStatus(CREATED);
        User user = User.builder()
                .age(18)
                .gender("male")
                .name("aaa")
                .regNo(101)
                .build();
        Mockito.when(userService.add(user)).thenReturn(response);
        ResponseEntity<?> responseEntity = userController.addUser(user);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    /*
     * testing the getAll api = /user/id method = DELETE
     * given id
     * then returns ResponseEntity<PaginationResponse<User>>
     * scenario SUCCESS
     */
    @Test
    void delete() {
        Response response = new Response<>();
        response.setHttpStatus(OK);
        long id = 11;
        Mockito.when(userService.delete(id)).thenReturn(response);
        ResponseEntity<?> responseEntity = userController.deleteInfo(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*
     * testing the getAll api = /user/id method = PUT
     * given id,user
     * then returns response
     * scenario SUCCESS
     */
    @Test
    void update() {
        Response response = new Response<>();
        {
            response.setHttpStatus(OK);
            User user = User.builder()
                    .name("aa")
                    .build();
            long id = 11;

            Mockito.when(userService.update(id, user))
                    .thenReturn(response);

            ResponseEntity<?> responseEntity = userController.updateInfo(user, id);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        }
    }

    /*
     * testing the getAll api = /user method = GET
     * given pageable
     * then returns ResponseEntity<PaginationResponse<User>>
     * scenario SUCCESS
     */

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);

        Response<PaginationResponse<List<User>>> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        PaginationResponse<List<User>> paginationResponse = new PaginationResponse<>();
        List<User> users = new ArrayList<>();
        User user = User.builder()
                .name("anu")
                .age(12)
                .gender("female")
                .regNo(1020)
                .build();
        users.add(user);
        paginationResponse.setData(users);
        paginationResponse.setSize(5);
        paginationResponse.setPage(1);
        response.setResponse(paginationResponse);

        Mockito.when(userService.getAll(pageable))
                .thenReturn(response);

        ResponseEntity<PaginationResponse<List<User>>> responseEntity = userController.getAllInfo(pageable);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getData().size());
        assertEquals("anu", responseEntity.getBody().getData().get(0).getName());
        assertEquals(1020, responseEntity.getBody().getData().get(0).getRegNo());
        assertEquals(12, responseEntity.getBody().getData().get(0).getAge());
        assertEquals("female", responseEntity.getBody().getData().get(0).getGender());


    }
}