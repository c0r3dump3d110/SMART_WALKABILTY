package com.fstm.coredumped.smartwalkabilty.core.danger.model.bo;

public class Accident extends Danger
{
    @Override
    public int CalculateRisk() {
        return degree*10;
    }
    @Override
    public String toString() {
        return "Accident";
    }
}
