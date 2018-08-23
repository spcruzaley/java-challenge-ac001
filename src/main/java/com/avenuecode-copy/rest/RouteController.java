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
    RouteService replyService;

    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraph(@RequestParam(value="data") String data) {
        //1.- Get the last idGroupRoutes
        int idGroupRoutes = replyService.getLastIdRouteGroup();

        //2.- Convert the json data to Route object
        List<Route> listRoutes = Utils.jsonToListRoutes(data);

        //3.- Save all the routes received with the id group generated
        int affectedRows = replyService.insertRoutes(listRoutes, idGroupRoutes);

        //4.- Wrapper the information to be send with the defined format
        RouteBO routeBO = new RouteBO(listRoutes, idGroupRoutes);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/graph?id="+idGroupRoutes).build().toUri();

        //5.- Send the information
        return ResponseEntity.created(location).body(routeBO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> getGraph(@RequestParam(value="id") int id) {
        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Wrapper the information to be send with the defined format
        RouteBO routeBO = new RouteBO(listRoutes, id);

        //3.- Send the information
        return ResponseEntity.ok().body(routeBO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes/{id}/from/{source}/to/{target}")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoutes(
            @PathVariable int id,
            @PathVariable String source,
            @PathVariable String target,
            @RequestParam(defaultValue="10000") int maxStops) {

        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Process the list to get all the available routes with the format needed
        List<AvailableRoutesTO> listAvailableRoutes = replyService.getAvailableRoutes(listRoutes, source, target, maxStops);

        //3.- Generate the result to be send
        String result = "{\"routes\":[" + (Arrays.toString(listAvailableRoutes.toString())) + "]}";

        if(listAvailableRoutes.isEmpty()) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //4.- Send the information
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/distance/{id}")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoutes(@PathVariable int id) {

        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);
        if(listRoutes.length == 0 || listRoutes.length == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Generate array with the towns ordered and without duplicates
        //TODO Generar metodo en la clase de servicio
        String[] towns = replyService.getOrderedTownsFromGraph(listRoutes);
        if(towns.size() <= 1) {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
        
        //3.- Get the distance from source to target given an array with towns in the routes list
        //TODO Generar metodo en la clase de servicio
        int distance = replyService.getDistanceFromGivenArrayRoute(listRoutes, towns);
        if(distance == 0) {
            return new ResponseEntity<String>("-1", HttpStatus.OK);
        }

        //3.- Generate the result to be send
        String result = "{\"distance\":" + distance + "}";

        if(listAvailableRoutes.isEmpty()) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //4.- Send the information
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/distance/{id}/from/{source}/to/{target}")
    @ResponseBody
    public ResponseEntity<?> getDistanceBetweenTwoTowns(
        @PathVariable int id, 
        @PathVariable String source,
        @PathVariable String target) {

        if(source.equals(target)) {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }

        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);
        if(listRoutes.length == 0 || listRoutes.length == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Get the shortest route
        //TODO Generar la magia en el metodo invocado
        List<RouteBetweenTownsTO> routesBetweenTowns = replyService.getRoutesBetweenTowns(listRoutes, source, target);

        //3.- As per the list above, search the shortest distance
        int distance = Integer.MAX_LIMIT;
        int currentDistance = 0;
        String[] towns;
        for(RouteBetweenTownsTO route: routesBetweenTowns) {
            currentDistance = route.getDistance();

            if(currentDistance < distance) {
                distance = currentDistance;
                towns = route.getTowns();
            }
        }

        //3.- Generate result to send
        String result = "{\"distance\":" + distance + ", \"path\":" + towns.toString() + "}";


        //4.- Send the information
        return ResponseEntity.ok().body(result);
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
