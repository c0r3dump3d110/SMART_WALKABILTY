package com.fstm.coredumped.smartwalkabilty.routing.Model.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    public static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/osmoffdb", "hp", "123");
            // database
            System.out.println("DB opened succefully");
            return c;
        }catch (Exception e){
            System.out.println("Error in connection: "+e.getMessage());
            return null;
        }
    }
}
