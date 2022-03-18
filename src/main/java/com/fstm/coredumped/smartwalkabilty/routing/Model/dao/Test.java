package com.fstm.coredumped.smartwalkabilty.routing.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) {
        try {
            Connection c =Connexion.getConnection();
            Statement stmnt = c.createStatement();
            ResultSet resultSet = stmnt.executeQuery("SELECT * FROM ways LIMIT 10;");

            while (resultSet.next()){
                double cost = resultSet.getDouble("cost");
                int gid = resultSet.getInt("gid");
                int source = resultSet.getInt("source");
                int target = resultSet.getInt("target");
                System.out.println("gid: "+gid);
                System.out.println("cost: "+cost);
                System.out.println("source: "+source);
                System.out.println("target: "+target);

            }

            resultSet.close();
            stmnt.close();
            c.close();

        }catch (Exception e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }


    }
}
