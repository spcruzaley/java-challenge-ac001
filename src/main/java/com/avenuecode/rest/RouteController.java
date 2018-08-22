package com.avenuecode.rest;

import com.avenuecode.repository.AvailableRouteBO;
import com.avenuecode.repository.RouteBO;
import com.avenuecode.repository.RouteBuilder;
import com.avenuecode.to.AvailableRoutesTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController {

    @Autowired
    RouteReply replyRoute;

    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraph(@RequestParam(value="data") String data) {
        int idRouteCreated = replyRoute.insert(data);

        RouteBO routeBO = new RouteBO();
        if(idRouteCreated > 0) {
            routeBO = replyRoute.get(idRouteCreated);
        }

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/graph?id="+idRouteCreated).build().toUri();

        return ResponseEntity.created(location).body(routeBO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> getGraph(@RequestParam(value="id") int id) {
        RouteBO routeBO = replyRoute.get(id);

        return ResponseEntity.ok().body(routeBO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes/{id}/from/{source}/to/{target}")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoutes(
            @PathVariable int id,
            @PathVariable String source,
            @PathVariable String target,
            @RequestParam(defaultValue="10000") int maxStops) {
        RouteBO routeBO = replyRoute.get(id);
        List<AvailableRoutesTO> availableRoutes = replyRoute.getAvailableRoutes(source, target, id, maxStops);
        AvailableRouteBO routes = new AvailableRouteBO(availableRoutes);

        if(availableRoutes.isEmpty()) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(routes);
    }

/*    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraphReply(@RequestParam(value="data") String jsonData) {
        RouteReply replyRoute = new RouteReply();
        RouteBuilder resource = replyRoute.insert(jsonData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resource.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }*/
}
