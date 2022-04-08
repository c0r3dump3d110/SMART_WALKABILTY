package com.fstm.coredumped.smartwalkabilty.core.danger.model.bo;

public class Vol extends Danger
{
    @Override
    public int CalculateRisk() {
        return degree*8;
    }
    @Override
    public String toString() {
        return "Vol";
    }
}
