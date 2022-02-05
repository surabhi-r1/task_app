package com.surabhi.taskapp.mapper;

import com.surabhi.taskapp.dto.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        return task;
    }

}
