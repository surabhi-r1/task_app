package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.entity.TaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByUserId(Integer userId);

}
