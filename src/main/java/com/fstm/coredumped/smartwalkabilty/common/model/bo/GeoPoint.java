package com.fstm.coredumped.smartwalkabilty.common.model.bo;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.io.Serializable;
import java.util.Objects;

public class GeoPoint implements Serializable {
    private static final long serialVersionUID=15L;
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
        return "["+this.laltittude+","+this.longtitude+"]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(laltittude, longtitude);
    }
    public double distanceToInMeters(GeoPoint geoPoint)
    {
        double R = 6371;//EarthRadios
        double lat1 = toRadians(laltittude);
        double long1 = toRadians(longtitude);
        double lat2 = toRadians(geoPoint.laltittude);
        double long2 = toRadians(geoPoint.longtitude);
        double dlong = long2 - long1;
        double dlat = lat2 - lat1;
        double ans = pow(sin(dlat / 2), 2) + cos(lat1) * cos(lat2) * pow(sin(dlong / 2), 2);
        ans = 2 * asin(sqrt(ans));
        ans*=1000*R;
        return ans;
    }
    public static double toRadians(double ree)
    {
        double one_deg = (Math.PI) / 180;
        return (one_deg * ree);
    }
}

