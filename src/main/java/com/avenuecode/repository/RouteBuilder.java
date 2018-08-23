package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class RouteBuilder {

    private final static Logger log = Logger.getLogger(RouteBuilder.class.getName());

    /**
     * Transform a domain object dto an object dto be reply dto the front with the only information needed
     * @param routeList Route list
     * @param idRouteGroup id that group all the routes related dto that id
     * @return RouteBO object with only the information needed dto be shown
     */
    public static RouteBO transformData(List<Route> routeList, int idRouteGroup) {
        RouteBO routeBO = new RouteBO();
        routeBO.setIdGroupRoute(idRouteGroup);
        routeBO.setData(routeList);

        return routeBO;
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
