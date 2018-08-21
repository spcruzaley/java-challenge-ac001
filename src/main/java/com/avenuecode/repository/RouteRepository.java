package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteRepository  {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String GET_ALL_ROUTES = "select * from route";

    /**
     * Get all the routes stored in the DB
     * @return Route objects list
     */
    public List<Route> getAll() {
        return jdbcTemplate.query(GET_ALL_ROUTES, new RouteRowMapper());
    }

    /**
     * Save a route
     * @param route  Route object to be inserted
     * @return Number of rows affected, in other case 0
     */
    public int insert(Route route) {
        return jdbcTemplate.update("insert into route (idRouteGroup, source, target, distance) " +
                "values(?, ?, ?, ?)", new Object[]{
                route.getIdRouteGroup(),
                route.getSource(),
                route.getTarget(),
                route.getDistance()
        });
    }

    /**
     * Get a list with all the routes by group
     * @param idRouteGroup id to identify the routes group
     * @return Routes list
     */
    public List<Route> findByIdRouteGroup(int idRouteGroup) {
        return jdbcTemplate.query("select * from route where idRouteGroup=?", new Object[]{
                        idRouteGroup
                },
                new RouteRowMapper());
    }
}
