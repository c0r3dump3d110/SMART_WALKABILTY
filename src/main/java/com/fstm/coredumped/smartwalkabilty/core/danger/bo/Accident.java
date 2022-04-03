package com.fstm.coredumped.smartwalkabilty.core.danger.bo;

public class Accident extends Danger
{
    @Override
    public double CalculateRisk() {
        return degree*10;
    }
    @Override
    public String toString() {
        return "Accident";
    }
}
