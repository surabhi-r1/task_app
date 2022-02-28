package com.surabhi.taskapp.service;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Response<PaginationResponse<List<Task>>> getAll(Pageable pageable);

    Response<?> add(Task task);



    Response<?> delete(Long id);

    Response<Task> getById(Long id);

    Response<List<Task>> getAllByUserId();


}
