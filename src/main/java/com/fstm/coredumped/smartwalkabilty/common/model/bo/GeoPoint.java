package com.fstm.coredumped.smartwalkabilty.common.model.bo;

import java.util.Objects;

public class GeoPoint {
    private static int num=0;
    public GeoPoint() {

    }

    public GeoPoint(double laltittude, double longtitude) {
        this.laltittude = laltittude;
        this.longtitude = longtitude;
        id=++num;
    }

    public GeoPoint(int id, double laltittude, double longtitude) {
        this.id = id;
        this.laltittude = laltittude;
        this.longtitude = longtitude;
    }

    private int id;
    private double laltittude;
    private double longtitude;

    public double getLaltittude() {
        return laltittude;
    }

    public void setLaltittude(double laltittude) {
        this.laltittude = laltittude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint geoPoint = (GeoPoint) o;
        return Double.compare(geoPoint.laltittude, laltittude) == 0 && Double.compare(geoPoint.longtitude, longtitude) == 0;
    }
    @Override
    public String toString() {
        return "GeoPoint{" +
                "id=" + id +
                ", laltittude=" + laltittude +
                ", longtitude=" + longtitude +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(laltittude, longtitude);
    }
}

