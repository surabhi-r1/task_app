package com.surabhi.taskapp.service.impl;


import com.surabhi.taskapp.dto.SubTask;
import com.surabhi.taskapp.dto.Task;
import com.surabhi.taskapp.entity.SubTaskEntity;
import com.surabhi.taskapp.entity.TaskEntity;
import com.surabhi.taskapp.repository.SubTaskRepository;
import com.surabhi.taskapp.repository.TaskRepository;
import com.surabhi.taskapp.response.PaginationResponse;
import com.surabhi.taskapp.response.Response;
import com.surabhi.taskapp.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Autowired
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Response<PaginationResponse<List<Task>>> getAll(Pageable pageable) {
        Response<PaginationResponse<List<Task>>> response = new Response<>();
        try {
            log.info("operation = getAllTasks, status = IN_PROGRESS, message = get all tasks");
            Page<TaskEntity> page = taskRepository.findAll(pageable);
            PaginationResponse<List<Task>> paginationResponse = new PaginationResponse<>();
            paginationResponse.setTotalPages(page.getTotalPages());
            paginationResponse.setTotalElements(page.getTotalElements());
            paginationResponse.setSize(pageable.getPageSize());

            List<Task> tasks = new ArrayList<>();
            for (TaskEntity taskEntity : page.getContent()) {
                Task task = new Task();
                BeanUtils.copyProperties(taskEntity, task);

                Set<SubTask> subTasks = Optional.ofNullable(taskEntity.getSubTaskEntityList())
                        .orElse(Collections.emptySet())
                        .stream().map(subTaskEntity -> {
                            SubTask subTask = new SubTask();
                            BeanUtils.copyProperties(subTaskEntity, subTask);
                            return subTask;
                        })
                        .collect(Collectors.toSet());

                task.setSubTasks(subTasks);
                tasks.add(task);
            }
            log.info("operation = getAllTasks, status = SUCCESS, message = get all tasks");
            response.setHttpStatus(HttpStatus.OK);

            paginationResponse.setData(tasks);
            response.setResponse(paginationResponse);
           // return response;

        } catch (Exception e) {
            log.error("operation = getAllTasks, status = ERROR, msg = error in getAllTasks", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    public Response<?> add(Task task) {
        log.info("operation = add, status = IN_PROGRESS, message = add all tasks");
        Response<?> response = new Response<>();
        try {
            TaskEntity taskEntity = new TaskEntity();
            BeanUtils.copyProperties(task, taskEntity);


            Set<SubTaskEntity> subTaskEntitySet = new HashSet<>();
            if (task.getSubTasks() != null && !task.getSubTasks().isEmpty()) {
                for (SubTask subTask : task.getSubTasks()) {
                    SubTaskEntity subTaskEntity = new SubTaskEntity();
                    BeanUtils.copyProperties(subTask, subTaskEntity);
                    subTaskEntitySet.add(subTaskEntity);
                }
            }

            taskEntity.setSubTaskEntityList(subTaskEntitySet);
            taskRepository.save(taskEntity);

            log.info("operation = add, status = SUCCESS, message = add all tasks");
            response.setHttpStatus(HttpStatus.CREATED);
            return response;
        } catch (Exception e) {
            log.error("operation = add, status = ERROR, msg = error in add", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public Response<Task> getById(Long id) {
        Response<Task> response = new Response<>();
        try {
            log.info("operation = getById, status = IN_PROGRESS, message = get task by id");

            Optional<TaskEntity> taskEntity = taskRepository.findById(id);

            if (taskEntity.isPresent()) {
                Task task1 = new Task();
                BeanUtils.copyProperties(taskEntity.get(), task1);

                log.info("operation = getById, status=SUCCESS, message= get task by id");
                response.setHttpStatus(HttpStatus.OK);
                response.setResponse(task1);
              //  return response;
            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
            }

            return response;
        } catch (Exception e) {
            log.error("operation = getById, status = ERROR, msg = error in get task by id", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @Override
    @Transactional
    public Response<?> update(Long id, Task task) {
        Response<?> response = new Response<>();
        try {
            log.info("operation = updateTask, status = IN_PROGRESS, message = get task by id");
            //throws Exception
            Optional<TaskEntity> taskEntity = taskRepository.findById(id);
            if (taskEntity.isPresent()) {
                TaskEntity tasks = taskEntity.get();
                Optional.ofNullable(task.getName())
                        .ifPresent(tasks::setName);
                Optional.ofNullable(task.getDescription())
                        .ifPresent(tasks::setDescription);

                tasks.setName(tasks.getName());
                tasks.setDescription(tasks.getDescription());

                taskRepository.save(tasks);
                log.info("operation = updateTask, status = SUCCESS, message = get task by id");
                response.setHttpStatus(HttpStatus.OK);
            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
            }
            return response;
        } catch (Exception e) {
            log.error("operation = updateTask, status = ERROR, msg = error in add details", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }

    }

    @Override
    public Response<?> delete(Long id) {
        Response<?> response = new Response<>();
        try {
            log.info("operation = deleteTask, status = IN_PROGRESS, message = delete task by id");
            taskRepository.deleteById(id);
            log.info("operation = deleteTask, status = SUCCESS, message = delete task by id");
            response.setHttpStatus(HttpStatus.OK);
            return response;

        } catch (Exception e) {
            log.error("operation = deleteTask, status = ERROR, msg = error in delete task", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

}