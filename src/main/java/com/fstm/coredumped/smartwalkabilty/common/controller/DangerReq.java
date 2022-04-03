package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

public class DangerReq extends RequestPerimetre{
    public DangerReq(double perimetre, GeoPoint actualPoint) {
        super(perimetre, actualPoint);
    }
}
