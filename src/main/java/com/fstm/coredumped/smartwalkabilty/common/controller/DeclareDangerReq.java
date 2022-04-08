package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.danger.model.bo.Danger;

public class DeclareDangerReq extends Request
{
    private Danger danger;

    public DeclareDangerReq(Danger danger, GeoPoint dangerLocation) {
        super(dangerLocation);
        this.danger = danger;
    }

    public Danger getDanger() {
        return danger;
    }
    public void setDanger(Danger danger) {
        this.danger = danger;
    }
}
