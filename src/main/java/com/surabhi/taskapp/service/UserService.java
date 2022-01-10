package com.surabhi.taskapp.service;

import com.surabhi.taskapp.dto.User;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Response<PaginationResponse<List<User>>> getAll(Pageable pageable);

    Response<?> add(User user);

    Response<User> update(Long id, User user);

    Response<User> getById(Long id);

    Response<?> delete(Long id);
}
