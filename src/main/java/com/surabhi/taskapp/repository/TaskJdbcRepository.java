package com.surabhi.taskapp.repository;

import com.surabhi.taskapp.repository.querydetails.Queries;
import com.surabhi.taskapp.repository.querydetails.TaskQueries;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.List;

@Component
public class TaskJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    //private final Queries queries;
    private final TaskQueries taskQueries;
    //public TaskJdbcRepository(@Qualifier("applicationDataSource") DataSource dataSource, Queries queries) {
    public TaskJdbcRepository(@Qualifier("applicationDataSource") DataSource dataSource, TaskQueries taskQueries) {
        this.jdbcTemplate = initJdbcTemplate(dataSource);
        //this.queries = queries;
        this.taskQueries=taskQueries;
    }
    private NamedParameterJdbcTemplate initJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    }
    public <RES> List<RES> executeQuery(MapSqlParameterSource mapSqlParameterSource, String queryName, RowMapper<RES> rowMapper) {
        String sqlQuery = getQuery(queryName);
        return jdbcTemplate.query(sqlQuery, mapSqlParameterSource, rowMapper);
    }
    private String getQuery(String queryName) {
        String query = null;

        if ("findAll".equals(queryName)) {
            query = taskQueries.getFindAll();
        }

        Assert.isTrue(query != null, "no sql query mapped for queryName " + queryName);

        return query;
    }


}
