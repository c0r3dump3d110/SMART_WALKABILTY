package com.fstm.coredumped.smartwalkabilty.routing.Model.dao;

import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Dijkistra;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Graph;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        GeoPoint p1 = new GeoPoint(-7.5932469, 33.5883562);
        GeoPoint p2 = new GeoPoint(-7.5942485, 33.5779598);

        Graph graph = new DAOGraph().getTheGraph(p1, p2);
        List<Chemin> chemins = new Dijkistra().doAlgo(graph, p1, p2);

        System.out.println(chemins);
    }
}
