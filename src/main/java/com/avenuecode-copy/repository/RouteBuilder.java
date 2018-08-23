package com.avenuecode.repository;

import com.avenuecode.domain.Route;
import com.avenuecode.to.RouteTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class RouteBuilder {

    private final static Logger log = Logger.getLogger(RouteBuilder.class.getName());

    /**
     * Transform a domain object to an object to be reply to the front with the only information needed
     * @param routeList Route list
     * @param idRouteGroup id that group all the routes related to that id
     * @return RouteBO object with only the information needed to be shown
     */
    public static RouteBO transformData(List<Route> routeList, int idRouteGroup) {
        RouteBO routeBO = new RouteBO();
        List<RouteTO> routeVOS = new ArrayList<>();
        RouteTO routeVO;

        for (Route route: routeList) {
            routeVO = new RouteTO();

            routeVO.setSource(route.getSource());
            routeVO.setTarget(route.getTarget());
            routeVO.setDistance(route.getDistance());
            routeVOS.add(routeVO);
        }

        routeBO.setId(idRouteGroup);
        routeBO.setData(routeVOS);

        return routeBO;
    }

    /**
     * Recursive method to get all the possible routes from a source to a target
     * @param source Source to be searched
     * @param target Target that we are searching
     * @param routes List with all the routes related to the graph
     * @param sourceTargets List where we'll to store all the possible routes
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
