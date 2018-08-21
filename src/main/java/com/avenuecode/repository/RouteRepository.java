package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import com.avenuecode.domain.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository  {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Save a route
     * @param route Route object to be inserted
     * @return Number of rows affected, in other case 0
     */
    public int insert(Route route) {
        return jdbcTemplate.update("insert", new Object[]{
                route.getId(), route.getSource(), route.getTarget(), route.getDistance()
        });
    }

    /**
     * Save a route
     * @param route  Route object to be inserted
     * @param source Source of Town's type
     * @param target Target of Town's type
     * @return Number of rows affected, in other case 0
     */
    public int insert(Route route, Town source, Town target) {
        return jdbcTemplate.update("insert", new Object[]{
                route.getId(), source.getLabel(), target.getLabel(), route.getDistance()
        });
    }

}
