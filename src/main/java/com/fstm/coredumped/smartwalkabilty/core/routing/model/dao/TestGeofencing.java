package com.fstm.coredumped.smartwalkabilty.core.routing.model.dao;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.geofencing.model.dao.DAOGAnnonce;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Dijkistra;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Graph;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOSite;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class TestGeofencing {
    public static void main(String[] args) {
        GeoPoint p1 = new GeoPoint(33.5941665,-7.6105184);
        GeoPoint p2 = new GeoPoint(33.58560119,-7.62948262);

        Graph graph = new DAOGraph().getTheGraph(p1, p2);

        List<Chemin> chemins = new Dijkistra().doAlgo(graph, p1, p2);

        DAOGAnnonce daoga = new DAOGAnnonce();
        for (Chemin c : chemins){
            try {
                Set<Integer> ids = daoga.getSitesOfChemin(c,150);
                for (Integer id : ids) {
                    c.getSites().add(DAOSite.getDaoSite().findById(id));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        for (Site s : chemins.get(0).getSites()){
            System.out.println("======================================================");
            System.out.println(s.getName()+" "+s.getOrganisation().getNom());
            System.out.println(s.getLocalisation());
            for (Annonce a: s.getAnnonces()){
                System.out.println(a.getTitre());
            }
            System.out.println("======================================================");
        }


    }
}
