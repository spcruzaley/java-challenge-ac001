package com.avenuecode.repository;

import com.avenuecode.domain.Town;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class TownRowMapper implements RowMapper<Town> {

    @Override
    public Town mapRow(ResultSet rs, int rowNum) throws SQLException {
        Town town = new Town();
        town.setId(rs.getInt("id"));
        town.setLabel(rs.getString("label"));

        return town;
    }
}
