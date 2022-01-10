package com.surabhi.taskapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private long id;
    private String name;
    private String description;
    private Date createdDate;
    private Set<SubTask> subTasks;
}
