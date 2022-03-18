package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

public class GeoPoint {
    private static int num=0;
    public GeoPoint() {

    }

    public GeoPoint(int id, double laltittude, double longtitude) {
        this.id = id;
        this.laltittude = laltittude;
        this.longtitude = longtitude;
    }

    private int id=++num;
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







}
