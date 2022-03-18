package com.fstm.coredumped.smartwalkabilty.web.Model.dao;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DAOImage implements IDAO<Image>{
    private static DAOImage daoImage=null;
    public static DAOImage getDAOImage(){
        if(daoImage==null)daoImage=new DAOImage();
        return daoImage;
    }
    private DAOImage(){

    }
    @Override
    public boolean Create(Image obj) {
        try {
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement("INSERT INTO Images (urlimage,id_Annonce) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, obj.getUrlImage());
            preparedStatement.setInt(2, obj.getAnnonce().getId());
            preparedStatement.executeUpdate();
            ResultSet set= preparedStatement.getGeneratedKeys();
            set.next();
            obj.setId(set.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return  false;
        }
    }

    @Override
    public Collection<Image> Retrieve() {
        return null;
    }

    @Override
    public void update(Image obj) {

    }

    @Override
    public boolean delete(Image obj) {
        return false;
    }
    public List<Image> findImagesByAnnonce(Annonce annonce) throws SQLException {
        List<Image> images=new ArrayList<Image>();
        PreparedStatement preparedStatement= Connexion.getCon().prepareStatement("SELECT * from Images where id_annonce=?");
        preparedStatement.setInt(1, annonce.getId());
        ResultSet set= preparedStatement.executeQuery();
        while (set.next()){
             Image img= extractImage(set);
             img.setAnnonce(annonce);
             annonce.AddImage(img);
             images.add(img);
        }
        return images;
    }
    private Image extractImage(ResultSet set) throws SQLException {
        Image image=new Image();
        image.setUrlImage(set.getString("urlimage"));
        image.setId(set.getInt("id"));
        return image;
    }
    public void clearImages(Annonce annonce) throws SQLException
    {
        PreparedStatement preparedStatement= Connexion.getCon().prepareStatement("DELETE from Images where id_annonce=?");
        preparedStatement.setInt(1, annonce.getId());
        preparedStatement.executeUpdate();
    }
}
