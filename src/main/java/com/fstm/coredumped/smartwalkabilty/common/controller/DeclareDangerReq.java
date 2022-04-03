package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.danger.bo.Danger;

public class DeclareDangerReq
{
    private Danger danger;
    private GeoPoint dangerLocation;

    public DeclareDangerReq() {
    }

    public DeclareDangerReq(Danger danger, GeoPoint dangerLocation) {
        this.danger = danger;
        this.dangerLocation = dangerLocation;
    }

    public Danger getDanger() {
        return danger;
    }

    public void setDanger(Danger danger) {
        this.danger = danger;
    }

    public GeoPoint getDangerLocation() {
        return dangerLocation;
    }

    public void setDangerLocation(GeoPoint dangerLocation) {
        this.dangerLocation = dangerLocation;
    }
}
