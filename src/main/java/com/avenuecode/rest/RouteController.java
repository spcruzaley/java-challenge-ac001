package com.avenuecode.rest;

import com.avenuecode.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
public class RouteController {

    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraph(@RequestParam(value="data") String data) {
        ReplyRoute replyRoute = new ReplyRoute();
        Resource resource = replyRoute.saveGraph(data);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/graph?id="+resource.getId()).build().toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> getGraph(@RequestParam(value="id") int id) {
        ReplyRoute replyRoute = new ReplyRoute();
        Resource resource = replyRoute.getGraph(id);

        return ResponseEntity.ok().body(resource);
    }

/*    @RequestMapping(method = RequestMethod.POST, value = "/graph")
    @ResponseBody
    public ResponseEntity<?> saveGraphReply(@RequestParam(value="data") String jsonData) {
        ReplyRoute replyRoute = new ReplyRoute();
        Resource resource = replyRoute.saveGraph(jsonData);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resource.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }*/
}
