package com.avenuecode.dto;

public class RouteBetweenTownsDTO {
    private int distance;
    private String[] towns;

    public RouteBetweenTownsDTO(int distance, String[] towns) {
        this.distance = distance;
        this.towns = towns;
    }

    public int getDistance() {
        return distance;
    }

    public String[] getTowns() {
        return towns;
    }

}