package com.avenuecode.to;

public class RouteTO {
    private String source;
    private String target;
    private int distance;

    public RouteTO() {
    }

    public RouteTO(String source, String target, int distance) {
        this.source = source;
        this.target = target;
        this.distance = distance;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getDistance() {
        return distance;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "RouteTO{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", distance=" + distance +
                '}';
    }
}
