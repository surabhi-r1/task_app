package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.TaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<TaskEntity, Long> {

}
