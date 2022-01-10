package com.surabhi.taskapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String name;

    @JsonProperty("reg_no")
    private Integer regNo;

    private String gender;

    @JsonProperty("mob_no")
    private Long mobileNumber;

    private Integer age;
    private Set<Address> addresses;
}
