package com.fstm.coredumped.smartwalkabilty.routing.Model.dao;

import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.geofencing.dao.DAOGAnnonce;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Dijkistra;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Graph;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Vertex;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class TestGeofencing {
    public static void main(String[] args) {
            GeoPoint p1 = new GeoPoint(-7.6105184,33.5941665);
            GeoPoint p2 = new GeoPoint(-7.62948262,33.58560119);

            Graph graph = new DAOGraph().getTheGraph(p1, p2);

            List<Chemin> chemins = new Dijkistra().doAlgo(graph, p1, p2);
//            for (Chemin c : chemins){
//                System.out.println("CHEMIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIN");
//                for (Vertex v : c.getVertices()){
//                    System.out.println(v);
//                }
//            }

        try {
            Set<Integer> ids = new DAOGAnnonce().getSitesOfPoint(new GeoPoint(-7.628227,33.584546),150);
            System.out.println(ids);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
