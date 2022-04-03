package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private final GeoPoint actualPoint;

    protected Request(GeoPoint actualPoint) {
        this.actualPoint = actualPoint;
    }

    public GeoPoint getActualPoint() {
        return actualPoint;
    }
}
