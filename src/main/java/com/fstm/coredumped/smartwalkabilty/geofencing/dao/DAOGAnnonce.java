package com.fstm.coredumped.smartwalkabilty.geofencing.dao;

import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Vertex;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOGAnnonce {

    /*public Set<Integer> getSitesOfChemin(Chemin chemin, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        String st_buffer = "ST_Buffer('LINESTRING(";
        for (Vertex v : chemin.getVertices()){
            st_buffer += v.getDepart() + " " + v.getArrive() + ",";
        }
        st_buffer = st_buffer.substring(0, st_buffer.length() - 1);
        st_buffer += ")," + radius + " ,'endcap=round join=round');";
        Connection connection = com.fstm.coredumped.smartwalkabilty.routing.Model.dao.Connexion.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site WHERE"
        + "ST_Contains(?,sitegeom) == true");
        ps.setString(1,st_buffer);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }*/
    public Set<Integer> getSitesOfChemin(Chemin chemin, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = com.fstm.coredumped.smartwalkabilty.routing.Model.dao.Connexion.getConnection();
        List<Integer> list = new ArrayList<Integer>();
        //String listOfIds = "(";
        for (Vertex v : chemin.getVertices()){
            //listOfIds += " " + v.getId()+",";
            list.add(v.getId());
        }
        //listOfIds = listOfIds.substring(0, listOfIds.length() - 1);
        //listOfIds += ")";

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site WHERE"
                + "ST_Contains(ST_Buffer(ST_Collect(SELECT the_geom FROM ways WHERE gid IN ?),?),sitegeom);");
        //ps.setString(1,listOfIds);
        ps.setDouble(2,radius);
        ps.setArray(1,connection.createArrayOf("bigint",list.toArray()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }

    public Set<Integer> getSitesOfPoint(GeoPoint point, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = com.fstm.coredumped.smartwalkabilty.routing.Model.dao.Connexion.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site WHERE"
                + "ST_Contains(ST_Buffer(ST_SetSRID(ST_MakePoint(?,?), 4326),?),sitegeom);");
        ps.setDouble(1,point.getLongtitude());
        ps.setDouble(2,point.getLaltittude());
        ps.setDouble(3,radius);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }
    /*public Set<Integer> getSitesOfVertex(Vertex vertex) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = com.fstm.coredumped.smartwalkabilty.routing.Model.dao.Connexion.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site WHERE"
                + "ST_Contains(ST_Buffer(ST_Collect(SELECT the_geom FROM ways WHERE gid IN ?)),sitegeom) == true");
        ps.setString(1,);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
            return idSet;
    }*/

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
