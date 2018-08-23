package com.avenuecode.rest;

import com.avenuecode.domain.Route;
import com.avenuecode.dto.AvailableRoutesDTO;
import com.avenuecode.dto.RouteBetweenTownsDTO;
import com.avenuecode.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Component
public class RouteService {
    private static Logger log = Logger.getLogger("InfoLogging");

    @Autowired
    private RouteRepository routeRepository;

    public RouteService() {
        this.routeRepository = new RouteRepository();
    }

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Call dto Repository for Route in order dto store the information received
     *
     * @param route Route object dto be inserted
     * @return affected row number
     */
    public int insert(Route route, int idGroupRoute) {
        return routeRepository.insert(route, idGroupRoute);
    }

    /**
     * Call dto Repository for Route in order dto store the information received
     *
     * @param routes Route object dto be inserted
     * @return affected row number
     */
    public int insertRoutes(List<Route> routes, int idGroupRoute) {
        int affectedRows = 0;

        for (Route route : routes) {
            affectedRows += insert(route, idGroupRoute);
        }

        return affectedRows;
    }

    /**
     * Get an specific graph from an id group
     *
     * @param id from the graph generated previously
     * @return Route's list
     */
    public List<Route> getRoutes(int id) {
        List<Route> listRoutes = routeRepository.findByIdRouteGroup(id);

        return listRoutes;
    }

    /**
     * Get all the available routes given a source, target, Route's list and maximum of stops
     *
     * @param listRoutes List with all the routes located given an id group
     * @param source     Source from
     * @param target     Target as a destiny
     * @param maxStops   Number of stops dto be consider
     * @return Available routes list
     */
    public static List<AvailableRoutesDTO> getAvailableRoutes(List<Route> listRoutes, String source, String target, int maxStops) {
        AvailableRoutesDTO availableRoutesTO;
        List<AvailableRoutesDTO> routes = new ArrayList<>();
        List<String> allPossibleRoutes = getTargetsFromSource(source, target, listRoutes,
                new ArrayList<String>());

        StringBuilder twonRoutes = new StringBuilder();
        int count = 0;

        for (String town : allPossibleRoutes) {
            twonRoutes.append(town);
            count++;

            if (town.equals(target)) {
                twonRoutes.insert(0, source);
                availableRoutesTO = new AvailableRoutesDTO();
                availableRoutesTO.setRoute(twonRoutes.toString());
                availableRoutesTO.setStops(count);

                if (count <= maxStops) {
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
     *
     * @param listRoutes List with all the routes located given an id group
     * @param source     Source from
     * @param target     Target as a destiny
     * @return Available routes between the given source, target and route list
     */
    public static RouteBetweenTownsDTO getRoutesBetweenTowns(List<Route> listRoutes, String source, String target) {
        if(source.equals(target)) {
            return new RouteBetweenTownsDTO(0, new String[] {source});
        }

        List<AvailableRoutesDTO> routesTOS = getAvailableRoutes(listRoutes, source, target, Integer.MAX_VALUE);
        List<String[]> listArrayRoutes = new ArrayList<>();
        StringBuilder builder;

        //Generate the complete route with the source
        for (AvailableRoutesDTO routesTO: routesTOS) {
            builder = new StringBuilder();
            builder.append(routesTO.getRoute());
            listArrayRoutes.add(builder.toString().split(""));
        }

        //Get the distance and validate what is the shortest
        int currentDistance;
        int distance = Integer.MAX_VALUE-1;
        int index = 0;

        for (int i = 0; i < listArrayRoutes.size(); i++) {
            currentDistance = getGraphDistance(listRoutes, listArrayRoutes.get(i));
            if(currentDistance < distance) {
                distance = currentDistance;
                index = i;
            }
        }
        String[] finalRoutes = listArrayRoutes.get(index);

        return new RouteBetweenTownsDTO(distance, finalRoutes);
    }

    /**
     * Return the next id for one graph that will be inserted in the DB
     *
     * @return the next id dto store in DB
     */
    public int getLastIdRouteGroup() {
        int maxIdRouteGroup = routeRepository.getLastIdRouteGroup();
        return maxIdRouteGroup;
    }

    /**
     * Get an ordered twons
     * @param listRoutes Routes list
     * @return String[] with the unique towns sorted
     */
    public static String[] getOrderedTownsFromGraph(List<Route> listRoutes) {
        // Create the sorted set
        SortedSet set = new TreeSet();

        //Obtain unique towns
        for (Route route: listRoutes) {
            set.add(route.getSource());
            set.add(route.getTarget());
        }

        Iterator<String> it = set.iterator();
        String[] sortTowns = new String[set.size()];
        int i=0;
        while (it.hasNext()) {
            sortTowns[i++] = it.next();
        }

        return sortTowns;
    }

    /**
     * Get the distance between some towns given
     * @param towns Array with towns
     * @param routes List of routes of an specific group of towns
     * @return int total distance between the towns given
     */
    public static int getGraphDistance(List<Route> routes, String[] towns){
        Route route;
        int distance = 0;
        int i = 0, j = 0;
        String source;
        String target;

        if(towns.length <= 1) {
            return 0;
        }

        for (;j<routes.size();) {
            route = routes.get(j);

            source = route.getSource();
            target = route.getTarget();

            if(source.equals(towns[i]) && target.equals(towns[i+1])) {
                distance += route.getDistance();
                j=0;
                i++;

                if(i == towns.length-1) {
                    break;
                }
            } else {
                j++;
            }
        }

        return (distance == 0 || j == routes.size()) ? -1 : distance;
    }

    /**
     * Recursive method dto get all the possible routes from a source dto a target
     * @param source Source dto be searched
     * @param target Target that we are searching
     * @param routes List with all the routes related dto the graph
     * @param sourceTargets List where we'll dto store all the possible routes
     * @return List with all the possible routes given a source and target
     */
    public static List<String> getTargetsFromSource(
            String source, String target, List<Route> routes, List<String> sourceTargets){
        for (Route route : routes) {
            if(source.equals(route.getSource())){
                sourceTargets.add(route.getTarget());

                if(target.equals(route.getTarget())){
                    break;
                } else {
                    getTargetsFromSource(route.getTarget(), target, routes, sourceTargets);
                }
            }
        }

        return sourceTargets;
    }
}
