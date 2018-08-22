package com.avenuecode.rest;

import com.avenuecode.common.Utils;
import com.avenuecode.domain.Resource;
import com.avenuecode.domain.Route;
import com.avenuecode.repository.RouteRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class ReplyRoute {
    private static Logger logger = Logger.getLogger("InfoLogging");

    @Autowired
    private RouteRepository routeRepository;

    public ReplyRoute() {
        this.routeRepository = new RouteRepository();
    }

    public ReplyRoute(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Call to Repository for Route in order to store the information received
     * @param data json incoming
     * @return Resource object with the same json data incomming plus the id generated, in other case an id with -1
     * @throws ParseException In case that the json data received comes malformed
     */
    public Resource saveGraph(String data) {
        int idRouteGroup = getLastIdRouteGroup();
        Resource resource = new Resource();

        try {
            List<Route> listRoutesToSave = Utils.jsonToListRoutes(data, idRouteGroup);
            //Save routes
            for(Route route: listRoutesToSave) {
                routeRepository.insert(route);
            }

            //Get routes
            List<Route> listRoutesToConfirm = routeRepository.findByIdRouteGroup(idRouteGroup);

            //Make the resource result
            resource.setId(idRouteGroup);
            resource.setData(listRoutesToConfirm);
        } catch (ParseException e) {
            resource.setId(-1);
        }

        return resource;
    }

    /**
     * Get an specific graph from an id
     * @param id from the graph generated previously
     * @return Resource object with the information, or else an ampty data
     */
    public Resource getGraph(int id) {
        List<Route> listRoutes = routeRepository.findByIdRouteGroup(id);
        Resource resource = new Resource();

        if(listRoutes.size() > 0) {
            resource.setId(listRoutes.get(0).getId());
            resource.getData().addAll(listRoutes);
        }

        return resource;
    }

    /**
     * Return the next id for one grpah that will be inserted in the DB
     * @return the next id to store in DB
     */
    public int getLastIdRouteGroup() {
        int maxIdRouteGroup = routeRepository.getLastIdRouteGroup();
        return (maxIdRouteGroup + 1);
    }
}
