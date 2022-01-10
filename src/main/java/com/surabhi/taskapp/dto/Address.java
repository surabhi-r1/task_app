package com.surabhi.taskapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    private String place;
    private Integer pincode;
    private String state;
}
