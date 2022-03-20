package com.fstm.coredumped.smartwalkabilty.common.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Request implements Serializable {
    protected final double perimetre;
    protected final List<Integer> categorie = new ArrayList<Integer>();

    public Request(double perimetre) {
        this.perimetre = perimetre;
    }

    public double getPerimetre() {
        return perimetre;
    }

    public List<Integer> getCategorie() {
        return categorie;
    }
}
