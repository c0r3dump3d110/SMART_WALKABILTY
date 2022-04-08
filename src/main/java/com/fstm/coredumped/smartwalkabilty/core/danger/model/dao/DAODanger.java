package com.fstm.coredumped.smartwalkabilty.core.danger.model.dao;

import com.fstm.coredumped.smartwalkabilty.common.controller.DangerReq;
import com.fstm.coredumped.smartwalkabilty.common.controller.DeclareDangerReq;
import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.danger.model.bo.*;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.dao.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAODanger {
    public boolean createDeclaration(DeclareDangerReq dangerReq){

        try{

            Connection c = Connexion.getConnection();
            int vertexId = this.getWayGidByPoint(dangerReq.getActualPoint());
            int id_type = this.getIdTypeFromType(dangerReq.getDanger().toString());
            if(vertexId != -1 && id_type != -1){
                PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO declaration (id_type , id_way, date, degree) VALUES (?, ?, CURRENT_TIMESTAMP, ?)");

                preparedStatement.setInt(1, id_type);
                preparedStatement.setInt(2, vertexId);
//                preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, dangerReq.getDanger().getDegree());

                preparedStatement.executeUpdate();

                return true;

            }else {
                System.out.println("Error in receiving the ids");
                return false;
            }

        }catch (Exception e){
            System.out.println("Error in connection: "+e);
            return false;
        }
    }


    public int getWayGidByPoint(GeoPoint declaredPoint){
        try {
            Connection c = Connexion.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement("select w.gid \n" +
                    "from public.ways w\n" +
                    "order by st_distance(w.the_geom,\n" +
                    "\t\tst_setsrid(\n" +
                    "\t\t\tST_Point(?, ?)\n" +
                    "\t\t\t,4326)) asc limit 1");

            preparedStatement.setDouble(1, declaredPoint.getLongtitude());
            preparedStatement.setDouble(2, declaredPoint.getLaltittude());

            ResultSet set = preparedStatement.executeQuery();
            if(set.next()) {
                System.out.println("the Vertex: "+ set.getInt("gid"));
                return set.getInt("gid");
            }


            return -1;
        }catch (Exception e){
            System.out.println("Error searching the closest vertex: "+e);
            return -1;
        }
    }

    public int getIdTypeFromType(String type){
        try {
            Connection c = Connexion.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement("SELECT id_type from dangertype where name LIKE ?");
            preparedStatement.setString(1, type);

            ResultSet set =preparedStatement.executeQuery();
            if(set.next()) {
                System.out.println("Id type: "+set.getInt("id_type"));
                return set.getInt("id_type");
            }
            return -1;
        } catch (Exception e){
            System.out.println("Execption in Getting the ID: "+e);
            return -1;
        }
    }

    public List<Declaration> retrieveDeclarations(DangerReq dangerReq){
        List<Declaration> declarations = new ArrayList<>();
        int hours = 7200;

        try {

            Connection c = Connexion.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement("select t.name, w.x1, w.y1, w.x2, w.y2, w.length_m, w.risk, d.date, d.degree \n" +
                    "FROM dangertype t JOIN declaration d on d.id_type = t.id_type JOIN ways w on d.id_way = w.gid \n" +
                    "\n" +
                    "WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - d.date)) < ? \n" +
                    "\n" +
                    "and ST_Intersects(( \n" +
                    "           SELECT ST_BUFFER (\n" +
                    "           ST_SetSRID(ST_Point(?, ?), ?), ?, ?)), w.the_geom); ");


            preparedStatement.setInt(1, hours);
            preparedStatement.setDouble(2, dangerReq.getActualPoint().getLongtitude());
            preparedStatement.setDouble(3, dangerReq.getActualPoint().getLaltittude());
            preparedStatement.setInt(4, 4326);
            preparedStatement.setDouble(5, dangerReq.getPerimetre());
            preparedStatement.setInt(6, 2);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Danger danger = null;

                if(resultSet.getString("name").equals("Accident")){
                    danger = new Accident();
                }
                if(resultSet.getString("name").equals("Traveaux")){
                    danger = new Traveaux();
                }
                if(resultSet.getString("name").equals("Vol")){
                    danger = new Vol();
                }
                if(danger != null){

                    Vertex vertex = new Vertex(
                            new GeoPoint(resultSet.getDouble("y1"), resultSet.getDouble("x1")),
                            new GeoPoint(resultSet.getDouble("y2"), resultSet.getDouble("x2")),
                            resultSet.getDouble("length_m")
                    );
                    vertex.setRisk(resultSet.getDouble("risk"));

                    declarations.add(new Declaration(danger, vertex, resultSet.getTimestamp("date")));
                }
                else {
                    System.out.println("Danger not found");
                }

            }

            return declarations;
        }catch (Exception e){
            System.out.println("Err in retrieving DECLARATIONS "+e);
            return null;
        }
    }
}
