package com.avenuecode.rest;

import com.avenuecode.common.Utils;
import com.avenuecode.repository.RouteBO;
import com.avenuecode.repository.RouteBuilder;
import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteRepository;
import com.avenuecode.to.AvailableRoutesTO;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RouteService {
    private static Logger logger = Logger.getLogger("InfoLogging");

    @Autowired
    private RouteRepository routeRepository;

    public RouteService() {
        this.routeRepository = new RouteRepository();
    }

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Call to Repository for Route in order to store the information received
     * @param route Route object to be inserted
     * @return affected row number
     */
    public int insert(Route route, int idGroupRoute) {
        return routeRepository.insert(route, idGroupRoute);
    }

    /**
     * Call to Repository for Route in order to store the information received
     * @param route Route object to be inserted
     * @return affected row number
     */
    public int insertRoutes(List<Route> routes, int idGroupRoute) {
        int affectedRows = 0;

        for(Route route: routes) {
            affectedRows += insert(route, idGroupRoute)
        }

        return affectedRows;
    }

    /**
     * Get an specific graph from an id group
     * @param id from the graph generated previously
     * @return Route's list
     */
    public List<Route> getRoutes(int id) {
        List<Route> listRoutes = routeRepository.findByIdRouteGroup(id);
        
        return listRoutes;
    }

    /**
     * Get all the available routes given a source, target, Route's list and maximum of stops
     * @param listRoutes List with all the routes located given an id group
     * @param source Source from
     * @param target Target as a destiny
     * @param maxStops Number of stops to be consider
     * @return Available routes list
     */
    public List<AvailableRoutesTO> getAvailableRoutes(List<Route> listRoutes, String source, String target, int maxStops) {
        AvailableRoutesTO availableRoutesTO;
        List<AvailableRoutesTO> routes = new ArrayList<>();
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource(source, target, listRoutes,
                new ArrayList<String>());

        StringBuilder twonRoutes = new StringBuilder();
        int count = 0;

        for (String town: allPossibleRoutes) {
            twonRoutes.append(town);
            count++;

            if(town.equals(target)) {
                twonRoutes.insert(0, source);
                availableRoutesTO = new AvailableRoutesTO();
                availableRoutesTO.setRoute(twonRoutes.toString());
                availableRoutesTO.setStops(count);

                if(count <= maxStops) {
                    routes.add(availableRoutesTO);
                }
                twonRoutes.delete(0, twonRoutes.length());
                count = 0;
            }
        }

        return routes;
    }

    /**
     * Get the shortest route
     * @param listRoutes List with all the routes located given an id group
     * @param source Source from
     * @param target Target as a destiny
     * @return Available routes between the given source, target and route list
     */
    public List<RouteBetweenTownsTO> getRoutesBetweenTowns(List<Route> listRoutes, String source, String target) {
        AvailableRoutesTO availableRoutesTO;
        List<AvailableRoutesTO> routes = new ArrayList<>();
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource(source, target, listRoutes,
                new ArrayList<String>());

        StringBuilder twonRoutes = new StringBuilder();
        int count = 0;

        for (String town: allPossibleRoutes) {
            twonRoutes.append(town);
            count++;

            if(town.equals(target)) {
                twonRoutes.insert(0, source);
                availableRoutesTO = new AvailableRoutesTO();
                availableRoutesTO.setRoute(twonRoutes.toString());
                availableRoutesTO.setStops(count);

                if(count <= maxStops) {
                    routes.add(availableRoutesTO);
                }
                twonRoutes.delete(0, twonRoutes.length());
                count = 0;
            }
        }

        return routes;
    }

    /**
     * Return the next id for one grpah that will be inserted in the DB
     * @return the next id to store in DB
     */
    public int getLastIdRouteGroup() {
        int maxIdRouteGroup = routeRepository.getLastIdRouteGroup();
        return maxIdRouteGroup;
    }
}
