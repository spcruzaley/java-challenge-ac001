package com.avenuecode.repository;

import com.avenuecode.to.RouteTO;

import java.util.List;

public class RouteBO {
    private int id;
    private List<RouteTO> data;

    public RouteBO() {}

    public int getId() {
        return id;
    }

    public List<RouteTO> getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(List<RouteTO> data) {
        this.data = data;
    }
}
