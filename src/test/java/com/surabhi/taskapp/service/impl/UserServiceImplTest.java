package com.surabhi.taskapp.service.impl;


import com.surabhi.taskapp.dto.Address;
import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.entity.AddressEntity;
import com.surabhi.taskapp.entity.UserEntity;
import com.surabhi.taskapp.repository.UserRepository;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    //AddressRepository addressRepository;
    UserServiceImpl userService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository);

    }

    @Test
    void delete() {
        long id = 11;
        Mockito.doNothing().when(userRepository).deleteById(id);
        Response<?> response = userService.delete(id);
        assertEquals(HttpStatus.OK, response.getHttpStatus());

    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Set<AddressEntity> addressEntities=new HashSet<>();
        AddressEntity addressEntity=AddressEntity.builder()
                .build();
        addressEntities.add(addressEntity);
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity userEntity = UserEntity.builder()
                .name("aaaa")
                .age(18)
                .gender("male")
                .id(11L)
                .mobileNumber(100000000L)
                .regNo(101)
                .addressEntityList(addressEntities)
                .build();
        userEntities.add(userEntity);
        Page<UserEntity> page1 = new PageImpl<>(userEntities, pageable, 1L);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(page1);

        Response<PaginationResponse<List<User>>> response1 = userService.getAll(pageable);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
        assertEquals(1, response1.getResponse().getData().size());
        assertEquals("aaaa",response1.getResponse().getData().get(0).getName());
        assertEquals("male",response1.getResponse().getData().get(0).getGender());
        assertEquals(100000000,response1.getResponse().getData().get(0).getMobileNumber());
        assertEquals(18,response1.getResponse().getData().get(0).getAge());
        assertEquals(11,response1.getResponse().getData().get(0).getId());

    }

    @Test
    void add() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.CREATED);

        Set<Address> addresses=new HashSet<>();
        Address address=new Address();
        address.setPlace("mysore");
        addresses.add(address);

        User user = User.builder()
                .id(11L)
                .name("anu")
                .gender("male")
                .age(18)
                .mobileNumber(100000000L)
                .regNo(101)
                .addresses(addresses)
                .build();

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

        Response<?> response1 = userService.add(user);

        assertEquals(HttpStatus.CREATED, response1.getHttpStatus());
    }

    @Test
    void update() {
        Response<?> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        UserEntity userEntity = UserEntity.builder()
                .name("anu")
                .id(11L)
                .gender("male")
                .age(18)
                .mobileNumber(100000000L)
                .regNo(101)
                .build();
        long id = 11;

        User user = User.builder()
                .name("aaa")
                .id(11L)
                .gender("male")
                .age(18)
                .mobileNumber(100000000L)
                .regNo(101)
                .build();

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(userEntity));

        Mockito.when(userRepository.save(Mockito.any()))
                .thenReturn(null);

        Response<?> response1 = userService.update(id,user);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
    }


    @Test
    void getById() {
        Response<User> response = new Response<>();
        response.setHttpStatus(HttpStatus.OK);
        UserEntity userEntity = UserEntity.builder()
                .name("aaa")
                .id(11L)
                .gender("male")
                .age(18)
                .mobileNumber(100000000L)
                .regNo(101)
                .build();
        long id = 11;

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(userEntity));

        Response<User> response1 = userService.getById(id);

        assertEquals(HttpStatus.OK, response1.getHttpStatus());
        assertEquals("aaa", response1.getResponse().getName());
        assertEquals(11, response1.getResponse().getId());
        assertEquals(100000000,response1.getResponse().getMobileNumber());
        assertEquals(18,response1.getResponse().getAge());
        assertEquals("male",response1.getResponse().getGender());
        assertEquals(101,response1.getResponse().getRegNo());
    }
}