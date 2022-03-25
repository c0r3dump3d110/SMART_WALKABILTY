package com.fstm.coredumped.smartwalkabilty.core.geofencing.model.dao;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Chemin;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.Connexion;


import java.sql.*;
import java.util.*;

public class DAOGAnnonce {

    public Set<Integer> getSitesOfChemin(Chemin chemin, double radius) throws SQLException {
        Set<Integer> idSet = new HashSet<Integer>();
        Connection connection = Connexion.getConnection();
        List<Integer> list = new ArrayList<Integer>();

        for (Vertex v : chemin.getVertices()){
            list.add(v.getId());
        }

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM site \n" +
                "WHERE ST_Contains(ST_Buffer(ST_SetSRID((SELECT ST_Union(( SELECT ARRAY(( SELECT the_geom FROM ways WHERE gid = any (?))))))\n" +
                "\t\t\t\t\t\t\t\t\t   , 4326)::geography\n" +
                "\t\t\t\t\t\t\t,?)::geometry\n" +
                "\t\t\t\t  ,ST_SetSRID(ST_MakePoint(longitude,latitude), 4326));");
        Array array = connection.createArrayOf("bigint",list.toArray());
        ps.setArray(1,array);
        ps.setDouble(2,radius);
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
                + "ST_Contains(ST_Buffer(ST_SetSRID(ST_MakePoint(?,?), 4326)::geography,?)::geometry,ST_SetSRID(ST_MakePoint(longitude,latitude), 4326));");
        ps.setDouble(2,point.getLaltittude());
        ps.setDouble(1,point.getLongtitude());
        ps.setDouble(3,radius);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            idSet.add(rs.getInt("id"));
        }
        return idSet;
    }
}
