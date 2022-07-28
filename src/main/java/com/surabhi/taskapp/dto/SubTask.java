package com.surabhi.taskapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {
    private long id;
    private String name;
    private String description;
    private Date createdDate;
    private Long taskEntityId;
}
