package com.avenuecode.domain;

public class Route {
    private int id;
    private Town source;
    private Town target;
    private int distance;

    public Route() {
        super();
    }

    public Route(Town source, Town target, int distance) {
        super();
        this.source = source;
        this.target = target;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public Town getSource() {
        return source;
    }

    public Town getTarget() {
        return target;
    }

    public int getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(Town source) {
        this.source = source;
    }

    public void setTarget(Town target) {
        this.target = target;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", source=" + source +
                ", target=" + target +
                ", distance=" + distance +
                '}';
    }
}
