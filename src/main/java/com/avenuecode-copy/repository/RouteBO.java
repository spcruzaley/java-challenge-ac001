package com.avenuecode.repository;

import com.avenuecode.domain.Route;

import java.util.List;

public class RouteBO {
    private int idGroupRoute;
    private List<Route> data;

    public RouteBO() {}

    public RouteBO(List<Route> data, int idGroupRoute) {
        this.data = data;
        this.idGroupRoute = idGroupRoute;
        
    }

    public int getIdGroupRoute() {
        return idGroupRoute;
    }

    public List<Route> getData() {
        return data;
    }

    public void setIdGroupRoute(int idGroupRoute) {
        this.idGroupRoute = idGroupRoute;
    }

    public void setData(List<RouteTO> data) {
        this.data = data;
    }
}
