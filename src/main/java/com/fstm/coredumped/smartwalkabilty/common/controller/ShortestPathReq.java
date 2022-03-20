package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.io.Serializable;
import java.util.List;

public class ShortestPathReq extends Request implements Serializable {
    private final GeoPoint depPoint;
    private final GeoPoint arrPoint;

    public ShortestPathReq(double perimetre, GeoPoint depPoint, GeoPoint arrPoint) {
        super(perimetre);
        this.depPoint = depPoint;
        this.arrPoint = arrPoint;
    }

    public GeoPoint getDepPoint() {
        return depPoint;
    }

    public GeoPoint getArrPoint() {
        return arrPoint;
    }
}
