package com.fstm.coredumped.smartwalkabilty.web.Model.dao;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Image;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DAOAnnonce implements IDAO<Annonce>{
    private static DAOAnnonce daoAnnonce=null;
    public static DAOAnnonce getDAOAnnonce(){
        if(daoAnnonce==null)daoAnnonce=new DAOAnnonce();
        return daoAnnonce;
    }
    private DAOAnnonce(){
    }
    @Override
    public boolean Create(Annonce obj)
    {
        try {
            Connexion.getCon().setAutoCommit(false);
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement("INSERT INTO Annonces (datedebut,datefin,titre,urlimageprincipale,description) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            fillStatement(obj, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet set= preparedStatement.getGeneratedKeys();
            if(set.next()) obj.setId(set.getInt(1));
            for (Image img: obj.getSubImgs()) {
                DAOImage.getDAOImage().Create(img);
            }
            for (Site site :obj.getSites())
            {
                Create_Relation_Ann_Site(obj,site);
            }
            Connexion.getCon().commit();
            Connexion.getCon().setAutoCommit(true);
            return true;
        }catch (SQLException e){
            try {
                Connexion.getCon().rollback();
            } catch (SQLException ex) {
                System.err.println(ex);
                return  false;

            }
            System.err.println(e);
            return false;
        }

    }

    @Override
    public Collection<Annonce> Retrieve()
    {
        return null;
    }
    public Collection<Annonce> RetrieveOrganisationAnnonces(int id_org)
    {
        List<Annonce> annonces=new LinkedList<Annonce>();
        try {
            PreparedStatement sql=Connexion.getCon().prepareStatement("SELECT A.* From annonces A JOIN annonces_Con_Site aCS on A.id = aCS.id_annonce JOIN site S  on aCS.id_site=S.id where id_organisation=?");
            sql.setInt(1,id_org);
            ResultSet set= sql.executeQuery();
            while (set.next()) annonces.add(extractAnnonce(set));
        } catch (SQLException e) {
            System.err.println(e);
        }
        return annonces;
    }
    public Annonce findById(int id){
        try {
            Annonce annonce=null;
            PreparedStatement sql=Connexion.getCon().prepareStatement("SELECT * From annonces where id=?");
            sql.setInt(1,id);
            ResultSet set= sql.executeQuery();
            if(set.next())
            {
                annonce=extractAnnonce(set);
                DAOImage.getDAOImage().findImagesByAnnonce(annonce);
                DAOSite.getDaoSite().extractAnnonceSites(annonce);
            }
            return annonce;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
    private Annonce extractAnnonce(ResultSet set) throws SQLException {
        Annonce annonce=new Annonce();
        annonce.setId(set.getInt("id"));
        annonce.setDescription(set.getString("description"));
        annonce.setUrlPrincipalImage(set.getString("urlimageprincipale"));
        annonce.setTitre(set.getString("titre"));
        annonce.setDateDebut(set.getDate("datedebut"));
        annonce.setDateFin(set.getDate("datefin"));
        return annonce;
    }

    @Override
    public void update(Annonce obj)
    {
        try {
            Connexion.getCon().setAutoCommit(false);
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement("UPDATE  annonces SET datedebut=?,datefin=?,titre=?,urlimageprincipale=?,description=? where id=?");
            fillStatement(obj, preparedStatement);
            preparedStatement.setInt(6,obj.getId());
            preparedStatement.executeUpdate();
            DAOImage.getDAOImage().clearImages(obj);
            for (Image img: obj.getSubImgs())
            {
                DAOImage.getDAOImage().Create(img);
            }
            Clear_Relation_Ann_Site(obj);
            for (Site site :obj.getSites())
            {

                Create_Relation_Ann_Site(obj,site);
            }
            Connexion.getCon().commit();
            Connexion.getCon().setAutoCommit(true);
        }catch (SQLException e){
            try {
                Connexion.getCon().rollback();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
            System.err.println(e);
        }

    }

    private void fillStatement(Annonce obj, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, new Date(obj.getDateDebut().getTime()));
        preparedStatement.setDate(2, new Date(obj.getDateFin().getTime()));
        preparedStatement.setString(3,obj.getTitre());
        preparedStatement.setString(4,obj.getUrlPrincipalImage());
        preparedStatement.setString(5,obj.getDescription());
    }

    @Override
    public boolean delete(Annonce obj)
    {
        try {
            Connexion.getCon().setAutoCommit(false);
            PreparedStatement sql=Connexion.getCon().prepareStatement("DELETE FROM annonces where id=?");
            sql.setInt(1,obj.getId());
            sql.executeUpdate();
            Connexion.getCon().commit();
            Connexion.getCon().setAutoCommit(true);
            return true;
        } catch (Exception e) {
            try {
                Connexion.getCon().rollback();
            } catch (SQLException ex) {
                System.err.println(ex);
                return  false;
            }
            System.err.println(e);
            return false;
        }
    }
    public boolean deleteMultiple(Collection<Annonce> annonces)
    {
        try {
            Connexion.getCon().setAutoCommit(false);
            for (Annonce annonce: annonces) {
                PreparedStatement sql=Connexion.getCon().prepareStatement("DELETE FROM annonces where id=?");
                sql.setInt(1,annonce.getId());
                sql.executeUpdate();
            }
            Connexion.getCon().commit();
            Connexion.getCon().setAutoCommit(true);
            return true;
        } catch (Exception e) {
            try {
                Connexion.getCon().rollback();
            } catch (SQLException ex) {
                System.err.println(ex);
                return  false;
            }
            System.err.println(e);
            return false;
        }
    }
    public boolean Create_Relation_Ann_Site(Annonce annonce,Site site)
    {
        try {
            PreparedStatement statement=Connexion.getCon().prepareStatement("INSERT INTO annonces_con_site VALUES (?,?)");
            statement.setInt(1,annonce.getId());
            statement.setInt(2,site.getId());
            statement.executeUpdate();
            return true;
        }catch (SQLException sqlException){
            System.err.println(sqlException);
            return false;
        }
    }
    private void Clear_Relation_Ann_Site(Annonce annonce) throws SQLException {

        PreparedStatement statement=Connexion.getCon().prepareStatement("Delete FROM annonces_con_site where id_annonce=?");
        statement.setInt(1,annonce.getId());
        statement.executeUpdate();
    }
    public void extractSiteAnnonces(Site site) throws SQLException
    {
        PreparedStatement statement=Connexion.getCon().prepareStatement("SELECT a.* FROM  annonces a JOIN annonces_con_site acs on a.id = acs.id_annonce  where id_site=?");
        statement.setInt(1,site.getId());
        ResultSet set=statement.executeQuery();
        while (set.next()){
            site.AddAnnonce(extractAnnonce(set));
        }
    }
    public boolean checkExiste(int id) {
        try {
            PreparedStatement sql= null;
            sql = Connexion.getCon().prepareStatement("SELECT * From annonces where id=?");
            sql.setInt(1,id);
            ResultSet set= sql.executeQuery();
            if(set.next())return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
