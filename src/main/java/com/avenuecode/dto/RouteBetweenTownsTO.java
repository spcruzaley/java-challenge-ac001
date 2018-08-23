package com.avenuecode.dto;

public class RouteBetweenTownsTO {
    private int distance;
    private String[] towns;

    public RouteBetweenTownsTO() {

    }

    public RouteBetweenTownsTO(int distance, String[] towns) {
        this.distance = distance;
        this.towns = towns;
    }

    public int getDistance() {
        return distance;
    }

    public String[] getTowns() {
        return towns;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setTowns(String[] towns) {
        this.towns = towns;
    }
}