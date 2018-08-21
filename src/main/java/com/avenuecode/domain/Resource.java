package com.avenuecode.domain;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    private int id;
    private List<Route> data;

    public Resource() {
        this.data = new ArrayList<Route>();
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
