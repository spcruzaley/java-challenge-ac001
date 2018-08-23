package com.avenuecode.rest;

import com.avenuecode.common.Utils;
import com.avenuecode.domain.Route;
import com.avenuecode.dto.AvailableRoutesDTO;
import com.avenuecode.dto.RouteBetweenTownsDTO;
import com.avenuecode.dto.RouteDTO;
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
        //1.- Get the last id
        int id = replyService.getLastIdRouteGroup();
        id += 1;

        //2.- Convert the json data to Route object
        List<Route> listRoutes = Utils.jsonToListRoutes(data);

        //3.- Save all the routes received with the id group generated
        int affectedRows = replyService.insertRoutes(listRoutes, id);

        //4.- Wrapper the information to be send with the defined format
        RouteDTO routeBO = new RouteDTO(listRoutes, id);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/graph?id="+id).build().toUri();

        //5.- Send the information
        return ResponseEntity.created(location).body(routeBO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> getGraph(@RequestParam(value="id") int id) {
        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Wrapper the information to be send with the defined format
        RouteDTO routeDTO = new RouteDTO(listRoutes, id);

        //3.- Send the information
        return ResponseEntity.ok().body(routeDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes/{id}/from/{source}/to/{target}")
    @ResponseBody
    public ResponseEntity<?> getAvailableRoutes(
            @PathVariable int id,
            @PathVariable String source,
            @PathVariable String target,
            @RequestParam(defaultValue="100000") int maxStops) {

        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);

        //2.- Process the list to get all the available routes with the format needed
        List<AvailableRoutesDTO> listAvailableRoutes = replyService.getAvailableRoutes(listRoutes, source, target, maxStops);

        //3.- Generate the result to be send
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

        //1.- get all the routes related to the id group routes
        List<Route> listRoutes = replyService.getRoutes(id);
        if(listRoutes.size() == 0 || listRoutes.size() == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Generate array with the towns ordered and without duplicates
        String[] towns = replyService.getOrderedTownsFromGraph(listRoutes);
        if(towns.length <= 1) {
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
        
        //3.- Get the distance from source to target given an array with towns in the routes list
        int distance = replyService.getGraphDistance(listRoutes, towns);
        if(distance == 0) {
            return new ResponseEntity<String>("-1", HttpStatus.OK);
        }

        //3.- Generate the result to be send
        String result = "{\"distance\":" + distance + "}";

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
        if(listRoutes.size() == 0 || listRoutes.size() == 1) {
            return new ResponseEntity<String>("NOT FOUND", HttpStatus.NOT_FOUND);
        }

        //2.- Get the shortest route
        RouteBetweenTownsDTO routeBetweenTownsTO = replyService.getRoutesBetweenTowns(listRoutes, source, target);

        //4.- Send the information
        return ResponseEntity.ok().body(routeBetweenTownsTO);
    }

}
