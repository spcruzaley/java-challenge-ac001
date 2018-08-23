package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class RouteRowMapper implements RowMapper<Route> {

    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        Route route = new Route();
        route.setSource(rs.getString("source"));
        route.setTarget(rs.getString("target"));
        route.setDistance(rs.getInt("distance"));

        return route;
    }
}
