package com.avenuecode.domain;

public class Route {
    private int id;
    private String source;
    private String target;
    private int distance;
    private int idRouteGroup;

    public Route() {
        super();
    }

    public Route(int idRouteGroup, String source, String target, int distance) {
        super();
        this.idRouteGroup = idRouteGroup;
        this.source = source;
        this.target = target;
        this.distance = distance;
    }

    public int getId() {
        return id;
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

    public int getIdRouteGroup() {
        return idRouteGroup;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setIdRouteGroup(int idRouteGroup) {
        this.idRouteGroup = idRouteGroup;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", source=" + source +
                ", target=" + target +
                ", distance=" + distance +
                ", idRouteGroup=" + idRouteGroup +
                '}';
    }
}
