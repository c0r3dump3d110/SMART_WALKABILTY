package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.io.Serializable;
import java.util.List;

public class PerimetreReq extends Request implements Serializable {
    private final GeoPoint actualPoint;

    public PerimetreReq(double perimetre, GeoPoint actualPoint) {
        super(perimetre);
        this.actualPoint = actualPoint;
    }

    public GeoPoint getActualPoint() {
        return actualPoint;
    }
}
