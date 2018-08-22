package com.avenuecode.to;

public class AvailableRoutesTO {
    private String route;
    private int stops;

    public AvailableRoutesTO() {
    }

    public String getRoute() {
        return route;
    }

    public int getStops() {
        return stops;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }
}
