package com.avenuecode.rest;

import com.avenuecode.repository.RouteBO;
import com.avenuecode.repository.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
