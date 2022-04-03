package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;

import java.util.List;

public class RequestPerimetreAnnonce extends RequestPerimetre{
    private final List<Integer> categorieList;

    public RequestPerimetreAnnonce(double perimetre, GeoPoint actualPoint, List<Integer> categorieList) {
        super(perimetre, actualPoint);
        this.categorieList = categorieList;
    }

    public List<Integer> getCategorieList() {
        return categorieList;
    }
}
