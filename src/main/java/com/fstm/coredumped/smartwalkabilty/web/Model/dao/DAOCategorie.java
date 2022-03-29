package com.fstm.coredumped.smartwalkabilty.web.Model.dao;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Image;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DAOCategorie implements IDAO<Categorie>{
    private static DAOCategorie daoCategorie=null;
    public static DAOCategorie getDaoCategorie(){
        if(daoCategorie==null)daoCategorie=new DAOCategorie();
        return daoCategorie;
    }
    private DAOCategorie(){

    }
    @Override
    public boolean Create(Categorie obj) {
      return  false;
    }

    @Override
    public Collection<Categorie> Retrieve() {
        try {
            Collection<Categorie> categories = new LinkedList<>();
            Categorie categorie=null;
            PreparedStatement sql=Connexion.getCon().prepareStatement("SELECT * from categories");
            ResultSet set= sql.executeQuery();
            while(set.next())
            {
                categorie=extractCategorie(set);
                categories.add(categorie);
            }
            return categories;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public void update(Categorie obj) {

    }

    @Override
    public boolean delete(Categorie obj) {
        return false;
    }

    private Categorie extractCategorie(ResultSet set) throws SQLException {
        Categorie categorie=new Categorie();
        categorie.setCategorie(set.getString("categorie"));
        categorie.setId(set.getInt("id"));
        return categorie;
    }
    public Categorie getById(int id){
        try {
            Categorie categorie=null;
            PreparedStatement sql=Connexion.getCon().prepareStatement("SELECT * from categories where id=?");
            sql.setInt(1,id);
            ResultSet set= sql.executeQuery();
            if(set.next())
            {
                categorie=extractCategorie(set);
            }
            return categorie;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
}
