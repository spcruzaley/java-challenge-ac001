package com.avenuecode.dto;

public class AvailableRoutesDTO {
    private String route;
    private int stops;

    public AvailableRoutesDTO() {
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

    @Override
    public String toString() {
        return "{" +
                "route='" + route + '\'' +
                ", stops=" + stops +
                '}';
    }
}
