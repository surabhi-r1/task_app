package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.SubTaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubTaskRepository extends CrudRepository<SubTaskEntity,Long> {
}
