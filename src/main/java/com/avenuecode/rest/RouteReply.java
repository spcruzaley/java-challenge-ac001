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
public class RouteReply {
    private static Logger logger = Logger.getLogger("InfoLogging");

    @Autowired
    private RouteRepository routeRepository;

    public RouteReply() {
        this.routeRepository = new RouteRepository();
    }

    public RouteReply(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Call to Repository for Route in order to store the information received
     * @param data json incoming
     * @return id route group generated
     * @throws ParseException In case that the json data received comes malformed
     */
    public int insert(String data) {
        int idRouteGroup = getLastIdRouteGroup() + 1;

        try {
            List<Route> listRoutesToSave = Utils.jsonToListRoutes(data, idRouteGroup);

            //Save routes
            for(Route route: listRoutesToSave) {
                routeRepository.insert(route);
            }
        } catch (ParseException e) {
            idRouteGroup = 0;
        }

        return idRouteGroup;
    }

    /**
     * Get an specific graph from an id
     * @param id from the graph generated previously
     * @return RouteBuilder object with the information, or else an ampty data
     */
    public RouteBO get(int id) {
        List<Route> listRoutes = routeRepository.findByIdRouteGroup(id);
        RouteBO routeBO = RouteBuilder.transformData(listRoutes, id);

        return routeBO;
    }

    public List<AvailableRoutesTO> getAvailableRoutes(String source, String target, int id, int maxStops) {
        AvailableRoutesTO availableRoutesTO;
        List<AvailableRoutesTO> routes = new ArrayList<>();
        List<Route> listRoutes = routeRepository.findByIdRouteGroup(id);
        List<String> allPossibleRoutes = RouteBuilder.getTargetsFromSource(source, target, listRoutes,
                new ArrayList<String>());

        StringBuilder twonRoutes = new StringBuilder();;
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
