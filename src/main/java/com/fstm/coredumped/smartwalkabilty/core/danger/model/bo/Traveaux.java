package com.fstm.coredumped.smartwalkabilty.core.danger.model.bo;

public class Traveaux extends Danger
{
    @Override
    public int CalculateRisk() {
        return degree*2;
    }
    @Override
    public String toString() {
        return "Traveaux";
    }
}
