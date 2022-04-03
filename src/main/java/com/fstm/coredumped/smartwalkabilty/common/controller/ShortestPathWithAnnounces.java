package com.fstm.coredumped.smartwalkabilty.common.controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;

import java.util.List;

public class ShortestPathWithAnnounces extends RequestPerimetreAnnonce{
    private final GeoPoint pointArrivee;

    public ShortestPathWithAnnounces(double perimetre, GeoPoint actualPoint, List<Integer> categorieList, GeoPoint pointArrivee) {
        super(perimetre, actualPoint, categorieList);
        this.pointArrivee = pointArrivee;
    }

    public GeoPoint getPointArrivee() {
        return pointArrivee;
    }

}
