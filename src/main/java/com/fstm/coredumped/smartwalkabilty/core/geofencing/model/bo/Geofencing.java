package com.fstm.coredumped.smartwalkabilty.core.geofencing.model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.geofencing.model.dao.DAOGAnnonce;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.*;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Geofencing implements Observer{

    Routage routing;
    double radius;

    @Override
    public void update() {
        //findAllAnnonces();
    }

    public Geofencing(Routage routnig, double radius){
        this.routing = routnig;
        this.radius = radius;
    }

    void findAllAnnonces(){
        DAOGAnnonce daoga = new DAOGAnnonce();
        for (Chemin c : routing.getChemins()){
            try {
                Set<Integer> ids = daoga.getSitesOfChemin(c,this.radius);
                for (Integer id : ids) {
                    c.getAnnonces().addAll(daoga.getAnnoncesByIdSite(id));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static List<Annonce> findAllAnnoncesByRadius(GeoPoint point, double radius){
        List<Annonce> listAnnonces = new ArrayList<>();
        DAOGAnnonce daoga = new DAOGAnnonce();
            try {
                Set<Integer> ids = daoga.getSitesOfPoint(point,radius);
                for (Integer id : ids) {
                    listAnnonces.addAll(daoga.getAnnoncesByIdSite(id));
                }
                return listAnnonces;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
    }
}
