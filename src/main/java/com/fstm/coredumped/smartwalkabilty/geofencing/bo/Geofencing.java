package com.fstm.coredumped.smartwalkabilty.geofencing.bo;

import com.fstm.coredumped.smartwalkabilty.geofencing.dao.DAOGAnnonce;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.*;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Geofencing implements Observer{

    Routage routing;
    double radius;

    @Override
    public void update() {
        findAllAnnonces();
        sendData();
    }

    public Geofencing(Routage routnig, double radius){
        this.routing = routing;
        this.radius = radius;
    }

    void sendData(){

    }

    void findAllAnnonces(){
        Set<Annonce> annonceSet = new HashSet<Annonce>();
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
}
