package com.avenuecode.repository;

import com.avenuecode.dto.AvailableRoutesTO;

import java.util.List;

public class AvailableRouteBO {
    private List<AvailableRoutesTO> routes;

    public AvailableRouteBO() {
    }

    public AvailableRouteBO(List<AvailableRoutesTO> routes) {
        this.routes = routes;
    }

    public List<AvailableRoutesTO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<AvailableRoutesTO> routes) {
        this.routes = routes;
    }
}
