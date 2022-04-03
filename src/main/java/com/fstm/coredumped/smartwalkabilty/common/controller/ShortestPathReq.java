package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.io.Serializable;
import java.util.List;

public class ShortestPathReq extends Request implements Serializable {
    private final GeoPoint arrPoint;

    public ShortestPathReq(GeoPoint depPoint, GeoPoint arrPoint) {
        super(depPoint);
        this.arrPoint = arrPoint;
    }

    public GeoPoint getArrPoint() {
        return arrPoint;
    }
}
