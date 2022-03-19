package com.fstm.coredumped.smartwalkabilty.routing.Model.dao;


import com.fstm.coredumped.smartwalkabilty.common.Model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Graph;
import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Vertex;
import com.fstm.coredumped.smartwalkabilty.routing.Model.service.GraphOperator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAOGraph implements IDAOGraph {

    @Override
    public Graph getTheGraph(GeoPoint source, GeoPoint target) {
        Graph graph = new Graph();
        // calculate distance:

        double distance = Math.sqrt(
                Math.pow((source.getLongtitude() - target.getLongtitude()), 2)+
                        Math.pow((source.getLaltittude() - target.getLaltittude()), 2)
        );
        // calculate the radius
        double Radius = (distance/2.0)*1.5;

        // calculate midPoint
        double Xcenter = (source.getLongtitude() + target.getLongtitude())/2.0;
        double Ycenter = (source.getLaltittude() + target.getLaltittude())/2.0;

        // set a projection system
        int projection_system = 4326;

        //Edges
        int edges = 2;

        // specify the_geom
        String geom = "the_geom";
        try
        {
            Connection c = Connexion.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement("select gid as id, source, target, length_m, x1, y1, x2, y2 FROM ways WHERE st_contains((\n" +
                    "SELECT ST_BUFFER (\n" +
                    "\tST_SetSRID(ST_Point(?, ?),?)\n" +
                    "\t, ?, ?\n" +
                    ")), the_geom);");

            preparedStatement.setDouble(1, Ycenter);
            preparedStatement.setDouble(2, Xcenter);

            preparedStatement.setInt(3, projection_system);

            // let the distance for now then we can use the Radius
            preparedStatement.setDouble(4, Radius);
            preparedStatement.setInt(5, edges);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Vertex v = new Vertex();
                v.setId(resultSet.getInt("id"));
                v.setDepart(new GeoPoint(resultSet.getInt("source"), resultSet.getDouble("x1"), resultSet.getDouble("y1")));
                v.setArrive(new GeoPoint(resultSet.getInt("target"), resultSet.getDouble("x2"), resultSet.getDouble("y2")));
                v.setDistance(resultSet.getDouble("length_m"));

                graph.Add_Route(v);
            }

            if(!graph.contains(source)){
                System.out.println("Graph not contains the source ");
                new GraphOperator(graph).addPoint(source, 0);
            }
            if (!graph.contains(target)){
                System.out.println("Graph not containes the target");
                new GraphOperator(graph).addPoint(target, 1);
            }

            return graph;
        }catch (Exception e){
            System.out.println("Err in graph creation: "+e);
            return graph;
        }
    }
}
