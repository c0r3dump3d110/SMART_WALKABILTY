package com.fstm.coredumped.smartwalkabilty.core.geofencing.model.dao;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.Connexion;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOGAnnonce {

    public Set<Integer> getSitesOfChemin(Chemin chemin, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = Connexion.getConnection();
        List<Integer> list = new ArrayList<Integer>();
        String listOfGid = "( ";

        for (Vertex v : chemin.getVertices()){
            //list.add(v.getId());
            listOfGid += v.getId()+",";
        }
        listOfGid = listOfGid.substring(0, listOfGid.length() - 1);

        listOfGid += ")";

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site\n" +
                "WHERE \n" +
                "ST_Contains(ST_Buffer(ST_SetSRID( (SELECT ST_Union(( SELECT ARRAY(( SELECT the_geom FROM ways WHERE gid\n" +
                "\t\t\t\t\t\t\t\t IN "+ listOfGid +" ))" +
                ")))\n" +
                "\t\t\t\t\t\t\t\t , 4326)::geography,?)::geometry,geomsite) ;");

        //ps.setArray(1,connection.createArrayOf("bigint",list.toArray()));
        //ps.setString(1,listOfGid);
        ps.setDouble(1,radius);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }

    public Set<Integer> getSitesOfPoint(GeoPoint point, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.Connexion.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site WHERE "
                + "ST_Contains(ST_Buffer(ST_SetSRID(ST_MakePoint(?,?), 4326)::geography,?)::geometry,geomsite);");
        ps.setDouble(1,point.getLaltittude());
        ps.setDouble(2,point.getLongtitude());
        ps.setDouble(3,radius);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }

    public Set<Annonce> getAnnoncesByIdSite (int idSite) throws SQLException {
        Set<Annonce> annonceSet = new HashSet<Annonce>();
        Connection connection = com.fstm.coredumped.smartwalkabilty.web.Model.dao.Connexion.getCon();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM "
        +"annonces as a, annonces_con_site as acs"
        + "WHERE a.id=acs.id_annonce AND acs.id_site=? ;");
        ps.setInt(1,idSite);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Annonce a = new Annonce();
            a.setId(rs.getInt("id"));
            a.setDateDebut(rs.getDate("datedebut"));
            a.setDateFin(rs.getDate("datefin"));
            a.setTitre(rs.getString("titre"));
            a.setUrlPrincipalImage(rs.getString("urlimageprincipale"));
            a.setDescription(rs.getString("description"));
            annonceSet.add(a);
        }
        return annonceSet;
    }
}
