package com.surabhi.taskapp.service.impl;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.response.Response;

import java.util.List;

public interface GenericService {
    Response<List<Task>> getAll(String queryName);
}
