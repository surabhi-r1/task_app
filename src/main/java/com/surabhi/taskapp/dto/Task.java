package com.surabhi.taskapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    @JsonProperty("created_date")
    private Timestamp createdDate;
    @JsonProperty("user_id")
    private Integer userId;
    private Set<SubTask> subTasks;
}
