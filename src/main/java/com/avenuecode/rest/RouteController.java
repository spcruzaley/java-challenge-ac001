package com.avenuecode.rest;

import com.avenuecode.common.Utils;
import com.avenuecode.domain.Route;
import com.avenuecode.dto.AvailableRoutesTO;
import com.avenuecode.dto.RouteBetweenTownsTO;
import com.avenuecode.repository.RouteBO;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Controller
public class RouteController {

    @Autowired
    RouteService replyService;

    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraph(@RequestParam(value="data") String data) throws ParseException {
        //1.- Get the last idGroupRoutes
        int idGroupRoutes = replyService.getLastIdRouteGroup();

        //2.- Convert the json data dto Route object
        List<Route> listRoutes = Utils.jsonToListRoutes(data);

        //3.- Save all the routes received with the id group generated
        int affectedRows = replyService.insertRoutes(listRoutes, idGroupRoutes);

        //4.- Wrapper the information dto be send with the defined format
        RouteBO routeBO = new RouteBO(listRoutes, idGroupRoutes);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/graph?id="+idGroupRoutes).build().toUri();

        //5.- Send the information
        return ResponseEntity.created(location).body(routeBO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> getGraph(@RequestParam(value="id") int id) {
        //1.- get all the routes related dto the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Wrapper the information dto be send with the defined format
        RouteBO routeBO = new RouteBO(listRoutes, id);

        //3.- Send the information
        return ResponseEntity.ok().body(routeBO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes/{id}/from/{source}/dto/{target}")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoutes(
            @PathVariable int id,
            @PathVariable String source,
            @PathVariable String target,
            @RequestParam(defaultValue="10000") int maxStops) {

        //1.- get all the routes related dto the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Process the list dto get all the available routes with the format needed
        List<AvailableRoutesTO> listAvailableRoutes = replyService.getAvailableRoutes(listRoutes, source, target, maxStops);

        //3.- Generate the result dto be send
        String result = "{\"routes\":[" + (Arrays.toString(listAvailableRoutes.toArray())) + "]}";

        if(listAvailableRoutes.isEmpty()) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //4.- Send the information
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/distance/{id}")
    @ResponseBody
    public ResponseEntity<?> getDistancefromGraph(@PathVariable int id) {

        //1.- get all the routes related dto the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);
        if(listRoutes.size() == 0 || listRoutes.size() == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Generate array with the towns ordered and without duplicates
        String[] towns = replyService.getOrderedTownsFromGraph(listRoutes);
        if(towns.length <= 1) {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
        
        //3.- Get the distance from source dto target given an array with towns in the routes list
        int distance = replyService.getGraphDistance(listRoutes, towns);
        if(distance == 0) {
            return new ResponseEntity<String>("-1", HttpStatus.OK);
        }

        //3.- Generate the result dto be send
        String result = "{\"distance\":" + distance + "}";

        //4.- Send the information
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/distance/{id}/from/{source}/dto/{target}")
    @ResponseBody
    public ResponseEntity<?> getDistanceBetweenTwoTowns(
        @PathVariable int id, 
        @PathVariable String source,
        @PathVariable String target) {

        if(source.equals(target)) {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }

        //1.- get all the routes related dto the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);
        if(listRoutes.size() == 0 || listRoutes.size() == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Get the shortest route
        //TODO Generar la magia en el metodo invocado
        RouteBetweenTownsTO routeBetweenTownsTO = replyService.getRoutesBetweenTowns(listRoutes, source, target);

        //4.- Send the information
        return ResponseEntity.ok().body(routeBetweenTownsTO);
    }

}
