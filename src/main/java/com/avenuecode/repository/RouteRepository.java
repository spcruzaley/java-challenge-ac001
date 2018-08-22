package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class RouteRepository  {
    private static Logger logger = Logger.getLogger("InfoLogging");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String GET_ALL_ROUTES = "select * from route";
    public static final String GET_ID_ROUTE_GROUP_MAX = "select max(idRouteGroup) from route";
    public static final String TRUNCATE_ROUTE_TABLE = "truncate table route";

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

    /**
     * Get the last groupId generated
     * @return The max id number group
     */
    public int getLastIdRouteGroup() {
        return jdbcTemplate.query(GET_ID_ROUTE_GROUP_MAX, resultSet -> {
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        });
    }

    /**
     * Truncate all the information located in the Route table
     */
    public void truncateTable() {
        jdbcTemplate.execute(TRUNCATE_ROUTE_TABLE);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
