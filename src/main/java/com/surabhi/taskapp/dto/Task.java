package com.surabhi.taskapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_id")
    private Long userId;
    private Set<SubTask> subTasks;
}
