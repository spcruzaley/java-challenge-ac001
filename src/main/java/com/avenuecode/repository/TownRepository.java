package com.avenuecode.repository;

import com.avenuecode.domain.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TownRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Find a town by label
     * @param label town's label to search
     * @return Town object with the information
     */
    public Town findByLabel(String label) {
        return jdbcTemplate.queryForObject("select * from town where label=?", new Object[]{
                        label
                },
                new BeanPropertyRowMapper<Town>(Town.class));
    }

    public List<Town> getAll() {
        return jdbcTemplate.query("select * from town", new TownRowMapper());
    }

    /**
     * Insert a town
     * @param label Town's name
     * @return Number of rows affected, zero in other case
     */
    public int insert(String label) {
        return jdbcTemplate.update("insert into town(label) values(?)", new Object[]{
                label
        });
    }
}
