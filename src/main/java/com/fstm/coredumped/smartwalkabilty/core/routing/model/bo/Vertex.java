package com.fstm.coredumped.smartwalkabilty.core.routing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.io.Serializable;
import java.util.Objects;

public class Vertex implements Serializable
{
    private static final long serialVersionUID=5L;
    static int count=0;
    int id;
    private GeoPoint depart;
    private GeoPoint Arrive;
    private double distance;

    public Vertex(GeoPoint depart, GeoPoint arrive, double distance) {
        this.depart = depart;
        Arrive = arrive;
        this.distance = distance;
        id=++count;
    }

    public Vertex() {
    }

    public Vertex(int id, GeoPoint depart, GeoPoint arrive, double distance) {
        this.id = id;
        this.depart = depart;
        Arrive = arrive;
        this.distance = distance;
    }

    public GeoPoint getDepart() {
        return depart;
    }

    public void setDepart(GeoPoint depart) {
        this.depart = depart;
    }

    public GeoPoint getArrive() {
        return Arrive;
    }

    public void setArrive(GeoPoint arrive) {
        Arrive = arrive;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if(id==0)return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "["+this.depart.toString()+"], ["+this.Arrive.toString()+"]";
    }
}
