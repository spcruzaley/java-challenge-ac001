package com.avenuecode.dto;

import com.avenuecode.domain.Route;

import java.util.List;

public class RouteDTO {
    private int id;
    private List<Route> data;

    public RouteDTO() {}

    public RouteDTO(List<Route> data, int id) {
        this.data = data;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Route> getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(List<Route> data) {
        this.data = data;
    }
}
