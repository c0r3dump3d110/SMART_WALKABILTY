package com.fstm.coredumped.smartwalkabilty.web.Model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private  static  Connection conn=null;
    public static Connection getCon(){
        if(conn==null)conn=createConn();
        return conn;
    }
    private static Connection createConn(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/DB_SMARTWALK","hp","123");
        }catch (ClassNotFoundException e){
            System.err.println(e);
            return  null;
        }catch (SQLException e){
            System.err.println(e);
            return  null;
        }
    }
}
