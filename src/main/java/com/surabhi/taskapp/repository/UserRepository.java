package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    List<UserEntity> findByIdIn(List<Long> id);
}
