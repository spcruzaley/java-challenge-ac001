package com.avenuecode.domain;

public class Town {
    private int id;
    private String label;

    public Town() {
        super();
    }

    public Town(int id, String label) {
        super();
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Town{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
