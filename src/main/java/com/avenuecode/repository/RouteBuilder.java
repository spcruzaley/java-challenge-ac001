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

}
